package net.pannoniae

// query
long startTime, endTime
startTime = System.currentTimeMillis()
SQLQuery queryBase = new SQLQuery([1, 1])
QueryCollect queryCollect = new QueryCollect()

SQLQuery sqlQuery
sqlQuery = queryBase.create()
while (sqlQuery != null) {
    sqlQuery.query()
    queryCollect.collate(sqlQuery, [])
    sqlQuery = queryBase.create()
}
queryCollect.finalise([])
endTime = System.currentTimeMillis()
println "Total time = ${endTime - startTime} milliseconds"
