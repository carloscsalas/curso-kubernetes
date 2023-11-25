package org.aguzman.springcloud.msvc.usuarios.controllers;
import org.aguzman.springcloud.msvc.usuarios.models.entity.Usuario;
import org.aguzman.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/crash")
    public void crash(){
        /*Aca se va simular un quiebre de aplicación. La idea es que cuando se invoque esta ruta y
        ocurra error, la aplicacion se va caer y kubernetes tendria que reiniciar el pod y levantar una
         nueva instancia para que mantenga el escenario ideal que tenemos configurado*/
        ((ConfigurableApplicationContext)context).close();
    }

    @GetMapping
    public Map<String,List<Usuario>> listar() {
        return Collections.singletonMap("usuarios", service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**BindingResult, donde se encuentra el resultado de la validacion*/
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if (result.hasErrors()){
            return validar(result);
        }
        if (!usuario.getEmail().isEmpty() && service.existePorEmail(usuario.getEmail()) ){
            return ResponseEntity.badRequest().
                    body(Collections.singletonMap("mensaje","Ya existe usuario con el ese correo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()){
            return validar(result);
        }

        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()) {
            Usuario usuariodb = o.get();
            if (!usuario.getEmail().isEmpty() &&
                    usuario.getEmail().equalsIgnoreCase(usuariodb.getEmail()) &&
                    service.getByEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("mensaje","Ya existe usuario con el ese correo"));
            }

            Usuario usuarioDb = o.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCursos(@RequestParam List<Long> ids){

        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(),"El campo "+err.getField()+" "+ err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
