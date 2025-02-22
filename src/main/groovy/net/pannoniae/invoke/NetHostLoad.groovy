package net.pannoniae.invoke

import cluster_cli.run.HostRun
import jcsp.userIO.Ask
import net.pannoniae.SQLCollect
import net.pannoniae.SQLImport

class NetHostLoad {
    static void main(String[] args) {
        String structureFile
        if (args.size() == 0)
            structureFile = Ask.string("Full pathname of the structure file? : ")
        else
            structureFile = args[0]
        new HostRun(structureFile, SQLImport, SQLCollect).invoke()
    }
}
