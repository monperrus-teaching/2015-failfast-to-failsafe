#!/bin/bash

cd FastToSafe
mvn clean
cd ..
cd commons-email
rm lib/*
mvn clean
cd ..
cd Example
rm lib/*
mvn clean
