package net.pannoniae.local

import cluster_cli.run.HostRun
import net.pannoniae.QueryCollect
import net.pannoniae.SQLQuery

class RunLocalHostQueries4 {
  static void main(String[] args) {
    String structureFile = System.getProperty("user.dir") + "/src/main/groovy/DSLfiles/query2n4w"
    Class emitClass = SQLQuery
    Class collectClass = QueryCollect
    new HostRun(structureFile, emitClass, collectClass, "Local").invoke()
  }

}
