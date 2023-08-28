package org.aguzman.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**"msvc-cursos de url, es el nombre hostname del micros. cursos, y se le da ese nombre porque sera el
 * nombre que se le asigne cuando se cree el contenedor
 * con el --name "msvc-cursos" y tambien tiene que coincidir con el nombre del microservicio que esta definido
 * en el application.properties"*/
@FeignClient(name = "msvc-cursos", url="msvc-cursos:8002")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);

}
