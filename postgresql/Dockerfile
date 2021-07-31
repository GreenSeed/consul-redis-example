FROM consul:1.10.1 as consul

FROM postgres:13.3-alpine

# установка и настройка consul
COPY --from=consul /bin/consul /usr/bin/consul
RUN mkdir -p /consul/config
RUN mkdir -p /consul/data
COPY consul-postgres.json /consul/config/consul-postgres.json

#чтобы запустить redis и consul одновременно решил сделать это через отдельный файл
RUN mkdir /scripts
COPY run.sh /scripts/run.sh
RUN chmod +x /scripts/run.sh
ENTRYPOINT ["/scripts/run.sh"]