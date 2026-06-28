package com.idotpet.resource;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/uploads")
@Tag(name = "Uploads", description = "Upload e disponibilizacao de imagens dos animais")
public class FileResource {

    @ConfigProperty(name = "app.upload.dir")
    String uploadDir;

    @GET
    @Path("/{nome}")
    @Operation(summary = "Acessar imagem enviada", description = "Retorna uma imagem previamente enviada pelo endpoint de upload.")
    @APIResponse(responseCode = "200", description = "Imagem encontrada")
    @APIResponse(responseCode = "400", description = "Nome de arquivo invalido")
    @APIResponse(responseCode = "404", description = "Imagem nao encontrada")
    public Response getFile(
            @Parameter(description = "Nome do arquivo gerado no upload", required = true, example = "550e8400-e29b-41d4-a716-446655440000.jpg")
            @PathParam("nome") String nome) {
        try {
            java.nio.file.Path uploadDirectory = Paths.get(uploadDir).toAbsolutePath().normalize();
            java.nio.file.Path path = uploadDirectory.resolve(nome).normalize();

            if (!path.startsWith(uploadDirectory)) {
                return Response.status(400).build();
            }

            if (!Files.exists(path)) {
                return Response.status(404).build();
            }

            String contentType = Files.probeContentType(path);

            return Response.ok(path.toFile(),contentType).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
