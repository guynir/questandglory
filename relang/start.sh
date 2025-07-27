#!/bin/bash

# Start the nginx as reverse proxy.
nginx -g 'daemon off;' &
tail -f /var/log/nginx/access.log &
tail -f /var/log/nginx/error.log &

# Start backend Java server.
cd /app/server || exit
java -jar app-0.0.1-SNAPSHOT.jar &

# Start NodeJS webapp server.
cd /app/webapp || exit
node server.js &

wait -n
exit $?