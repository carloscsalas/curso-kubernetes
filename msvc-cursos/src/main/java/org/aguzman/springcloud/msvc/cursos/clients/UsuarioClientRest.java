package org.aguzman.springcloud.msvc.cursos.clients;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*Este nombre se configura en el application.properties del servicio usuario al cual nos queremos
comunicar. Como esta registrado con el AutoDiscoveryClient spring cloud de forma automatica se va comunicar
con el api de kubernetes, va obtener la lista de servicios que contienen el hostname y puerto y los registra
en un diretorio(muy parecido a lo que hace eureka de springcloud de netflix)*/
@FeignClient(name = "msvc-usuarios")
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
