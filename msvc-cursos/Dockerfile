FROM openjdk:11.0.16 as builder

#app, se va copiar el pom del parent y en msvc-cursos el pom del microservicio
WORKDIR  /app/msvc-cursos

#El dockerfile esta a nivel del msvc-cursos, pero se va ha copiar desde curso-kubernetes(por eso el ./ ). Se esta copiando
#el pom del parent a la carpeta interna "app".
COPY ./pom.xml /app
#va crear una carpeta .mvn dentro de la carpeta msvc-cursos de WORKDIR, y tod el contenido de ..ios/.mvn lo va copiar
COPY ./msvc-cursos/.mvn ./.mvn
# . , va copiar a la carpeta de trabajo
COPY ./msvc-cursos/mvnw .
COPY ./msvc-cursos/pom.xml .

#para que no compile y se salte el test. main, no va compilar ni ejecutar nada relacionado al codigo fuente, solo
#se necesita que se descargue las dependencias.
#&&, para concatenar otra instruccion. rm, para eliminar la carpeta target
#  RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target
#es muy parecida al anterior, esta a veces no suele descartar tod
RUN ./mvnw dependency:go-offline

#va copiar tod el codigo fuente, tod lo que esta dentro de src, lo va copiar en la carpeta src de la imagen, en
#el directorio del WORKDIR
COPY ./msvc-cursos/src ./src
#aca solo se compila y no descarga dependencias
RUN ./mvnw clean package -DskipTests

FROM openjdk:11.0.16

WORKDIR /app
#cuando se cree la segunda imagen por el builder, luego se crearia la carpeta logs en la carpeta de trabajo, "/app"
RUN mkdir ./logs

#con from hacemos referencia a la imagen anterior. Lo hacemos para copiar el target que se
#genero en la construccion anterior en la carpeta /app
COPY --from=builder /app/msvc-cursos/target/msvc-cursos-0.0.1-SNAPSHOT.jar .

#puerto donde se ejecuta la aplicacion. Normalmente configurado en el application.properties
EXPOSE 8001

#se cambia a CMD porque el ENTRYPOINT es inmutable, no se puede cambiar el comando de arranque por otro
CMD ["java", "-jar", "msvc-cursos-0.0.1-SNAPSHOT.jar"]

#CMD sleep 10 && java -jar msvc-cursos-0.0.1-SNAPSHOT.jar
