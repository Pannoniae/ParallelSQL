package net.pannoniae

import cluster_cli.records.EmitInterface
import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import groovy.transform.CompileStatic

// The flow goes like this: first we distribute the work (by splitting it up at least somewhat equally)
// We get 1 worker per table at least
// Then, each worker loads their table into an SQLite database and writes it onto the shared disk
class SQLQuery implements EmitInterface<SQLQuery>, Serializable {

    int nodes, workers, total
    int i = 0

    // partitioned queries
    List<List<Integer>> queries

    // used by cluster-cli for the parameter passing
    public SQLQuery(List l) {
        nodes = l[0] as int
        workers = l[1] as int
        total = nodes * workers
        // queries (total number: 19)
        // query 17, 20 and 22 are very long, skip
        List<Integer> work = (1..22).findAll { it != 17 && it != 20 && it != 22 } as List<Integer>

        println("work: " + work)
        queries = Stats.partition(work, total)
        println "queries: " + queries
        println "" + queries.getClass() + " " + queries.get(0).getClass()

    }

    // actual constructor (used by create)
    public SQLQuery(List<List<Integer>> queries, int nodes, int workers, int i) {
        this.queries = queries
        this.nodes = nodes
        this.workers = workers
        this.i = i
    }

    @Override
    SQLQuery create() {
        if (i >= total) {
            return null
        } else {
            println "i: " + i
            var inst = new SQLQuery(queries, nodes, workers, i)
            i++
            return inst
        }
    }

    void query(List p) {
        //println " idx:" + i
        // if empty, we're done
        if (queries[i].size() == 0) {
            return
        }
        long startTime = System.currentTimeSeconds()
        println(Constants.tables.keySet())

        // create db in memory
        def sql = Sql.newInstance("jdbc:sqlite::memory:", "org.sqlite.JDBC")
        // attach all databases in the db folder to the in-memory db
        var ctr = 0
        Constants.tables.keySet().each { table ->
            sql.execute("ATTACH DATABASE ? AS ?", ["${Stats.PATH}${table}.db", table])
            ctr++
        }
        println("Attached $ctr databases")

        //def q = 1

        // execute the queries for this worker
        queries[i].each { q ->
            def query = Constants.queries[q]

            def results
            if (q.toInteger() == 15) {
                // there are 2 statements in this query, we need the result of the second
                def statements = query.split(";").collect(it -> it + ";")
                sql.execute(statements[0]) // view
                results = sql.rows(statements[1])
            } else {
                results = sql.rows(query)
            }
            println "q: " + q
            println "results: " + results.size()

            // print results (somewhat formatted)
            // also don't display scientific, display normal numbers
            /*results.each { row ->
                row.each { k, v ->
                    print "$k: "
                    if (v instanceof Double) {
                        println String.format("%.2f", v)
                    } else {
                        println v
                    }
                }
                println ""
            }*/
            long endTime = System.currentTimeSeconds()
            println "\nElapsed time = ${endTime - startTime} seconds (query)"
        }
        // close the db
        sql.close()

        long endTime = System.currentTimeSeconds()
        println "\nElapsed time = ${endTime - startTime} seconds (node)"
    }

}
