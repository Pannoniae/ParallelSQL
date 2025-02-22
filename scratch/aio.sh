#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 source_file destination_path"
    exit 1
fi

source_file="$1"
destination_path="$2"

scp -r -J 40536446@gateway.napier.ac.uk "$source_file" 40536446@login.enucc.napier.ac.uk:"$destination_path"
