package com.example.demo.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Skill extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_generator")
    @SequenceGenerator(name="skill_generator", sequenceName = "skill_seq", allocationSize=50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String description;

    private Integer duration;

    private LocalDate lastUsed;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="employee_id", referencedColumnName = "id")
    private Employee employee;
}
