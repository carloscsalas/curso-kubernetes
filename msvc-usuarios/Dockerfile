FROM openjdk:11.0.16

#es la ruta de trabajo, ahi se va copiar la aplicacion
WORKDIR  /app

#copiar desde la carpeta donde estamos posicionados actualmente, jar que esta en target
#. , con el punto se copia a la carpeta destino, que seria el WORKDIR
COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

#puerto donde se ejecuta la aplicacion. En el contenedor se tiene que habilitar este puerto
EXPOSE 8001

#levantar la aplicacion, que se ejecutara cuando se levanta el contenedor.
#el tercer parametro es el ejecutable que estaria en la carpeta de trabajo (/app), ya apunta al workdir
ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]
