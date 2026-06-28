package com.idotpet.resource;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/uploads")
public class FileResource {

    @ConfigProperty(name = "app.upload.dir")
    String uploadDir;

    @GET
    @Path("/{nome}")
    public Response getFile(@PathParam("nome") String nome) {
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
