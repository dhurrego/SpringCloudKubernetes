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
    * docker run -d -p 8002:8002 --rm --name ms-cursos --network spring cursos
    * docker run -d -p 8001:8001 --rm --name ms-usuarios --network spring usuarios