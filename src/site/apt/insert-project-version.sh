#!/bin/sh

files="download.apt"

for i in $files
do
  sed 's/${project.version}/2.0.5/' $i.template > $i
done
