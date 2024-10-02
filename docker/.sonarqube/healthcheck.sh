#!/bin/sh

echo Running healthcheck
response=$(curl -su admin:admin http://localhost:9000/api/system/health)
health=$(echo $response | jq '.health')
if [[ $health == '"GREEN"' ]]
then
  echo End healthcheck
  exit 0
else
  echo Received $response
  echo End healthcheck
  exit 1
fi