#!/bin/bash

set -e
set -x

echo "======================Restart started!=============================="
echo "====================Stopping old components!========================="

docker-compose down

echo "====================All components are down!========================="

echo "=========================Rebuild !==================================="

./build_jars.sh

echo "====================Start all containers!============================="

docker-compose up -d --build

echo "==========================COMPLETED!=================================="
