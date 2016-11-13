#!/bin/bash
set -e
twc="target/twc.jar"
if [ ! -f $twc ] || [ "$1" == "--build" ] ; then
  ./build.sh
fi
java -jar $twc