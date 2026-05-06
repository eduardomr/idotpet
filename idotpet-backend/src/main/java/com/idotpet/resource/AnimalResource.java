package com.idotpet.resource;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

import com.idotpet.dto.AnimalRequest;
import com.idotpet.dto.AnimalResponse;
import com.idotpet.service.AnimalService;

@Path("/animais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnimalResource {

    @Inject
    AnimalService service;

    @POST
    public Response criar(@Valid AnimalRequest dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    @GET
    public List<AnimalResponse> listar(@QueryParam("page") @DefaultValue("0") int page,
                              @QueryParam("size") @DefaultValue("10") int size) {
        return service.listar(page, size);
    }

    @GET
    @Path("/{id}")
    public AnimalResponse buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}