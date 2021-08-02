FROM consul:1.10.1 as consul

FROM rabbitmq:3.9.1-management-alpine

# установка и настройка consul
COPY --from=consul /bin/consul /usr/bin/consul
RUN mkdir -p /consul/config
RUN mkdir -p /consul/data
COPY consul-rabbitmq.json /consul/config/consul-rabbitmq.json

COPY rabbitmq.conf /etc/rabbitmq/conf.d/rabbitmq.conf

#чтобы запустить rabbitmq и consul одновременно решил сделать это через отдельный файл
RUN mkdir /scripts
COPY run.sh /scripts/run.sh
RUN chmod +x /scripts/run.sh

ENTRYPOINT ["/scripts/run.sh"]