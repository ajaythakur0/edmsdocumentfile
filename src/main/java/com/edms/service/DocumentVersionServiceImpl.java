package com.edms.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.edms.entity.DocumentMetadataEntity;
import com.edms.entity.DocumentVersionEntity;
import java.io.IOException;
import java.time.LocalDateTime;
@Service
public class DocumentVersionServiceImpl implements DocumentVersionService {
	@Override
    public DocumentVersionEntity uploadDocumentVersion(MultipartFile file, String author, int latestVersion) throws IOException {
        DocumentMetadataEntity metadata = createDocumentMetadata(file, author);
        DocumentVersionEntity documentVersionEntity = new DocumentVersionEntity();
        documentVersionEntity.setVersion(latestVersion + 1);
        documentVersionEntity.setMetadata(metadata);
        documentVersionEntity.setFile(file.getBytes());
        // Save the file content in documentVersionEntity.file

        return documentVersionEntity;
    }

    private DocumentMetadataEntity createDocumentMetadata(MultipartFile file, String author) {
        DocumentMetadataEntity metadata = new DocumentMetadataEntity();
        metadata.setFilename(file.getOriginalFilename());
        metadata.setFileSize(formatFileSize(file.getSize()));
        metadata.setFileType(file.getContentType());
        metadata.setAuthor(author);
        metadata.setCreatedBy(author);
        metadata.setCreatedAt(LocalDateTime.now());
        return metadata;
    }

    private String formatFileSize(long fileSizeBytes) {
        if (fileSizeBytes < 0) {
            return "0 B";
        }

        int unit = 1024;
        if (fileSizeBytes < unit) {
            return fileSizeBytes + " B";
        }

        int exp = (int) (Math.log(fileSizeBytes) / Math.log(unit));
        String pre = String.valueOf("KMGTPE".charAt(exp - 1));
        return String.format("%.1f %sB", fileSizeBytes / Math.pow(unit, exp), pre);
    }
}