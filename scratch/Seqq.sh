#!/bin/bash
#SBATCH --ntasks=1
#SBATCH --nodes=1
#SBATCH -D /users/40536446/ParallelSQL
#SBATCH -o Seqq.out
#SBATCH --exclusive

module load apps/java/21.0.2/noarch

for n in $(seq 1 5)
do
        printf "Sequential Run Number : %s\n" $n
        PARALLELSQL_DIR="/users/40536446/ParallelSQL" java -jar ../SQLfiles/SeqQuery-1.0.jar
done
