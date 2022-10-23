#!/bin/bash

set -e
set -x

echo "====================Stopping old components!========================="

docker-compose down

echo "====================All components are down!========================="