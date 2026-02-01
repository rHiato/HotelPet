package br.com.hotelpet.controller;
import br.com.hotelpet.dto.PetRequestDTO;
import br.com.hotelpet.dto.PetResponseDTO;
import br.com.hotelpet.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private AnimalService animalService;

    @GetMapping
    public List<PetResponseDTO> listarTodosOsPets() {
        return animalService.listaTodos();
    }

    @GetMapping("/{id}")
    public PetResponseDTO buscarPorId(@PathVariable Integer id){
        return animalService.buscarPorIdOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> adicionarPet(@RequestBody PetRequestDTO petsDados) {
       PetResponseDTO novoPet = animalService.adicionarPet(petsDados);
        return ResponseEntity.status(201).body(novoPet);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPet(@PathVariable Integer id) {
        try {
            animalService.deletarPet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> atualizarPet (@PathVariable Integer id, @RequestBody PetRequestDTO petsDados){
        return ResponseEntity.ok(animalService.atualizarPet(id, petsDados));
    }
}