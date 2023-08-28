package org.aguzman.springcloud.msvc.cursos.clients;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/*FeignClient, nos permite consumir al microservicio usuario.*/
@FeignClient(name = "msvc-usuarios", url = "msvc-usuarios:8001")
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
