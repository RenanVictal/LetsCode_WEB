package com.example.service;

import com.example.dto.AlunoRequest;
import com.example.dto.AlunoResponse;
import com.example.mapper.AlunoMapper;
import com.example.model.Aluno;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor

public class AlunoService {
    private final AlunoMapper mapper;

    public List<AlunoResponse> retrieveAll() {
        log.info("Listing Alunos");
        final List<Aluno> listOfAlunos = Aluno.listAll();
        return  mapper.toResponse(listOfAlunos);
    }

    public AlunoResponse getById(int id) {
        log.info("Getting Aluno id-{}", id);

        Aluno aluno = Aluno.findById(id);
        return mapper.toResponse(aluno);
    }

    @Transactional
    public AlunoResponse save(AlunoRequest alunoRequest) {

        log.info("Saving Aluno - {}", alunoRequest);

        Aluno entity =
                Aluno.builder()
                .name(alunoRequest.getName())
                .build();

        entity.persistAndFlush();

        return mapper.toResponse(entity);
    }

    @Transactional
    public AlunoResponse update(int id, AlunoRequest alunoRequest) {

        log.info("Updating Aluno id - {}, data - {}", id, alunoRequest);

        Optional<Aluno> aluno = Aluno.findByIdOptional(id);

        if (aluno.isPresent()) {
            var entity = aluno.get();
            entity.setName(alunoRequest.getName());
            entity.persistAndFlush();
            return mapper.toResponse(entity);
        }

        return new AlunoResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting Aluno id - {}", id);
        Aluno.findByIdOptional(id).ifPresent(PanacheEntityBase::delete);
    }
}
