package net.pannoniae.local

import cluster_cli.run.NodeRun

class Node5 {
  static void main(String[] args) {
    new NodeRun("127.0.0.1", "127.0.0.5").invoke()
  }
}
