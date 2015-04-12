package com.example.tutorial.util;

import java.io.InputStream;

public class CSVAttachment extends AttachmentStreamResponse {
    public CSVAttachment(InputStream is, String args) {
        super(is, args);
        this.contentType = "application/vnd.ms-excel";
        this.extension = "csv";
    }

    public CSVAttachment(InputStream is) {
        super(is);
        this.contentType = "application/vnd.ms-excel";
        this.extension = "csv";
    }
}