
## Dockerizacion
1. crear imagen
docker image build -t luka/lukaback-1.0.0 .

2. Ejecución
docker run --name lukaback -e SERVER_PORT=8080 -p 8080:8080 luka/lukaback-1.0.0

## Redis
 Es necesario crear un servidor Redis en el puerto 6379

1. Docker 
docker run -d --name redis -p6379:6379 redis


