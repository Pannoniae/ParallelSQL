#!/bin/bash
#SBATCH --ntasks=1
#SBATCH --nodes=1
#SBATCH -D /users/40536446/ParallelSQL
#SBATCH -o Seq.out
#SBATCH --exclusive

for n in $(seq 1 5)
do
        printf "Sequential Run Number : %s\n" $n
        java -jar ../SQL/Seq.jar
done
