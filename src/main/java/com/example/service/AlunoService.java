package com.example.service;
import com.example.mapper.AlunoMapper;
import com.example.model.Aluno;
import com.example.dto.AlunoRequest;
import com.example.dto.AlunoResponse;
import com.example.dto.TutorResponse;
import com.example.exception.InvalidStateException;
import com.example.exception.NotAllowedNameExcption;
// import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import com.example.repository.AlunoRepository;
import com.example.repository.ProfessorRepository;

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
    private final ProfessorRepository professorRepository;

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
    public TutorResponse updateTutor(int idAluno, int idProfessor) {

        log.info("Updating titular disciplina-id: {}, professor-id: {}", idAluno, idProfessor);

        //find entities
        var aluno = repository.findById(idAluno);
        var professor = professorRepository.findById(idProfessor);

        //validate is not empty
        if (Objects.isNull(aluno)) throw new EntityNotFoundException("Aluno not found");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor not found");

        //verify if Professor has no Disciplina

        //Update
        aluno.setTutor(professor);;
        repository.persist(aluno);

        return mapper.toResponse(professor);
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting Aluno id - {}", id);
        // Aluno.findByIdOptional(id).ifPresent(PanacheEntityBase::delete);
        Optional<Aluno> aluno = repository.findByIdOptional(id);
        aluno.ifPresent(repository::delete);
    }

    public List<AlunoResponse> getTutoradosByProfessorId(int idProfessor) {

        log.info("Getting tutorados by professor-id: {}", idProfessor);

        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor not found");

        List<Aluno> listOfEntities = repository.getTutoradosByProfessor(professor);

        return mapper.toResponse(listOfEntities);
    }
}
