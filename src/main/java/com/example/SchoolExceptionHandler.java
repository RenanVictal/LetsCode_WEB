package com.example;

import com.example.dto.ErrorMessage;
import com.example.exception.NotAllowedNameExcption;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class SchoolExceptionHandler implements ExceptionMapper<Exception> {


    @Override
    public Response toResponse(Exception exception) {
        log.error("Exception {}", exception.getMessage());
        if (exception instanceof EntityNotFoundException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorMessage.builder()
                            .message(exception.getMessage())
                            .build())
                    .build();
        }

        if (exception instanceof NotAllowedNameExcption) {
            return Response
                    .status(422)
                    .entity(ErrorMessage.builder()
                            .message(exception.getMessage())
                            .build())
                    .build();
        }


        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorMessage.builder()
                        .message("Contact our support and provide the message: " + exception.getMessage())
                        .build())
                .build();
    }
}
