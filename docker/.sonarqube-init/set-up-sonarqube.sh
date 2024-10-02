#!/bin/sh

USER=admin
PASS=admin
PROJECT=playground
PASS2=tmp
SONAR_HOST=sonarqube:9000

echo "Resetting admin password"
curl -su $USER:$PASS -X POST $SONAR_HOST/api/users/change_password \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "login=$USER&password=$PASS2&previousPassword=$PASS"
curl -su $USER:$PASS2 -X POST $SONAR_HOST/api/users/change_password \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "login=$USER&password=$PASS&previousPassword=$PASS2"
echo "Finished resetting admin password"

echo "Creating project"
curl -su $USER:$PASS -X POST $SONAR_HOST/api/projects/create \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=$PROJECT&project=$PROJECT"
echo
echo "Finished creating project"

echo "Finished setup"
