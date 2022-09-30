## COMANDOS PARA INICIAR LOS MICROSERVICIOS

1. Creando network docker
    * docker network create spring
2. Creando imagenes MS
    * docker build -t cursos:latest . -f .\ms-cursos\Dockerfile
    * docker build -t usuarios:latest . -f .\ms-usuarios\Dockerfile
3. Corriendo contenedores
    * docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=123456 -e MY
      SQL_DATABASE=ms_usuarios -v data-mysql:/var/lib/mysql mysql:8
    * docker run -d -p 5532:5432 --name postgres14 --network spring -e POSTGRES_PASSWORD=123456 -e POS
       TGRES_DB=ms_cursos -v data-postgres:/var/lib/postgresql/data postgres:14-alpine
    * docker run -p 8002:8002 --env-file .\ms-cursos\.env --rm -d --name ms-cursos --network spring cursos
    * docker run -p 8001:8001 --env-file .\ms-usuarios\.env --rm -d --name ms-usuarios --network spring usuarios

## Usando Docker Compose

### Comandos para iniciar y detener contenedores o services y construir images

1. Iniciar: docker-compose up -d --build
2. Detener y eliminar: docker-compose down