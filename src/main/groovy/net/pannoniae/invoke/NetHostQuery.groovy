package net.pannoniae.invoke

import cluster_cli.run.HostRun
import jcsp.userIO.Ask
import net.pannoniae.QueryCollect
import net.pannoniae.SQLQuery

class NetHostQuery {
    static void main(String[] args) {
        String structureFile
        if (args.size() == 0) {
            structureFile = Ask.string("Full pathname of the structure file? : ")
        } else {
            structureFile = args[0]
        }
        String nature = "Net"
        if (args.size() > 1) {
            nature = args[1]
        }
        new HostRun(structureFile, SQLQuery, QueryCollect, nature).invoke()
    }
}
