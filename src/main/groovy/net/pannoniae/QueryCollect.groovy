package net.pannoniae

import cluster_cli.records.CollectInterface

class QueryCollect implements CollectInterface<SQLQuery> {

    int totalResults = 0

    QueryCollect(List d) {
        //println "collecting"
    }

    QueryCollect() {
        //println "collecting2"
    }

    @Override
    void collate(SQLQuery sql, List params) {
        totalResults += sql.totalResults
    }

    @Override
    void finalise(List l) {
        println "Total rows = $totalResults"
    }
}