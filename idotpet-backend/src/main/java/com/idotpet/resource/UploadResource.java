package com.idotpet.resource;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import com.idotpet.dto.ErrorResponse;
import com.idotpet.dto.UploadResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/upload")
public class UploadResource {

    @ConfigProperty(name = "app.upload.dir")
    String uploadDir;

    @ConfigProperty(name = "app.upload.public-path")
    String publicPath;

    @ConfigProperty(name = "app.upload.max-size-bytes")
    long maxSizeBytes;

    @ConfigProperty(name = "app.upload.allowed-content-types")
    String allowedContentTypes;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@RestForm("file") FileUpload file) {
        try {
            if (file == null || file.size() == 0) {
                return error(Response.Status.BAD_REQUEST, "Arquivo não enviado");
            }

            if (file.size() > maxSizeBytes) {
                return error(Response.Status.BAD_REQUEST, "Imagem excede o tamanho máximo permitido");
            }

            String contentType = resolveContentType(file);
            if (!allowedTypes().contains(contentType)) {
                return error(Response.Status.UNSUPPORTED_MEDIA_TYPE, "Tipo de imagem não suportado");
            }

            String fileName = UUID.randomUUID() + extensionFor(contentType);
            java.nio.file.Path uploadDirectory = java.nio.file.Path.of(uploadDir).toAbsolutePath().normalize();
            java.nio.file.Path target = uploadDirectory.resolve(fileName).normalize();

            if (!target.startsWith(uploadDirectory)) {
                return error(Response.Status.BAD_REQUEST, "Nome de arquivo inválido");
            }

            Files.createDirectories(uploadDirectory);
            Files.copy(file.uploadedFile(), target, StandardCopyOption.REPLACE_EXISTING);

            String url = normalizedPublicPath() + "/" + fileName;
            return Response.ok(new UploadResponse(fileName, url, contentType, file.size())).build();

        } catch (Exception e) {
            e.printStackTrace();
            return error(Response.Status.INTERNAL_SERVER_ERROR, "Erro ao salvar imagem");
        }
    }

    private String resolveContentType(FileUpload file) throws Exception {
        String contentType;
        if (file.contentType() != null && !file.contentType().isBlank()) {
            contentType = file.contentType();
        } else {
            contentType = Files.probeContentType(file.uploadedFile());
        }

        if (contentType == null) {
            return null;
        }

        return contentType.split(";")[0].trim().toLowerCase(Locale.ROOT);
    }

    private Set<String> allowedTypes() {
        return Arrays.stream(allowedContentTypes.split(","))
                .map(String::trim)
                .filter(type -> !type.isBlank())
                .collect(Collectors.toSet());
    }

    private String normalizedPublicPath() {
        if (publicPath.endsWith("/")) {
            return publicPath.substring(0, publicPath.length() - 1);
        }
        return publicPath;
    }

    private String extensionFor(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }

    private Response error(Response.Status status, String message) {
        ErrorResponse error = new ErrorResponse(
                status.getStatusCode(),
                status.getReasonPhrase(),
                message);

        return Response.status(status)
                .entity(error)
                .build();
    }
}
