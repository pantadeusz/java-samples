#!/bin/bash -e

cd $(dirname $0)
cd ..

D=$PWD

cd dbunit/hellodbunit/scripts
./server.sh &
SRVPID=$!
cd $D
cd jdbc/hellojdbc
mvn test
kill $SRVPID

