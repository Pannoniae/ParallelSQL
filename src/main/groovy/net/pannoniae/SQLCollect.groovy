package net.pannoniae

import cluster_cli.records.CollectInterface

class SQLCollect implements CollectInterface<SQLImport> {

    SQLCollect(List d) {
        println "collecting"
    }

    SQLCollect() {
        println "collecting2"
    }

    @Override
    void collate(SQLImport sql, List params) {
        println "collecting3"
    }

    @Override
    void finalise(List l) {
        println "collecting4"
    }
}