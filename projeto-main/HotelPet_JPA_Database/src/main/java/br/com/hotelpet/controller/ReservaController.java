package br.com.hotelpet.controller;
import br.com.hotelpet.dto.*;
import br.com.hotelpet.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired private ReservaService reservaService;

    @GetMapping
    public List<ReservaResponseDTO> listarTodosAsReservas(){
        return reservaService.listarReservas();
    }

    @GetMapping("/{id}")
    public ReservaResponseDTO buscarPorid(@PathVariable Integer id){
        return reservaService.buscarPorIdOuFalhar(id);
    }

   @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody ReservaRequestDTO reservaDados) {
   return ResponseEntity.status(201).body(reservaService.criarReserva(reservaDados));
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> CancelarReserva(@PathVariable Integer id){
        try {
            reservaService.deletarReserva(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
   }

   @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizarReserva(@PathVariable Integer id,@RequestBody ReservaRequestDTO reservaDados){

        return ResponseEntity.ok(reservaService.atualizarReserva(id,reservaDados));
   }
}