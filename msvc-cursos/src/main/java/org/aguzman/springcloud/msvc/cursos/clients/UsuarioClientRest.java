package org.aguzman.springcloud.msvc.cursos.clients;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**en la variable url, podemos utilizar los datos del aplication.properties. Por ejemplo utilizamos esa llave del
 * property para configurar el host del microserv. usuarios*/
@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    /**Se estuvo utilizando List en el RequestParam, pero en el Feign da problemas, por ello
     * utilizamos Iterable. Al final Iterable es una interface que se implementa en el List*/
    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCursos(@RequestParam Iterable<Long> ids);

}
