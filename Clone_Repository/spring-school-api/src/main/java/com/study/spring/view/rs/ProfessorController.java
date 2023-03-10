package com.study.spring.view.rs;

import com.study.spring.dto.ProfessorDto;
import com.study.spring.service.ProfessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Slf4j
public class ProfessorController {

    private final ProfessorService service;

    @Autowired
    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDto>> listProfessors() {

        final List<ProfessorDto> professorDtoList = service.retrieveAll();

        return ResponseEntity.ok(professorDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getProfessor(@PathVariable("id") int id) {

        final ProfessorDto professorDto = service.getById(id);

        return ResponseEntity.ok(professorDto);
    }

    @PostMapping
    public ResponseEntity<Void> saveProfessor(@RequestBody final ProfessorDto professor) {

        service.save(professor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable("id") int id, @RequestBody ProfessorDto professor) {

        service.update(id, professor);
        return ResponseEntity
                .ok(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProfessor(@PathVariable("id") int id) {

        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
