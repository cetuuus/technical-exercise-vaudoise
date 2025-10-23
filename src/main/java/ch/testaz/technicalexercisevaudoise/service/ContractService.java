package ch.testaz.technicalexercisevaudoise.service;

import ch.testaz.technicalexercisevaudoise.dto.ContractDTO;
import ch.testaz.technicalexercisevaudoise.dto.ContractResponseDTO;
import ch.testaz.technicalexercisevaudoise.model.Client;
import ch.testaz.technicalexercisevaudoise.model.Contract;
import ch.testaz.technicalexercisevaudoise.repository.ClientRepository;
import ch.testaz.technicalexercisevaudoise.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;

    public ContractService(ContractRepository contractRepository, ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public ContractResponseDTO create(Long clientId, ContractDTO dto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientService.NotFoundException("Client not found"));
        Contract c = new Contract();
        c.setClient(client);
        if (dto.getStartDate() != null) {
            c.setStartDate(dto.getStartDate());
        } else {
            c.setStartDate(LocalDateTime.now());
        }
        c.setEndDate(dto.getEndDate());
        c.setAmount(dto.getAmount());
        c.setUpdatedDate(LocalDateTime.now());
        Contract saved = contractRepository.save(c);
        return toResponse(saved);
    }

    @Transactional
    public ContractResponseDTO updateAmount(Long contractId, BigDecimal newAmount) {
        Contract c = contractRepository.findById(contractId)
                .orElseThrow(() -> new ClientService.NotFoundException("Contract not found"));
        // Do not allow updates on ended contracts (endDate in the past or now)
        LocalDateTime now = LocalDateTime.now();
        if (c.getEndDate() != null && !c.getEndDate().isAfter(now)) {
            throw new ClientService.ValidationException("Cannot update amount of an ended contract");
        }
        c.setAmount(newAmount);
        // updatedDate will be handled by auditing @PreUpdate or set now
        c.setUpdatedDate(now);
        Contract saved = contractRepository.save(c);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDTO> listActiveForClient(Long clientId, LocalDateTime updatedFrom, LocalDateTime updatedTo) {
       List<Contract> contracts;
       LocalDateTime now = LocalDateTime.now();
       
        if (updatedFrom != null && updatedTo != null) {
            contracts = contractRepository.findActiveByClientIdAndUpdatedBetween(clientId, now, updatedFrom, updatedTo);
       } else if (updatedFrom != null) {
            contracts = contractRepository.findByClientIdAndUpdatedFrom(clientId, now, updatedFrom);
        } else if (updatedTo != null) {
            contracts = contractRepository.findByClientIdAndUpdatedTo(clientId, now, updatedTo);
        } else {
            contracts = contractRepository.findActiveByClientId(clientId, now);
        }
        
        return contracts.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BigDecimal sumActiveAmounts(Long clientId) {
        return contractRepository.sumAmountOfActiveByClientId(clientId, LocalDateTime.now());
    }

    private ContractResponseDTO toResponse(Contract c) {
        ContractResponseDTO r = new ContractResponseDTO();
        r.setId(c.getId());
        r.setClientId(c.getClient().getId());
        r.setStartDate(c.getStartDate());
        r.setEndDate(c.getEndDate());
        r.setAmount(c.getAmount());
        return r;
    }
}
