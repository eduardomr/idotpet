package com.idotpet.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

import com.idotpet.dto.AnimalRequest;
import com.idotpet.dto.AnimalResponse;
import com.idotpet.dto.ErrorResponse;
import com.idotpet.service.AnimalService;

@Path("/animais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animais", description = "Cadastro, listagem, busca e exclusao de animais para adocao")
public class AnimalResource {

    @Inject
    AnimalService service;

    @POST
    @Operation(summary = "Cadastrar animal", description = "Cadastra um novo animal disponivel para adocao. O animal pode ter ate 5 imagens.")
    @APIResponse(responseCode = "201", description = "Animal cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = AnimalResponse.class)))
    @APIResponse(responseCode = "400", description = "Dados invalidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public Response criar(
            @RequestBody(description = "Dados do animal a ser cadastrado", required = true,
                    content = @Content(schema = @Schema(implementation = AnimalRequest.class)))
            @Valid AnimalRequest dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    @GET
    @Operation(summary = "Listar animais", description = "Lista animais cadastrados com paginacao simples.")
    @APIResponse(responseCode = "200", description = "Lista de animais retornada com sucesso",
            content = @Content(schema = @Schema(implementation = AnimalResponse.class)))
    public List<AnimalResponse> listar(
            @Parameter(description = "Pagina iniciando em 0", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Quantidade de itens por pagina", example = "10")
            @QueryParam("size") @DefaultValue("10") int size) {
        return service.listar(page, size);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar animal por ID", description = "Retorna os dados de um animal cadastrado.")
    @APIResponse(responseCode = "200", description = "Animal encontrado",
            content = @Content(schema = @Schema(implementation = AnimalResponse.class)))
    @APIResponse(responseCode = "404", description = "Animal nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public AnimalResponse buscar(
            @Parameter(description = "ID do animal", required = true, example = "1")
            @PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir animal", description = "Remove um animal cadastrado pelo ID.")
    @APIResponse(responseCode = "204", description = "Animal removido com sucesso")
    @APIResponse(responseCode = "404", description = "Animal nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public void deletar(
            @Parameter(description = "ID do animal", required = true, example = "1")
            @PathParam("id") Long id) {
        service.deletar(id);
    }
}
