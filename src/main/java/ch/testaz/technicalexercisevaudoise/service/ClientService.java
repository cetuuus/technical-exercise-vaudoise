package ch.testaz.technicalexercisevaudoise.service;

import ch.testaz.technicalexercisevaudoise.dto.ClientDTO;
import ch.testaz.technicalexercisevaudoise.dto.ClientResponseDTO;
import ch.testaz.technicalexercisevaudoise.model.Client;
import ch.testaz.technicalexercisevaudoise.model.Contract;
import ch.testaz.technicalexercisevaudoise.repository.ClientRepository;
import ch.testaz.technicalexercisevaudoise.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;

    public ClientService(ClientRepository clientRepository, ContractRepository contractRepository) {
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
    }

    @Transactional
    public ClientResponseDTO create(ClientDTO dto) {
        validateTypeSpecificFields(dto);
        Client client = toEntity(dto, new Client());
        Client saved = clientRepository.save(client);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO get(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        return toResponse(client);
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        // type is immutable too? Spec not explicit, but we allow changing type if constraints met
        // Birthdate and companyIdentifier are immutable per spec
        validateUpdateImmutables(client, dto);
        // Update mutable fields only
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        // Do not update birthDate/companyIdentifier
        Client saved = clientRepository.save(client);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        // Update all contracts end date to now
        LocalDateTime now = LocalDateTime.now();
        for (Contract c : contractRepository.findByClient_Id(client.getId())) {
            c.setEndDate(now);
        }
        // flush contracts via repository saveAll implicitly by dirty checking
        clientRepository.delete(client);
    }

    private void validateTypeSpecificFields(ClientDTO dto) {
        if (dto.getType() == Client.ClientType.PERSON) {
            if (dto.getBirthDate() == null) {
                throw new ValidationException("birthDate is required for PERSON");
            }
            dto.setCompanyIdentifier(null); // ignore if provided
        } else if (dto.getType() == Client.ClientType.COMPANY) {
            if (dto.getCompanyIdentifier() == null || !dto.getCompanyIdentifier().matches("^[A-Za-z]{3}-[0-9]{3}$")) {
                throw new ValidationException("companyIdentifier is required for COMPANY and must match pattern aaa-123");
            }
            dto.setBirthDate(null); // ignore if provided
        }
    }

    private void validateUpdateImmutables(Client existing, ClientDTO incoming) {
        if (existing.getBirthDate() != null && incoming.getBirthDate() != null && !existing.getBirthDate().equals(incoming.getBirthDate())) {
            throw new ValidationException("birthDate cannot be updated");
        }
        if (existing.getCompanyIdentifier() != null && incoming.getCompanyIdentifier() != null && !existing.getCompanyIdentifier().equals(incoming.getCompanyIdentifier())) {
            throw new ValidationException("companyIdentifier cannot be updated");
        }
    }

    private Client toEntity(ClientDTO dto, Client target) {
        target.setName(dto.getName());
        target.setEmail(dto.getEmail());
        target.setPhone(dto.getPhone());
        target.setType(dto.getType());
        target.setBirthDate(dto.getBirthDate());
        target.setCompanyIdentifier(dto.getCompanyIdentifier());
        return target;
    }

    private ClientResponseDTO toResponse(Client client) {
        ClientResponseDTO r = new ClientResponseDTO();
        r.setId(client.getId());
        r.setName(client.getName());
        r.setEmail(client.getEmail());
        r.setPhone(client.getPhone());
        r.setBirthDate(client.getBirthDate());
        r.setCompanyIdentifier(client.getCompanyIdentifier());
        r.setType(client.getType());
        return r;
    }

    // Simple custom exceptions
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) { super(message); }
    }
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) { super(message); }
    }
}
