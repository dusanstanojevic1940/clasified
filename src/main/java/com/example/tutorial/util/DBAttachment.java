package com.example.tutorial.util;

import java.io.InputStream;

public class DBAttachment extends AttachmentStreamResponse {
    public DBAttachment(InputStream is, String args) {
        super(is, args);
        this.contentType = "application/octet-stream";
        this.extension = "db";
    }

    public DBAttachment(InputStream is) {
        super(is);
        this.contentType = "application/octet-stream";
        this.extension = "db";
    }
}