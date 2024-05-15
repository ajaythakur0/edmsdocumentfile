package com.edms.entity;
import org.springframework.data.annotation.Id;
import java.util.Arrays;
public class DocumentVersionEntity {
    @Id
    private int version;
    private byte[] file;
    private DocumentMetadataEntity metadata;
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public DocumentMetadataEntity getMetadata() {
		return metadata;
	}
	public void setMetadata(DocumentMetadataEntity metadata) {
		this.metadata = metadata;
	}
	@Override
	public String toString() {
		return "DocumentVersionEntity [version=" + version + ", file= Stored, metadata="
				+ metadata + "]";
	}
	
}
