package net.pannoniae.local

import cluster_cli.run.HostRun
import net.pannoniae.SQLCollect
import net.pannoniae.SQLImport

class RunLocalHost4 {
  static void main(String[] args) {
    String structureFile = System.getProperty("user.dir") + "/src/main/groovy/DSLfiles/2n4w"
    Class  emitClass = SQLImport
    Class collectClass = SQLCollect
    new HostRun(structureFile, emitClass, collectClass, "Local").invoke()
  }

}
