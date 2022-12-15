package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dto.AlunoDto;


// import io.vertx.core.impl.logging.Logger;
// import io.vertx.core.impl.logging.LoggerFactory;



@Path("/aluno")
public class AlunoResource {

   private final Map<Integer, AlunoDto> mapAluno = new HashMap<>();

   private static final Logger log = LoggerFactory.getLogger(AlunoResource.class);

   @GET   
   @Consumes(MediaType.APPLICATION_JSON)
   public Response listAlunos(){
      log.info("Listing alunos");
      if (mapAluno.isEmpty()){
         return Response.ok().build();
      }

      else {
         return Response.ok(new ArrayList<>(mapAluno.values())).build();
      }
   }

   // List<AlunoDto> alunoDtoList = new ArrayList<>(mapAluno.values());

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response saveAluno(AlunoDto aluno){
      if (Objects.isNull(aluno)){
         log.error("Invalid body - null");
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      else {
         mapAluno.put(aluno.getIdAluno(), aluno);
         log.info("Inserted a new aluno {}", aluno);
         return Response.status(Response.Status.CREATED).build();
      }

   }

   @PUT
   @Path("/{idAluno}")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response getAluno(@PathParam("idAluno") int id, AlunoDto alunoDto){
      log.info("Updating aluno {}", id);
      var aluno = mapAluno.get(id);
      if (Objects.isNull(aluno)){
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      else {
         mapAluno.put(id, alunoDto);
         return Response.ok(alunoDto).build();
      }
   }

   // @GET   
   // @Consumes(MediaType.APPLICATION_JSON)
   // public Response getAluno(){
   //    log.info("Getting alunos");
   //    var aluno = mapAluno.get(listAlunos());
   //    if (Objects.isNull(aluno)){
   //       return Response.status(Response.Status.NOT_FOUND).build();
   //    }
   //    else {
   //       return Response.ok(aluno).build();
   //    }            
   // }

   @GET
   @Path("/{idAluno}")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response getAluno(@PathParam("idAluno") int id){
      log.info("Getting aluno {} ", id);
      var aluno = mapAluno.get(id);
      if (Objects.isNull(aluno)){
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      else {
         return Response.ok(aluno).build();
      }            
   }

   @DELETE
   @Path("/{idAluno}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeAluno(@PathParam("idAluno") int id){
      log.info("Deleting aluno {}", id);
      var aluno = mapAluno.get(id);
      if (Objects.isNull(aluno)){
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      else {
         mapAluno.remove(id);
         return Response.status(Response.Status.NO_CONTENT).build();
      }
   }


}