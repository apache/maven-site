#!/bin/bash

#libreoffice --headless --convert-to svg content/xdoc/maven-sources.odg
# CLI export keeps full A3 page
# I prefer doing it by hand, limiting export to "selection" = avoids extra space

# svgo https://github.com/svg/svgo
svgo --config svgo.config.mjs maven-sources.svg -o maven-sources-optimized.svg

cat maven-sources-optimized.svg \
  | sed 's/a xlink:href/a target="_parent" xlink:href/' \
  | sed 's_file://__' \
  > content/resources/maven-sources.svg
