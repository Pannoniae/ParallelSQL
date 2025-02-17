package net.pannoniae

// load
long startTime, endTimeLoad, endTime
startTime = System.currentTimeMillis()
SQLImport sqlBase = new SQLImport([1, 1])
SQLCollect sqlCollect = new SQLCollect()

SQLImport sqlImporter
sqlImporter = sqlBase.create()
while (sqlImporter != null) {
    sqlImporter.load()
    sqlCollect.collate(sqlImporter, [])
    sqlImporter = sqlBase.create()
}
sqlCollect.finalise([])
endTimeLoad = System.currentTimeMillis()
println "Load time = ${endTimeLoad - startTime} milliseconds"

// query
SQLQuery queryBase = new SQLQuery([1, 1])
QueryCollect queryCollect = new QueryCollect()

SQLQuery sqlQuery
sqlQuery = queryBase.create()
while (sqlQuery != null) {
    sqlQuery.load()
    queryCollect.collate(sqlQuery, [])
    sqlQuery = queryBase.create()
}
queryCollect.finalise([])
endTime = System.currentTimeMillis()
println "Query time = ${endTime - endTimeLoad} milliseconds"
println "Total time = ${endTime - startTime} milliseconds"
