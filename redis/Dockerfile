FROM consul:1.9.7 as consul

FROM redis:6.2.4-alpine

# установка и настройка consul
COPY --from=consul /bin/consul /usr/bin/consul
RUN mkdir -p /consul/config
RUN mkdir -p /consul/data
COPY consul-redis.json /consul/config/consul-redis.json

#чтобы запустить redis и consul одновременно решил сделать это через отдельный файл
RUN mkdir /scripts
COPY run.sh /scripts/run.sh
RUN chmod +x /scripts/run.sh

ENTRYPOINT ["/scripts/run.sh"]