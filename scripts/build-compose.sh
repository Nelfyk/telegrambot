#!/usr/bin/env bash
mvn clean package -DskipTests
docker-compose build
docker-compose up -d