package com.idotpet.dto;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class UploadForm {
    @RestForm("file")
    public FileUpload file;

}
