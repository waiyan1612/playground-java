#!/bin/bash

set -eu

psql -v ON_ERROR_STOP=1 --username "${POSTGRES_USER}" > /dev/null <<-EOSQL
  CREATE USER ${MIGRATION_USER} LOGIN NOCREATEDB NOCREATEROLE NOINHERIT NOSUPERUSER PASSWORD '${MIGRATION_PASSWORD}';
  GRANT ${MIGRATION_USER} to ${POSTGRES_USER};
  CREATE DATABASE ${APP_DB} OWNER ${MIGRATION_USER};
  REVOKE ${MIGRATION_USER} from ${POSTGRES_USER};
  CREATE USER ${APP_USER} LOGIN NOCREATEDB NOCREATEROLE NOINHERIT NOSUPERUSER PASSWORD '${APP_PASSWORD}';
EOSQL
