package com.idotpet.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Resposta do upload de uma imagem")
public class UploadResponse {
    @Schema(description = "Nome gerado pelo backend para o arquivo", example = "550e8400-e29b-41d4-a716-446655440000.jpg")
    public String fileName;

    @Schema(description = "URL relativa para acesso publico a imagem", example = "/uploads/550e8400-e29b-41d4-a716-446655440000.jpg")
    public String url;

    @Schema(description = "Tipo MIME da imagem enviada", example = "image/jpeg")
    public String contentType;

    @Schema(description = "Tamanho do arquivo em bytes", example = "123456")
    public long size;

    public UploadResponse(String fileName, String url, String contentType, long size) {
        this.fileName = fileName;
        this.url = url;
        this.contentType = contentType;
        this.size = size;
    }
}
