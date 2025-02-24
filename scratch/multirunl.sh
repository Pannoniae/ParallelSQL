#!/bin/bash

# Parse input
if [ $# -ne 1 ]; then
    echo "Usage: $0 <config>"
    echo "Example: $0 2n12w"
    exit 1
fi

# Extract parameters using regex
if [[ $1 =~ ^([0-9]+)n([0-9]+)w$ ]]; then
    specified_nodes=${BASH_REMATCH[1]}
    workers_per_node=${BASH_REMATCH[2]}
else
    echo "Invalid parameter format. Expected format: NnWw"
    echo "Example: 2n12w"
    exit 1
fi

# total nodes = n + 3
# start node = 2
# end node = 2 + total nodes
# num_nodes = n + 2 because host is not included
num_nodes=$((specified_nodes + 2))
end_node=$((num_nodes + 2))
cpus_per_task=$((workers_per_node + 2))

# cap it at 32 (the max. number of CPUs on the system)
cpus_per_task=$((cpus_per_task > 32 ? 32 : cpus_per_task))

module load apps/java/21.0.2/noarch

for n in $(seq 1 5)
do
    printf "Run Number : %s\n" "$n"
    printf "%d\n" $num_nodes
    printf "%d\n" $end_node
    printf "%d\n" $cpus_per_task
    srun -N 1 -n 1 --cpus-per-task=$cpus_per_task --exact --nodelist node[02] ./runLoad.sh "$1" &
    srun -N $num_nodes -n $num_nodes --cpus-per-task=$cpus_per_task --exact --nodelist node[03-"$(printf "%02d" $end_node)"] ./runNode.sh &

    wait
done
