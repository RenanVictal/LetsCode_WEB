package com.example.service;

import com.example.dto.AlunoRequest;
import com.example.dto.AlunoResponse;
import com.example.mapper.AlunoMapper;
import com.example.model.Aluno;
import com.example.exception.NotAllowedNameExcption;
// import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import com.example.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor

public class AlunoService {
    private final AlunoMapper mapper;
    private final AlunoRepository repository;

    public List<AlunoResponse> retrieveAll() {
        log.info("Listing Alunos");
        // final List<Aluno> listOfAlunos = Aluno.listAll();
        final List<Aluno> listOfAlunos = repository.listAll();
        return mapper.toResponse(listOfAlunos);
    }

    public AlunoResponse getById(int id) {
        log.info("Getting Aluno id-{}", id);

        // Aluno aluno = Aluno.findById(id);
        Aluno aluno = repository.findById(id);
        return mapper.toResponse(aluno);
    }

    @Transactional

    public AlunoResponse save(@Valid AlunoRequest alunoRequest) {

        Objects.requireNonNull(alunoRequest, "É necessário adicionar um nome");

        log.info("Saving Aluno - {}", alunoRequest);

        if (alunoRequest.getName().equals("AAA")) {
            throw new NotAllowedNameExcption("The name AAA is not allowed");
        }

        Aluno entity = Aluno.builder()
                .name(alunoRequest.getName())
                .build();

        // entity.persistAndFlush();
        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional

    public AlunoResponse update(int id, @Valid AlunoRequest alunoRequest) {

        Objects.requireNonNull(alunoRequest, "É necessário adicionar um nome");

        log.info("Updating Aluno id - {}, data - {}", id, alunoRequest);

        // Optional<Aluno> aluno = Aluno.findByIdOptional(id);
        Optional<Aluno> aluno = repository.findByIdOptional(id);

        aluno.orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        var entity = aluno.get();
        entity.setName(alunoRequest.getName());
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting Aluno id - {}", id);
        // Aluno.findByIdOptional(id).ifPresent(PanacheEntityBase::delete);
        Optional<Aluno> aluno = repository.findByIdOptional(id);
        aluno.ifPresent(repository::delete);
    }
}
