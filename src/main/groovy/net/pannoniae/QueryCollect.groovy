package net.pannoniae

import cluster_cli.records.CollectInterface

class QueryCollect implements CollectInterface<SQLQuery> {

    QueryCollect(List d) {
        //println "collecting"
    }

    QueryCollect() {
        //println "collecting2"
    }

    @Override
    void collate(SQLQuery sql, List params) {
        //println "collecting3"
    }

    @Override
    void finalise(List l) {
        //println "collecting4"
    }
}