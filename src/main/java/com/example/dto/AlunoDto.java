package com.example.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AlunoDto {
    private String nomeAluno;
    private int idAluno;


    public AlunoDto(String nomeAluno, int idAluno) {
        this.nomeAluno = nomeAluno;
        this.idAluno = idAluno;
    }

    public AlunoDto() {

    }

    
    public String getNomeAluno() {
        return nomeAluno;
    }


    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }


    public int getIdAluno() {
        return idAluno;
    }


    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlunoDto alunoDto = (AlunoDto) o;
        return idAluno == alunoDto.idAluno && Objects.equals(nomeAluno, alunoDto.nomeAluno);
    }

    @Override
    public int hashCode() {
    
        return Objects.hash(idAluno, nomeAluno);
    }

    @Override
    public String toString() {
        return "AlunoDto{" +
        "idAluno=" + idAluno +
        ", nomeAluno='" + nomeAluno + '\'' +
        '}';
}
    }


    
