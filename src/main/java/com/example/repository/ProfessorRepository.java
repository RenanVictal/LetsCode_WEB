package com.example.repository;

import com.example.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepositoryBase<Professor, Integer> {
}