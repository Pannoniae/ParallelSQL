#!/bin/bash
ip=$(hostname -i)
printf "Node ip is %s: running a node process\n" $ip

PARALLELSQL_DIR="/users/40536446/ParallelSQL" java -jar ../SQLfiles/NetNode-1.0.jar 10.10.0.102
