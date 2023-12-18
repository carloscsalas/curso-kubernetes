package org.aguzman.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*FeignClient, ya por defecto tiene caracteristicas de loadBalancer, ya al a ver colocado la dependencia
* "...es-client-loadbalancer"  en el pom, automaticamente FeignClient ya realizar valanceo de carga y comunicaciones
* con otro servicio de manera automatica, no tenemos que configurar*/
/*Aca nos comunicaremos mediante el nombre. Este nombre se configura en el application.properties
* del servicio cursos al cual nos queremos comunicar "spring.application.name"; tambien debe
* coincidir con el nombre del servicio de kubernetes(donde estaran agrupados todos lo pods)*/
@FeignClient(name = "msvc-cursos")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);

}
