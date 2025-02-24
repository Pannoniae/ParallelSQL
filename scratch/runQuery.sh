#!/bin/bash
ip=$(hostname -i)
printf  "Host ip is %s: starting host node\n" $ip

PARALLELSQL_DIR="/users/40536446/ParallelSQL" java -jar ../SQLfiles/NetHostQuery-1.0.jar ../SQLfiles/query$1
