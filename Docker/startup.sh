#!/bin/bash
mongod &
mongosh WhatsNew /tmp/mongo-users.js

/opt/tomcat/bin/catalina.sh run
