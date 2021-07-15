Демонстрация работы с redis и consul.

Процесс установки:
1) Установить redis со стандартными настройками
2) Установить consul со стандартными настройками
3) Создать и заполнить конфиг `/etc/consul.d/redis.json` для consul чтобы он видел redis:\
    ```
   {"service":{
           "id": "redisnode",
           "name": "redis",
           "tags": [
             "master"
           ],
           "address": "127.0.0.1",
           "port": 6379,
           "checks": [
             {
               "args": [
     	  "/usr/local/bin/redis-cli",
     	  "-h",
     	  "127.0.0.1", "-p", "6379", "info", "|", "grep", "role:master", "||","exit 2"
     	  ],
               "interval": "5s"
             }
           ]
         }
     }
   ```
4) Запустить redis-server
5) Запустить consul командой\
`consul agent -dev -enable-script-checks -config-dir=/etc/consul.d`
6) Запустить ConsulRedisExampleApplication