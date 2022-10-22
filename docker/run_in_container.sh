#!/bin/sh

if [ -f /project/project.jar ]; then
  echo "Found jar"
  java -version
  export JVM_OPTIONS="$JVM_OPTIONS -Xmx1024m"
  echo "$JVM_OPTIONS"
  cd /project && java -jar project.jar "${JVM_OPTIONS}"
else
  echo "Jar not found!"
  /bin/bash
fi
