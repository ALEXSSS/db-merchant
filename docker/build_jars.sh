#!/bin/sh

set -e
set -x

echo "Current location: $(pwd)"
(cd .. && mvn clean install -DskipTests)
ls -alh ../

#rm -rf jars/*
echo "Move jars"
mv ../merchant-team/target/merchant-team-1.0-SNAPSHOT.jar jars/merchant-service.jar
