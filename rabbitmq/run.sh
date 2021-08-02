#!/bin/sh
/usr/local/bin/docker-entrypoint.sh rabbitmq-server &
consul agent -config-dir=/consul/config
