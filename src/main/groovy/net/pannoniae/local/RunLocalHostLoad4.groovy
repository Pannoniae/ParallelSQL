package net.pannoniae.local

import cluster_cli.run.HostRun
import net.pannoniae.SQLCollect
import net.pannoniae.SQLImport

class RunLocalHostLoad4 {
  static void main(String[] args) {
    String structureFile = System.getProperty("user.dir") + "/src/main/groovy/DSLfiles/load2n4w"
    Class emitClass = SQLImport
    Class collectClass = SQLCollect
    new HostRun(structureFile, emitClass, collectClass, "Local").invoke()
  }

}
