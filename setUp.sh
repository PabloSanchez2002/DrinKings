#!/bin/bash

# Set up the environment
# java 21.0.5
# Gradle 4.4.1
# ./gradlew bootRun
# ./gradlew clean build --refresh-dependencies
sudo apt update
sudo apt install sqlite3
sudo apt install libsqlite3-dev

sqlite3 drinkings.db
