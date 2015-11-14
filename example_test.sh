#!/bin/bash

cp commons-email/target/commons-email-1.4.jar Example/lib/
cd Example
mvn package
java -jar target/example-1.0-SNAPSHOT.jar
