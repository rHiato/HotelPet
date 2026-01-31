package br.com.hotelpet.controller;
import br.com.hotelpet.dto.*;
import br.com.hotelpet.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {
    @Autowired private TutorService tutorService;

    @PostMapping
    public ResponseEntity<TutorResponseDTO> cadastrar(@RequestBody TutorRequestDTO dados) {
        return ResponseEntity.status(201).body(tutorService.cadastrar(dados));
    }
    @GetMapping
    public ResponseEntity<List<TutorResponseDTO>> listar() {
        return ResponseEntity.ok(tutorService.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(tutorService.buscarPorIdOuFalhar(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTutor(@PathVariable Integer id){
        try{
            tutorService.deletar(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> atualizar (@PathVariable Integer id, @RequestBody TutorRequestDTO tutorDados){
        return ResponseEntity.ok(tutorService.atualizarTutor(id, tutorDados));
    }

}