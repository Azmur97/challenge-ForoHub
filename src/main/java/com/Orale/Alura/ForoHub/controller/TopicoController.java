package com.Orale.Alura.ForoHub.controller;
import com.Orale.Alura.ForoHub.model.Topico;
import com.Orale.Alura.ForoHub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;

    @Autowired
    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }


    @GetMapping
    public ResponseEntity<List<Topico>> obtenerTodosLosTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            return ResponseEntity.ok(topico);
        } else {
            return ResponseEntity.notFound().build();
        }
}

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@Valid @RequestBody Topico topico) {
        // Validar si ya existe un tópico con el mismo título y mensaje
        if (topicoRepository.existsByTituloAndAutor(topico.getTitulo(), topico.getMensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Guardar el tópico en la base de datos
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico topicoActualizado) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            // Verificar si el nuevo título y mensaje ya existen en otro tópico
            if (topicoRepository.existsByTituloAndAutorAndIdNot(topicoActualizado.getTitulo(), topicoActualizado.getMensaje(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Actualizar los datos del tópico existente con los datos recibidos
            Topico existente = optionalTopico.get();
            existente.setTitulo(topicoActualizado.getTitulo());
            existente.setMensaje(topicoActualizado.getMensaje());
            existente.setAutor(topicoActualizado.getAutor());
            existente.setCurso(topicoActualizado.getCurso());

            // Guardar y devolver el tópico actualizado
            Topico topicoActualizadoDB = topicoRepository.save(existente);
            return ResponseEntity.ok(topicoActualizadoDB);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
