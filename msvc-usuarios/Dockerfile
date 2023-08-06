FROM openjdk:11.0.16 as builder

#app, se va copiar el pom del parent y en msvc-usuarios el pom del microservicio
WORKDIR  /app/msvc-usuarios

#El dockerfile esta a nivel del msvc-usuarios, pero se va ha copiar desde curso-kubernetes(por eso el ./ ). Se esta copiando
#el pom del parent a la carpeta interna "app".
COPY ./pom.xml /app
#va crear una carpeta .mvn dentro de la carpeta msvc-usuarios de WORKDIR, y tod el contenido de ..ios/.mvn lo va copiar
COPY ./msvc-usuarios/.mvn ./.mvn
#copiamos el ejecutable mvn de linux, y la copiamos en la raiz
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#para que no compile y se salte el test. main, no va compilar ni ejecutar nada relacionado al codigo fuente, solo
#se necesita que se descargue las dependencias.
#&&, para concatenar otra instruccion. rm, para eliminar la carpeta target
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target
#RUN ./mvnw dependency:go-offline   es muy parecida al anterior, esta a veces no suele descartar todo

#va copiar tod el codigo fuente, tod lo que esta dentro de src, lo va copiar en la carpeta src de la imagen, en
#el directorio del WORKDIR
COPY ./msvc-usuarios/src ./src
#aca solo se compila y no descarga dependencias
RUN ./mvnw clean package -DskipTests

#ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"] (se elimina porque lo ejecuta la siguiente imagen)

FROM openjdk:11.0.16

WORKDIR /app
#cuando se cree la segunda imagen por el builder, luego se crearia la carpeta logs en la carpeta de trabajo, "/app"
RUN mkdir ./logs

#con from hacemos referencia a la imagen anterior. Lo hacemos para copiar el target que se
#genero en la construccion anterior en la carpeta /app
COPY --from=builder /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

#puerto donde se ejecuta la aplicacion. En el contenedor se tiene que habilitar este puerto
EXPOSE 8001

#se cambia a CMD porque el ENTRYPOINT es inmutable, no se puede cambiar el comando de arranque por otro
CMD ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]
