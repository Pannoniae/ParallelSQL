package net.pannoniae.invoke

import cluster_cli.run.NodeRun
import jcsp.userIO.Ask

class NetNode {
    static void main(String[] args) {
        String hostIP = null
        String nodeIP = null
        if (args.size() == 0) {
            hostIP = Ask.string("Host IP address? : ")
        } else {
            hostIP = args[0]
            // pass node IP in the second argument
            if (args.size() > 1) {
                nodeIP = args[1]
            }
        }
        println "Running node on host $hostIP with IP $nodeIP"
        if (nodeIP == null) {
            new NodeRun(hostIP).invoke()
        }
        else {
            new NodeRun(hostIP, nodeIP).invoke()
        }
    }
}
