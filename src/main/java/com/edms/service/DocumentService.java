package com.edms.service;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.edms.dto.DocumentDTO;
public interface DocumentService {
	
	public String uploadDocument(MultipartFile file, String title, String author);
	public String uploadNewVersion(String documentId, MultipartFile file, String author);
	public DocumentDTO getDocument(String documentId);
	public DocumentDTO getDocumentVersion(String documentId, int version);
	public String updateDocument(String documentId,String title, String author);
	public String deleteDocument(String documentId);
	public String deleteDocumentVersion(String documentId, int version);
	public List<DocumentDTO> getAllDocuments();
}
