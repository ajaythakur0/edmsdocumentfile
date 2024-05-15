package com.edms.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
@Document(collection = "documents")
public class DocumentEntity {
    @Id
    private String id;
    private String title;
    private int latestVersion;
    private DocumentMetadataEntity latestMetadata;
    private List<DocumentVersionEntity> versionHistory;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(int latestVersion) {
		this.latestVersion = latestVersion;
	}
	public DocumentMetadataEntity getLatestMetadata() {
		return latestMetadata;
	}
	public void setLatestMetadata(DocumentMetadataEntity latestMetadata) {
		this.latestMetadata = latestMetadata;
	}
	public List<DocumentVersionEntity> getVersionHistory() {
		return versionHistory;
	}
	public void setVersionHistory(List<DocumentVersionEntity> versionHistory) {
		this.versionHistory = versionHistory;
	}
	@Override
	public String toString() {
		return "DocumentEntity [id=" + id + ", title=" + title + ", latestVersion=" + latestVersion
				+ ", latestMetadata=" + latestMetadata + ", versionHistory=" + versionHistory + "]";
	}
   
}
