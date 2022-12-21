package com.example.mapper;

import com.example.dto.AlunoRequest;
import com.example.dto.AlunoResponse;
import com.example.model.Aluno;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped

public class AlunoMapper {
    public List<AlunoResponse> toResponse(List<Aluno> listOfAlunos) {

        if (Objects.isNull(listOfAlunos)) return new ArrayList<>();

        return listOfAlunos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AlunoResponse toResponse(Aluno entity) {

        if (Objects.isNull(entity)) return null;

        return  AlunoResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
    }

    public Aluno toEntity(AlunoRequest request) {
         if (Objects.isNull(request)) {
             return null;
         } else {
             return Aluno.builder()
                     .name(request.getName())
                     .build();
         }
    }
}
