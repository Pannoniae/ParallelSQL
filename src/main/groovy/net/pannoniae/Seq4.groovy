package net.pannoniae

long startTime, endTime
startTime = System.currentTimeMillis()
SQLImport sqlBase = new SQLImport([1, 4])
SQLCollect sqlCollect = new SQLCollect()

SQLImport sqlImporter
sqlImporter = sqlBase.create()
while (sqlImporter != null) {
    sqlImporter.load()
    sqlCollect.collate(sqlImporter, [])
    sqlImporter = sqlBase.create()
}
sqlCollect.finalise([])
endTime = System.currentTimeMillis()
println "Elapsed time = ${endTime - startTime} milliseconds"
