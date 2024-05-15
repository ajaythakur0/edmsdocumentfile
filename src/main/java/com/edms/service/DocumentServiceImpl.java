package com.edms.service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.edms.dto.DocumentDTO;
import com.edms.dto.DocumentMetadataDTO;
import com.edms.dto.DocumentVersionDTO;
import com.edms.entity.DocumentEntity;
import com.edms.entity.DocumentMetadataEntity;
import com.edms.entity.DocumentVersionEntity;
import com.edms.exception.DocumentNotFoundException;
import com.edms.repository.DocumentRepository;
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentVersionService documentVersionService;
    @Override
    public String uploadDocument(MultipartFile file, String title, String author) {
        DocumentVersionEntity documentVersionEntity = null;
		try {
			documentVersionEntity = documentVersionService.uploadDocumentVersion(file, author, 0);
		} catch (IOException e) {
			System.out.print("// TODO Auto-generated catch block:Inside DocumentServiceImpl line 35");
			e.printStackTrace();
		}

        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
        documentEntity.setTitle(title);
        documentEntity.setLatestVersion(documentVersionEntity.getVersion());
        documentEntity.setLatestMetadata(documentVersionEntity.getMetadata());
        documentEntity.setVersionHistory(List.of(documentVersionEntity));

        documentRepository.save(documentEntity);
        return "Document uploaded successfully: " + documentEntity;
    }

    @Override
    public String uploadNewVersion(String documentId, MultipartFile file, String author) {
        DocumentEntity documentEntity = null;
		try {
			documentEntity = documentRepository.findById(documentId)
			        .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + documentId));
		} catch (DocumentNotFoundException e) {
			System.out.print("// TODO Auto-generated catch block:Inside DocumentServiceImpl line 57");
			e.printStackTrace();
		}

        DocumentVersionEntity documentVersionEntity = null;
		try {
			documentVersionEntity = documentVersionService.uploadDocumentVersion(file, author, documentEntity.getLatestVersion());
		} catch (IOException e) {
			System.out.print("// TODO Auto-generated catch block:Inside DocumentServiceImpl line 65");
			e.printStackTrace();
		}
        documentEntity.getVersionHistory().add(documentVersionEntity);
        documentEntity.setLatestVersion(documentVersionEntity.getVersion());
        documentEntity.setLatestMetadata(documentVersionEntity.getMetadata());

        documentRepository.save(documentEntity);
        return "New version uploaded successfully for document: " + documentEntity;
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------


	@Override
	public DocumentDTO getDocument(String documentId) {
		DocumentDTO documentDTO=new DocumentDTO();
		DocumentEntity documentEntity = null;
		try {
			documentEntity = documentRepository.findById(documentId)
			        .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + documentId));
		} catch (DocumentNotFoundException e) {
			System.out.print("// TODO Auto-generated catch block:Inside DocumentServiceImpl line 89");
			e.printStackTrace();
		}
		documentDTO.setId(documentEntity.getId());
		documentDTO.setTitle(documentEntity.getTitle());
		documentDTO.setLatestVersion(documentEntity.getLatestVersion());
		documentDTO.setLatestMetadata(documentMetadataEntityToDTO(documentEntity.getLatestMetadata()));
		documentDTO.setVersionHistory(documentVersionEntityToDTO(documentEntity.getVersionHistory(),documentEntity.getId()));
		return documentDTO;
	}


	public List<DocumentVersionDTO> documentVersionEntityToDTO(List<DocumentVersionEntity> documentVersionEntity, String documentId) {
	    List<DocumentVersionDTO> documentVersionDTOList = new ArrayList<>();
	    for (DocumentVersionEntity versionEntity : documentVersionEntity) {
	        DocumentVersionDTO documentVersionDTO = new DocumentVersionDTO();
	        documentVersionDTO.setVersion(versionEntity.getVersion());
	        documentVersionDTO.setFileLocation("Saved In The Database. Try Access with-: http://localhost:8080/document/"+documentId+"/"+versionEntity.getVersion());
	        documentVersionDTO.setMetadata(documentMetadataEntityToDTO(versionEntity.getMetadata()));
	        documentVersionDTOList.add(documentVersionDTO);
	    }
	    return documentVersionDTOList;
	}


	public DocumentMetadataDTO documentMetadataEntityToDTO(DocumentMetadataEntity documentMetadataEntity) {
	    DocumentMetadataDTO documentMetadataDTO = new DocumentMetadataDTO();
	    documentMetadataDTO.setFilename(documentMetadataEntity.getFilename());
	    documentMetadataDTO.setFileSize(documentMetadataEntity.getFileSize());
	    documentMetadataDTO.setFileType(documentMetadataEntity.getFileType());
	    documentMetadataDTO.setAuthor(documentMetadataEntity.getAuthor());
	    documentMetadataDTO.setCreatedBy(documentMetadataEntity.getCreatedBy());
	    documentMetadataDTO.setModifiedBy(documentMetadataEntity.getModifiedBy());
	    documentMetadataDTO.setCreatedAt(documentMetadataEntity.getCreatedAt());
	    documentMetadataDTO.setModifiedAt(documentMetadataEntity.getModifiedAt());
	    return documentMetadataDTO;
	}

	@Override
	public DocumentDTO getDocumentVersion(String documentId, int version) {
	    DocumentDTO documentDTO = new DocumentDTO();
	    DocumentEntity documentEntity;
	    try {
	        documentEntity = documentRepository.findById(documentId)
	                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + documentId));
	    } catch (DocumentNotFoundException e) {
	        e.printStackTrace();
	        return null; // or throw an appropriate exception
	    }
	    DocumentVersionEntity documentVersionEntity = null;
		try {
			documentVersionEntity = documentEntity.getVersionHistory().stream()
			        .filter(versionEntity -> versionEntity.getVersion() == version)
			        .findFirst()
			        .orElseThrow(() -> new FileNotFoundException("Version " + version + " not found for document: " + documentId));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    documentDTO.setId(documentEntity.getId());
	    documentDTO.setTitle(documentEntity.getTitle());
	    documentDTO.setLatestVersion(documentEntity.getLatestVersion());
	    documentDTO.setLatestMetadata(documentMetadataEntityToDTO(documentEntity.getLatestMetadata()));
	    documentDTO.setVersionHistory(documentVersionEntityToDTO(documentVersionEntity));
	    return documentDTO;
	}

	private List<DocumentVersionDTO> documentVersionEntityToDTO(DocumentVersionEntity documentVersionEntity) {
	    List<DocumentVersionDTO> documentVersionDTOList = new ArrayList<>();
	    DocumentVersionDTO documentVersionDTO = new DocumentVersionDTO();
	    documentVersionDTO.setVersion(documentVersionEntity.getVersion());
//	    documentVersionDTO.setFileLocation("fileLocation");
	    documentVersionDTO.setFileLocation(saveFileToLocation(documentVersionEntity.getFile(),documentVersionEntity.getMetadata().getFilename()));
	    documentVersionDTO.setMetadata(documentMetadataEntityToDTO(documentVersionEntity.getMetadata()));
	    documentVersionDTOList.add(documentVersionDTO);
	    return documentVersionDTOList;
	}

    @Value("${project.file.local}")
    private String directoryPath;
    
	private String saveFileToLocation(byte[] file, String fileName) {
	    String fileLocation = directoryPath + fileName;
	    try {
	        Files.write(Paths.get(fileLocation), file);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Error saving photo to local machine: " + e.getMessage();
	    }

	    return fileLocation;
	}
	
	
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	@Override
	public String updateDocument(String documentId, String title, String author) {
	    try {
	        Optional<DocumentEntity> optionalDocument = documentRepository.findById(documentId);
	        if (optionalDocument.isPresent()) {
	            DocumentEntity documentEntity = optionalDocument.get();
	            documentEntity.setTitle(title);
	            DocumentMetadataEntity latestMetadata = documentEntity.getLatestMetadata();
	            if (latestMetadata != null) {
	                latestMetadata.setModifiedBy(author);
	                latestMetadata.setModifiedAt(LocalDateTime.now());
	            }
	            
	            DocumentEntity updatedDocument = documentRepository.save(documentEntity);
	            return "Document updated successfully as: "+updatedDocument.toString();
	        } else {
	            throw new DocumentNotFoundException("Document not found with id: " + documentId);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error updating the document: " + e.getMessage(), e);
	    }
	}
	
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public String deleteDocument(String documentId) {
	    try {
	        DocumentEntity documentEntity = documentRepository.findById(documentId)
	                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + documentId));
	        if (documentEntity != null) {
	            String message = documentEntity.toString();
	            documentRepository.deleteById(documentId);
	            return "Document deleted successfully: " + message;
	        } else {
	            return "Document not found with id: " + documentId;
	        }
	    } catch (DocumentNotFoundException e) {
	        e.printStackTrace();
	        return "Error deleting the document: " + e.getMessage();
	    }
	}

	@Override
	public String deleteDocumentVersion(String documentId, int version) {
	    try {
	        Optional<DocumentEntity> optionalDocument = documentRepository.findById(documentId);
	        if (optionalDocument.isPresent()) {
	            DocumentEntity documentEntity = optionalDocument.get();
	            List<DocumentVersionEntity> versionHistory = documentEntity.getVersionHistory();

	            Optional<DocumentVersionEntity> optionalVersionToDelete = versionHistory.stream()
	                    .filter(versionEntity -> versionEntity.getVersion() == version)
	                    .findFirst();

	            //sterming in java
	            if (optionalVersionToDelete.isPresent()) {
	                List<DocumentVersionEntity> updatedVersionHistory = versionHistory.stream()
	                        .filter(versionEntity -> versionEntity.getVersion() != version)
	                        .collect(Collectors.toList());

	                documentEntity.setVersionHistory(updatedVersionHistory);
	                if (documentEntity.getLatestVersion() == version) {
	                    if (!updatedVersionHistory.isEmpty()) {
	                        DocumentVersionEntity latestVersion = updatedVersionHistory.get(updatedVersionHistory.size() - 1);
	                        documentEntity.setLatestVersion(latestVersion.getVersion());
	                        documentEntity.setLatestMetadata(latestVersion.getMetadata());
	                    } else {
	                        documentEntity.setLatestVersion(0);
	                        documentEntity.setLatestMetadata(null);
	                    }
	                }

	                documentRepository.save(documentEntity);
	                return "Document version " + version + " deleted successfully.";
	            } else {
	                return "Version " + version + " not found for document: " + documentId;
	            }
	        } else {
	            return "Document not found with id: " + documentId;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error deleting the document version: " + e.getMessage();
	    }
	}
//------------------------------------------------------------------------------------------------------------------------
	@Override
    public List<DocumentDTO> getAllDocuments() {
        List<DocumentEntity> documentEntities = documentRepository.findAll();

        return documentEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    private DocumentDTO entityToDTO(DocumentEntity documentEntity) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(documentEntity.getId());
        documentDTO.setTitle(documentEntity.getTitle());
        documentDTO.setLatestVersion(documentEntity.getLatestVersion());
        documentDTO.setLatestMetadata(documentMetadataEntityToDTO(documentEntity.getLatestMetadata()));
        documentDTO.setVersionHistory(documentVersionEntityToDTO(documentEntity.getVersionHistory(), documentEntity.getId()));
        return documentDTO;
    }

}