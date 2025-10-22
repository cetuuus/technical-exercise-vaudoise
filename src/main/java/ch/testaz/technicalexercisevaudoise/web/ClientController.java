package ch.testaz.technicalexercisevaudoise.web;

import ch.testaz.technicalexercisevaudoise.dto.ClientDTO;
import ch.testaz.technicalexercisevaudoise.dto.ClientResponseDTO;
import ch.testaz.technicalexercisevaudoise.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clients", produces = "application/json")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientDTO dto) {
        ClientResponseDTO created = clientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ClientResponseDTO get(@PathVariable Long id) {
        return clientService.get(id);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ClientResponseDTO update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        return clientService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
