package net.pannoniae

import cluster_cli.records.EmitInterface

// The flow goes like this: first we distribute the work (by splitting it up at least somewhat equally)
// We get 1 worker per table at least
// Then, each worker loads their table into an SQLite database and writes it onto the shared disk
// Then the collect class attaches all partial databases from the workers and runs the query
class SQLImport implements EmitInterface<SQLImport>, Serializable {

    int i = 0
    int idx

    // used by cluster-cli for the parameter passing
    public SQLImport(List l) {
        i = 0
        println "c" + i
        println "l: " + l[0]+ " " + l[1]
    }

    // actual constructor (used by create)
    public SQLImport(int idx) {
        i = 0
        println "a" + i + " idx:" + idx
        this.idx = idx
    }

    @Override
    SQLImport create() {
        if (i >= 8) {
            return null
        } else {
            i++
            return new SQLImport(i)
        }
    }

    public void load(List p) {
        i = 0
        println "b" + i + " idx:" + idx
    }
}

