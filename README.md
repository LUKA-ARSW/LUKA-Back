
## Dockerizacion
1. crear imagen
docker image build -t luka/lukaback-1.0.0 .

2. Ejecución
docker run --name lukaback -e SERVER_PORT=<puerto del servidor> -e MONGO_PORT=<puerto de mongo> -e REDIS_HOST=<host de redis> -e REDIS_PORT=<puerto de redis> -e LUKA_JWT_SECRET=<llave de 256 bits> -p 13000:<SERVER_PORT> luka/lukaback-1.0.0

## Redis
 Es necesario crear un servidor Redis en el puerto 6379

1. Docker 
docker run -d --name redis -p6379:6379 redis


