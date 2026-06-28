package com.idotpet.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Resposta padronizada para erros conhecidos da API")
public class ErrorResponse {
    @Schema(description = "Codigo HTTP do erro", example = "404")
    public int status;

    @Schema(description = "Descricao padrao do status HTTP", example = "Not Found")
    public String error;

    @Schema(description = "Mensagem detalhada do erro", example = "Animal nao encontrado")
    public String message;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
