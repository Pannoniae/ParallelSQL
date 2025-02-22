package net.pannoniae

import cluster_cli.records.EmitInterface
import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import groovy.transform.CompileStatic
import org.sqlite.SQLiteException

// The flow goes like this: first we distribute the work (by splitting it up at least somewhat equally)
// We get 1 worker per table at least
// Then, each worker loads their table into an SQLite database and writes it onto the shared disk
class SQLImport implements EmitInterface<SQLImport>, Serializable {

    int nodes, workers, total
    int i = 0

    // index to table
    List<List<String>> stats

    // used by cluster-cli for the parameter passing
    public SQLImport(List l) {
        nodes = l[0] as int
        workers = l[1] as int
        total = nodes * workers
        stats = Stats.partition(Stats.counts(Stats.PATH), total)
        //println "stats: " + stats
        //println "" + stats.getClass() + " " + stats.get(0).getClass()

        // delete db files before doing anything
        Constants.tables.keySet().each { table ->
            def file = new File("${Stats.PATH}${table}.db")
            if (file.exists()) {
                file.delete()
            }
        }
    }

    // actual constructor (used by create)
    public SQLImport(List<List<String>> stats, int nodes, int workers, int i) {
        this.stats = stats
        this.nodes = nodes
        this.workers = workers
        this.i = i
    }

    @Override
    SQLImport create() {
        if (i >= total) {
            return null
        } else {
            var inst = new SQLImport(stats, nodes, workers, i)
            i++
            return inst
        }
    }

    void load(List p) {
        //println " idx:" + i
        // if empty, we're done
        if (stats[i].size() == 0) {
            return
        }
        long startTime = System.currentTimeSeconds()

        // for each table, load the data
        for (String table : stats[i]) {

            // load the table
            // the db name is the table name with the extension changed to .db
            String basename = table.split("\\.")[0]
            String dbname = basename + ".db"
            //println dbname

            Sql db = Sql.newInstance("jdbc:sqlite:" + Stats.PATH + dbname)
            // needs to be executed first?
            //db.execute(Constants.cache)

            db.execute(Constants.jmOff)
            db.execute(Constants.synch)
            db.execute(Constants.lock)
            db.execute(Constants.temp)

            db.cacheStatements = true

            // execute create table
            db.execute(Constants.tables[basename].drop)
            db.execute(Constants.tables[basename].create)

            // load the data
            loadData(db, table)

            db.close()
        }
        long endTime = System.currentTimeSeconds()
        println "\nElapsed time = ${endTime - startTime} seconds"
    }

    @CompileStatic
    static void loadData(Sql db, String table) {
        String basename = table.split("\\.")[0]
        File f = new File(Stats.PATH + table)
        int batchSize = 16384
        List<String> tokens

        db.withTransaction {
            switch (basename) {
                case "region":
                    db.withBatch(batchSize, Constants.insertRegion) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), tokens[1], tokens[2])
                        }
                    }
                    break
                case "nation":
                    db.withBatch(batchSize, Constants.insertNation) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), tokens[1],
                                    Integer.parseInt(tokens[2]), tokens[3])
                        }
                    }
                    break
                case "supplier":
                    db.withBatch(batchSize, Constants.insertSupplier) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), tokens[1], tokens[2],
                                    Integer.parseInt(tokens[3]), tokens[4],
                                    Double.parseDouble(tokens[5]), tokens[6])
                        }
                    }
                    break
                case "part":
                    db.withBatch(batchSize, Constants.insertPart) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), tokens[1], tokens[2],
                                    tokens[3], tokens[4], Integer.parseInt(tokens[5]),
                                    tokens[6], Double.parseDouble(tokens[7]), tokens[8])
                        }
                    }
                    break
                case "customer":
                    db.withBatch(batchSize, Constants.insertCustomer) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), tokens[1], tokens[2],
                                    Integer.parseInt(tokens[3]), tokens[4],
                                    Double.parseDouble(tokens[5]), tokens[6], tokens[7])
                        }
                    }
                    break
                case "partsupp":
                    db.withBatch(batchSize, Constants.insertPartSupp) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
                                    Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]),
                                    tokens[4])
                        }
                    }
                    break
                case "lineitem":
                    db.withBatch(batchSize, Constants.insertLineItem) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
                                    Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]),
                                    Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]),
                                    Double.parseDouble(tokens[6]), Double.parseDouble(tokens[7]),
                                    tokens[8], tokens[9], tokens[10], tokens[11], tokens[12],
                                    tokens[13], tokens[14], tokens[15])
                        }
                    }
                    break
                case "orders":
                    db.withBatch(batchSize, Constants.insertOrders) { BatchingPreparedStatementWrapper ps ->
                        f.eachLine { String line ->
                            tokens = line.tokenize('|')
                            ps.addBatch(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
                                    tokens[2], Double.parseDouble(tokens[3]), tokens[4],
                                    tokens[5], tokens[6], Integer.parseInt(tokens[7]), tokens[8])
                        }
                    }
                    break
            }
        }
    }
}

