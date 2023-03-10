package com.example.service;

import com.example.dto.ProfessorRequest;
import com.example.dto.ProfessorResponse;
import com.example.exception.NotAllowedNameExcption;
import com.example.mapper.ProfessorMapper;
import com.example.model.Professor;
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
public class ProfessorService {

    private final ProfessorMapper mapper;
    private final ProfessorRepository repository;

    public List<ProfessorResponse> retrieveAll() {
        log.info("Listing professors");
        final List<Professor> listOfProfessors = repository.listAll();
        return  mapper.toResponse(listOfProfessors);
    }

    public ProfessorResponse getById(int id) {
        log.info("Getting professor id-{}", id);

        Professor professor = repository.findById(id);
        return mapper.toResponse(professor);
    }

    @Transactional
    public ProfessorResponse save(@Valid ProfessorRequest professorRequest) {

        Objects.requireNonNull(professorRequest, "request must not be null");

        log.info("Saving professor - {}", professorRequest);


        if (professorRequest.getName().equals("AAA")) {
            throw new NotAllowedNameExcption("The name AAA is not allowed");
        }

        Professor entity =
                Professor.builder()
                .name(professorRequest.getName())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public ProfessorResponse update(int id, @Valid ProfessorRequest professorRequest) {

        Objects.requireNonNull(professorRequest, "request must not be null");

        log.info("Updating professor id - {}, data - {}", id, professorRequest);

        Optional<Professor> professor = repository.findByIdOptional(id);

        professor.orElseThrow(() -> new EntityNotFoundException("Professor not found."));

        var entity = professor.get();
        entity.setName(professorRequest.getName());
        return mapper.toResponse(entity);
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting professor id - {}", id);
        Optional<Professor> professor = repository.findByIdOptional(id);
        professor.ifPresent(repository::delete);
    }
}
