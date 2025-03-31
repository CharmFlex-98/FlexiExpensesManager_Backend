FROM --platform=linux/amd64 postgres:latest

RUN apt-get update && apt-get  install -y postgresql-16-postgis-3

WORKDIR /docker-entrypoint-initdb.d
COPY ../../../database/00_database_init.sql /docker-entrypoint-initdb.d
COPY ../../../database/deploy /docker-entrypoint-initdb.d