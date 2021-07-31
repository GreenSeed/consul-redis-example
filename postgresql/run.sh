#!/bin/sh
/usr/local/bin/docker-entrypoint.sh postgres&
consul agent -config-dir=/consul/config
