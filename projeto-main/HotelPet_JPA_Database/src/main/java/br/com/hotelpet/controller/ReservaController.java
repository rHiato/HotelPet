package br.com.hotelpet.controller;
import br.com.hotelpet.dto.*;
import br.com.hotelpet.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired private ReservaService reservaService;

   @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody ReservaRequestDTO reservaDados) {
   reservaService.criarReserva(reservaDados);
   return ResponseEntity.status(201).body(reservaService.criarReserva(reservaDados));
   }
}