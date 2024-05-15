package com.edms.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.edms.entity.DocumentVersionEntity;

public interface DocumentVersionService {
	public DocumentVersionEntity uploadDocumentVersion(MultipartFile file, String author, int latestVersion) throws IOException;
}