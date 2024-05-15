package com.edms.dto;
import java.time.LocalDateTime;
public class DocumentMetadataDTO {
	private String filename;
    private String fileSize;
    private String fileType;
    private String author;
    private String createdBy;
    private String modifiedBy;
//    private String deletedBy;
//    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//    private LocalDateTime deletedAt;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	@Override
	public String toString() {
		return "DocumentMetadataDTO [filename=" + filename + ", fileSize=" + fileSize + ", fileType=" + fileType
				+ ", author=" + author + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdAt="
				+ createdAt + ", modifiedAt=" + modifiedAt + "]";
	}


    
}
