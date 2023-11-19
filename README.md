
##Dockerizacion
1. crear imagen
docker image build -t luka/lukaBack-1.0.0 .

2. Ejecución
docker run --name lukaBack -e SERVER_PORT=8080 -p 8080:8080 luka/lukaback-1.0.0