package com.example.mapper;


import com.example.dto.AlunoResponse;
import com.example.dto.TutorResponse;
import com.example.model.Aluno;
import com.example.model.Professor;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


        Objects.requireNonNull(entity, "Entidade deve ser preenchida");
        // if (Objects.isNull(entity)) return null;
        var formatter = DateTimeFormatter.ofPattern("dd=MM-YYYY hh:mm:ss");
        return  AlunoResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();
    }

    public TutorResponse toResponse(Professor entity){
        if (Objects.isNull(entity)) return null;

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TutorResponse.builder()
                .tutor(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();
    }

    // public Aluno toEntity(AlunoRequest request) {
    //      if (Objects.isNull(request)) {
    //          return null;
    //      } else {
    //          return Aluno.builder()
    //                  .name(request.getName())
    //                  .build();
    //      }
    // }
}
