package ch.testaz.technicalexercisevaudoise.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "company_identifier")
    private String companyIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientType type;

    @OneToMany(mappedBy = "client")
    private List<Contract> contracts;

    @PreRemove
    private void onPreRemove() {
        if (contracts != null && !contracts.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            for (Contract c : contracts) {
                c.setEndDate(now);
                c.setUpdatedDate(now);
                c.setClient(null);
            }
        }
    }

    public enum ClientType {
        PERSON,
        COMPANY
    }
}
