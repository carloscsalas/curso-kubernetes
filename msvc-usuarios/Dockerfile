#variable que se usa a nivel de Dockerfile y construccion de imagen. Es un arg global
ARG MSVC_NAME=msvc-usuarios

FROM openjdk:17-jdk-alpine as builder
#se coloca el arg msvc_name despues de cada from para que sea considerado en esta etapa
ARG MSVC_NAME

#app, se va copiar el pom del parent y en msvc-usuarios el pom del microservicio
WORKDIR  /app/$MSVC_NAME

#El dockerfile esta a nivel del $MSVC_NAME, pero se va ha copiar desde curso-kubernetes(por eso el ./ ). Se esta copiando
#el pom del parent a la carpeta interna "app".
COPY ./pom.xml /app
#va crear una carpeta .mvn dentro de la carpeta msvc-usuarios de WORKDIR, y tod el contenido de ..ios/.mvn lo va copiar
COPY ./$MSVC_NAME/.mvn ./.mvn
#copiamos el ejecutable mvn de linux, y la copiamos en la raiz
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

#para que no compile y se salte el test. main, no va compilar ni ejecutar nada relacionado al codigo fuente, solo
#se necesita que se descargue las dependencias.
#&&, para concatenar otra instruccion. rm, para eliminar la carpeta target
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target
#RUN ./mvnw dependency:go-offline   es muy parecida al anterior, esta a veces no suele descartar todo

#va copiar tod el codigo fuente, tod lo que esta dentro de src, lo va copiar en la carpeta src de la imagen, en
#el directorio del WORKDIR
COPY ./$MSVC_NAME/src ./src
#aca solo se compila y no descarga dependencias
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
#se declara el arg msvc_name despues de cada from para que sea considerado en esta etapa
ARG MSVC_NAME

WORKDIR /app
#cuando se cree la segunda imagen por el builder, luego se crearia la carpeta logs en la carpeta de trabajo, "/app"
RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MSVC_NAME/target
#con from hacemos referencia a la imagen anterior. Lo hacemos para copiar el target que se
#genero en la construccion anterior en la carpeta /app
COPY --from=builder $TARGET_FOLDER/msvc-usuarios-0.0.1-SNAPSHOT.jar .

#definiendo la variable de ambiente PORT con valor por defecto 8001, cuando se levanta la aplicación va setear el
#puerto a 8000 y ya no el 8001 en la variable PORT del properties. Recordar que es el valor interno de la aplicacion.
#se cambia a 8001 porque el micro. cursos espera un 8001
# ENV PORT 8001

ARG PORT_APP=8001
ENV PORT $PORT_APP

#puerto donde se ejecuta la aplicacion. En el contenedor se tiene que habilitar este puerto
EXPOSE $PORT

#aca no se deberia usar la variable ARG, porque este linea se ejecuta en el contenedor, si lo colocamos
#aca no lo va reconocer
CMD ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

#Implementamos otro CMD para darle un tiempo de espera al contenedor, con un comado de linux sleep.
##Aca ejecutamos una sentencia completa. Arriba es una ejecucion con comando  donde "java" seria el principal
#CMD sleep 20 && java -jar msvc-usuarios-0.0.1-SNAPSHOT.jar