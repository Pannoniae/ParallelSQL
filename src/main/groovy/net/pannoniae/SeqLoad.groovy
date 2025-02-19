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
