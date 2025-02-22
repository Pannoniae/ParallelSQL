package net.pannoniae.invoke

import cluster_cli.run.HostRun
import net.pannoniae.QueryCollect
import net.pannoniae.SQLCollect
import net.pannoniae.SQLImport
import net.pannoniae.SQLQuery

// this doesn't actually work (framework is not set up to run this way)
class RunLocalHost4 {
  static void main(String[] args) {
    String structureFileLoad = System.getProperty("user.dir") + "/src/main/groovy/DSLfiles/load2n4w"
    String structureFileQuery = System.getProperty("user.dir") + "/src/main/groovy/DSLfiles/query2n4w"
    Class emitClassLoad = SQLImport
    Class collectClassLoad = SQLCollect
    Class emitClassQuery = SQLQuery
    Class collectClassQuery = QueryCollect
    new HostRun(structureFileLoad, emitClassLoad, collectClassLoad, "Local").invoke()
    println "----------------------"
    new HostRun(structureFileQuery, emitClassQuery, collectClassQuery, "Local").invoke()
  }
}
