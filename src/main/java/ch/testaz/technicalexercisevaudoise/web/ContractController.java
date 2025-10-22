package ch.testaz.technicalexercisevaudoise.web;

import ch.testaz.technicalexercisevaudoise.dto.ContractDTO;
import ch.testaz.technicalexercisevaudoise.dto.ContractResponseDTO;
import ch.testaz.technicalexercisevaudoise.dto.UpdateAmountDTO;
import ch.testaz.technicalexercisevaudoise.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/", produces = "application/json")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping(path = "/clients/{clientId}/contracts", consumes = "application/json")
    public ResponseEntity<ContractResponseDTO> create(@PathVariable Long clientId, @Valid @RequestBody ContractDTO dto) {
        ContractResponseDTO created = contractService.create(clientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping(path = "/contracts/{id}/amount", consumes = "application/json")
    public ContractResponseDTO updateAmount(@PathVariable Long id, @Valid @RequestBody UpdateAmountDTO dto) {
        return contractService.updateAmount(id, dto.getAmount());
    }

    @GetMapping("/clients/{clientId}/contracts")
    public List<ContractResponseDTO> getActiveContracts(
            @PathVariable Long clientId,
            @RequestParam(value = "updatedFrom", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime updatedFrom,
            @RequestParam(value = "updatedTo", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime updatedTo
    ) {
        return contractService.listActiveForClient(clientId, updatedFrom, updatedTo);
    }

    @GetMapping("/clients/{clientId}/contracts/active/sum")
    public BigDecimal sumActive(@PathVariable Long clientId) {
        return contractService.sumActiveAmounts(clientId);
    }
}
