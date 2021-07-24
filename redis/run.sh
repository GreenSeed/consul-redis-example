#!/bin/sh
redis-server --protected-mode no &
consul agent -config-dir=/consul/config
