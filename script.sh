#!/bin/bash

cd FastToSafe
mvn clean
mvn package
cd ..
rm commons-email/lib/*
cp FastToSafe/target/fasttosafe-1.0-SNAPSHOT.jar commons-email/lib/

cd commons-email/
mvn compile -e
