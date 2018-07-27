#!/bin/bash

cd "$(dirname "$0")"
docker-compose -f single-docker-compose.yml down
docker-compose -f single-docker-compose.yml up
