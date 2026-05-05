package com.idotpet.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/uploads")
public class FileResource {

    private static final String UPLOAD_DIR = "/deployments/uploads/";

    @GET
    @Path("/{nome}")
    public Response getFile(@PathParam("nome") String nome) {
        try {
            java.nio.file.Path path = Paths.get(UPLOAD_DIR + nome);

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