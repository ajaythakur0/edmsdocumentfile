//package com.edms;
//import com.edms.dto.DocumentDTO;
//import com.edms.dto.DocumentVersionDTO;
//import com.edms.entity.DocumentEntity;
//import com.edms.entity.DocumentMetadataEntity;
//import com.edms.entity.DocumentVersionEntity;
//import com.edms.exception.DocumentNotFoundException;
//import com.edms.repository.DocumentRepository;
//import com.edms.service.DocumentServiceImpl;
//import com.edms.service.DocumentVersionService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class DocumentServiceImplTest {
//
//    @Mock
//    private DocumentRepository documentRepository;
//
//    @Mock
//    private DocumentVersionService documentVersionService;
//
//    @InjectMocks
//    private DocumentServiceImpl documentService;
//
//    // Write test cases for other methods of DocumentServiceImpl
//    @Test
//    public void testGetDocument() {
//        // Arrange
//        String documentId = "1";
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
//        documentEntity.setTitle("Test Document");
//        documentEntity.setLatestVersion(1);
//        DocumentMetadataEntity metadataEntity = new DocumentMetadataEntity();
//        metadataEntity.setFilename("test.pdf");
//        documentEntity.setLatestMetadata(metadataEntity);
//        DocumentVersionEntity versionEntity = new DocumentVersionEntity();
//        versionEntity.setVersion(1);
//        versionEntity.setMetadata(metadataEntity);
//        documentEntity.setVersionHistory(Arrays.asList(versionEntity));
//
//        when(documentRepository.findById(documentId)).thenReturn(Optional.of(documentEntity));
//
//        // Act
//        DocumentDTO result = documentService.getDocument(documentId);
//
//        // Assert
//        verify(documentRepository, times(1)).findById(documentId);
//        assertEquals(documentId, result.getId());
//        assertEquals("Test Document", result.getTitle());
//        assertEquals(1, result.getLatestVersion());
//        assertEquals("test.pdf", result.getLatestMetadata().getFilename());
//        List<DocumentVersionDTO> versionHistory = result.getVersionHistory();
//        assertEquals(1, versionHistory.size());
//        assertEquals(1, versionHistory.get(0).getVersion());
//        assertEquals("test.pdf", versionHistory.get(0).getMetadata().getFilename());
//    }
//    @Test
//    public void testUploadDocument() throws IOException {
//        // Arrange
//        String title = "Test Document";
//        String author = "John Doe";
//        MultipartFile file = mock(MultipartFile.class);
//        DocumentVersionEntity versionEntity = new DocumentVersionEntity();
//        versionEntity.setVersion(1);
//        when(documentVersionService.uploadDocumentVersion(file, author, 0)).thenReturn(versionEntity);
//
//        // Act
//        String result = documentService.uploadDocument(file, title, author);
//
//        // Assert
//        verify(documentRepository, times(1)).save(any(DocumentEntity.class));
//        assertNotNull(result);
//        assertTrue(result.contains("Document uploaded successfully"));
//    }
//
//   
//
//    
//
//    @Test
//    public void testDeleteDocument() {
//        // Arrange
//        String documentId = "1";
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
//        when(documentRepository.findById(documentId)).thenReturn(Optional.of(documentEntity));
//
//        // Act
//        String result = documentService.deleteDocument(documentId);
//
//        // Assert
//        verify(documentRepository, times(1)).deleteById(documentId);
//        assertNotNull(result);
//        assertTrue(result.contains("Document deleted successfully"));
//    }
//
//    @Test
//    public void testDeleteDocumentVersion() {
//        // Arrange
//        String documentId = "1";
//        int version = 2;
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
//        documentEntity.setLatestVersion(3);
//        DocumentVersionEntity versionEntity1 = new DocumentVersionEntity();
//        versionEntity1.setVersion(1);
//        DocumentVersionEntity versionEntity2 = new DocumentVersionEntity();
//        versionEntity2.setVersion(version);
//        DocumentVersionEntity versionEntity3 = new DocumentVersionEntity();
//        versionEntity3.setVersion(3);
//        documentEntity.setVersionHistory(Arrays.asList(versionEntity1, versionEntity2, versionEntity3));
//        when(documentRepository.findById(documentId)).thenReturn(Optional.of(documentEntity));
//
//        // Act
//        String result = documentService.deleteDocumentVersion(documentId, version);
//
//        // Assert
//        verify(documentRepository, times(1)).save(documentEntity);
//        assertNotNull(result);
//        assertTrue(result.contains("Document version " + version + " deleted successfully"));
//        assertEquals(2, documentEntity.getVersionHistory().size());
//        assertFalse(documentEntity.getVersionHistory().stream().anyMatch(v -> v.getVersion() == version));
//    }
//    
//
//    @Test
//    public void testUploadNewVersion() throws IOException, DocumentNotFoundException {
//        // Arrange
//        String documentId = "1";
//        String author = "John Doe";
//        MultipartFile file = mock(MultipartFile.class);
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
//        documentEntity.setLatestVersion(1);
//        documentEntity.setVersionHistory(new ArrayList<>()); // Initialize versionHistory
//        DocumentVersionEntity versionEntity = new DocumentVersionEntity();
//        versionEntity.setVersion(2);
//        versionEntity.setMetadata(new DocumentMetadataEntity());
//        when(documentRepository.findById(documentId)).thenReturn(Optional.of(documentEntity));
//        when(documentVersionService.uploadDocumentVersion(file, author, 1)).thenReturn(versionEntity);
//
//        // Act
//        String result = documentService.uploadNewVersion(documentId, file, author);
//
//        // Assert
//        verify(documentRepository, times(1)).save(documentEntity);
//        assertNotNull(result);
//        assertTrue(result.contains("New version uploaded successfully"));
//        assertEquals(1, documentEntity.getVersionHistory().size()); // Adjusted expected size
//        assertEquals(2, documentEntity.getLatestVersion());
//        assertSame(versionEntity.getMetadata(), documentEntity.getLatestMetadata());
//    }
//
//    @Test
//    public void testUpdateDocument() {
//        // Arrange
//        String documentId = "1";
//        String title = "Updated Document";
//        String author = "John Doe";
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
//        documentEntity.setTitle("Original Title");
//        DocumentMetadataEntity metadataEntity = new DocumentMetadataEntity();
//        metadataEntity.setAuthor("Previous Author");
//        documentEntity.setLatestMetadata(metadataEntity);
//        when(documentRepository.findById(documentId)).thenReturn(Optional.of(documentEntity));
//
//        // Mock the return value of documentRepository.save(documentEntity)
//        DocumentEntity updatedEntity = new DocumentEntity();
//        updatedEntity.setId(documentId);
//        updatedEntity.setTitle(title);
//        metadataEntity.setModifiedBy(author);
//        metadataEntity.setModifiedAt(LocalDateTime.now().minusHours(1));
//        updatedEntity.setLatestMetadata(metadataEntity);
//
//        // Mock LocalDateTime.now() to return a fixed value
//        LocalDateTime fixedDateTime = LocalDateTime.now().plusHours(2);
//        DocumentMetadataEntity updatedMetadata = new DocumentMetadataEntity();
//        updatedMetadata.setModifiedBy(author);
//        updatedMetadata.setModifiedAt(fixedDateTime);
//        when(documentRepository.save(any())).thenReturn(updatedEntity);
//        when(updatedEntity.getLatestMetadata()).thenReturn(updatedMetadata);
//
//        // Act
//        String result = documentService.updateDocument(documentId, title, author);
//
//        // Assert
//        verify(documentRepository, times(1)).save(documentEntity);
//        assertNotNull(result);
//        assertTrue(result.contains("Document updated successfully"));
//        assertEquals(title, updatedEntity.getTitle());
//        assertEquals(author, updatedEntity.getLatestMetadata().getModifiedBy());
//        assertNotEquals(metadataEntity.getModifiedAt(), updatedEntity.getLatestMetadata().getModifiedAt());
//    }
//
//    @Test
//    public void testGetDocumentVersion() throws FileNotFoundException {
//        // Arrange
//        String documentId = "1";
//        int version = 2;
//        DocumentEntity documentEntity = new DocumentEntity();
//        documentEntity.setId(documentId);
//        DocumentVersionEntity versionEntity1 = new DocumentVersionEntity();
//        versionEntity1.setVersion(1);
//        DocumentMetadataEntity metadataEntity = new DocumentMetadataEntity();
//        metadataEntity.setFilename("test.pdf");
//        DocumentVersionEntity versionEntity2 = new DocumentVersionEntity();
//        versionEntity2.setVersion(version);
//        versionEntity2.setMetadata(metadataEntity);
//        documentEntity.setVersionHistory(Arrays.asList(versionEntity1, versionEntity2));
//        when(documentRepository.findById(documentId)).thenReturn(Optional.of(documentEntity));
//
//        // Act
//        DocumentDTO result = documentService.getDocumentVersion(documentId, version);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(documentId, result.getId());
//        assertEquals(1, result.getVersionHistory().size());
//        assertEquals(version, result.getVersionHistory().get(0).getVersion());
//        assertNotNull(result.getVersionHistory().get(0).getMetadata());
//        assertEquals("test.pdf", result.getVersionHistory().get(0).getMetadata().getFilename());
//    }
//}
