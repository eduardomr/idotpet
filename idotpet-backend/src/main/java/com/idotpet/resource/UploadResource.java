package com.idotpet.resource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.jboss.resteasy.reactive.MultipartForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import com.idotpet.dto.UploadForm;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Path;

@Path("/upload")
public class UploadResource {

    private static final String UPLOAD_DIR = "/deployments/uploads/";

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@MultipartForm UploadForm form) {
        try {
            FileUpload file = form.file;

            String fileName = UUID.randomUUID().toString() + "_" + file.fileName();
            java.nio.file.Path path = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(path.getParent());
            System.out.println("Salvando em: " + path.toAbsolutePath());
            Files.copy(
                    Files.newInputStream(file.uploadedFile()),
                    path,
                    StandardCopyOption.REPLACE_EXISTING);

            String url = "http://localhost:8080/uploads/" + fileName;
            return Response.ok(url).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                    .entity("Erro: " + e.getMessage())
                    .build();
        }

    }
}
