package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISCIPLINA_Renan")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disciplina_id")
    private Integer id;

    @NotBlank(message = "Name must be not empty or null")
    @Column(name = "disciplina_name", nullable = false)
    private String name;

    @Column(name="data_atualizacao", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular", unique = true)
    private Professor titular;

    @PrePersist
    public void prePersist(){
        setDateTime(LocalDateTime.now());
    }

}