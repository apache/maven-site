#!/bin/sh

files="download.apt"

for i in $files
do
  sed 's/${project.version}/2.0.5/g' $i.template > $i
done
