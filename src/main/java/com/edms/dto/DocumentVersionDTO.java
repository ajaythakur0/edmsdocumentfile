package com.edms.dto;
public class DocumentVersionDTO {
	 private int version;
	 private String fileLocation;
	 private DocumentMetadataDTO metadata;
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public DocumentMetadataDTO getMetadata() {
		return metadata;
	}
	public void setMetadata(DocumentMetadataDTO metadata) {
		this.metadata = metadata;
	}
	@Override
	public String toString() {
		return "DocumentVersionDTO [version=" + version + ", fileLocation=" + fileLocation + ", metadata=" + metadata
				+ "]";
	}
	 
}
