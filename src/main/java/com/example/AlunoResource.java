package com.example;

import com.example.dto.AlunoRequest;
import com.example.dto.ErrorResponse;
import com.example.service.AlunoService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/aluno")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

    private final AlunoService service;

    @Inject
    public AlunoResource(AlunoService service) {
        this.service = service;
    }

    @GET
    public Response listAlunos() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAluno(@PathParam("id") int id) {

        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    @POST
    public Response saveAluno(final AlunoRequest Aluno) {

        try {
            final var response = service.save(Aluno);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        } catch (ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }

    }

    @PUT
    @Path("/{id}")
    public Response updateAluno(@PathParam("id") int id, AlunoRequest Aluno) {
        try {
            var response = service.update(id, Aluno);

            return Response
                    .ok(response)
                    .build();

        } catch (ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeAluno(@PathParam("id") int id) {

        service.delete(id);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @PATCH
    @Path("/{id}/tutor/{idProfessor}")
    public Response updateTutor(@PathParam("id") int idAluno, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTutor(idAluno, idProfessor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

}