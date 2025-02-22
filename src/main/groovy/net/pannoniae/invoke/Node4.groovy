package net.pannoniae.invoke

import cluster_cli.run.NodeRun

class Node4 {
  static void main(String[] args) {
    new NodeRun("127.0.0.1", "127.0.0.4").invoke()
  }
}
