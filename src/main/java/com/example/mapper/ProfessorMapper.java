package com.example.mapper;

import com.example.dto.ProfessorResponse;
import com.example.model.Professor;


import javax.enterprise.context.ApplicationScoped;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProfessorMapper {
    public List<ProfessorResponse> toResponse(List<Professor> listOfProfessors) {

        if (Objects.isNull(listOfProfessors)) return new ArrayList<>();

        return listOfProfessors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProfessorResponse toResponse(Professor entity) {

        Objects.requireNonNull(entity, "Entity must be not null");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm:ss");
        return  ProfessorResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();
    }
}
