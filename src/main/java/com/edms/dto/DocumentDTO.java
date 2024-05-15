package com.edms.dto;
import java.util.List;
public class DocumentDTO {
	private String id;
    private String title;
    private int latestVersion;
    private DocumentMetadataDTO latestMetadata;
    private List<DocumentVersionDTO> versionHistory;
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
	public DocumentMetadataDTO getLatestMetadata() {
		return latestMetadata;
	}
	public void setLatestMetadata(DocumentMetadataDTO latestMetadata) {
		this.latestMetadata = latestMetadata;
	}
	public List<DocumentVersionDTO> getVersionHistory() {
		return versionHistory;
	}
	public void setVersionHistory(List<DocumentVersionDTO> versionHistory) {
		this.versionHistory = versionHistory;
	}
	@Override
	public String toString() {
		return "DocumentDTO [id=" + id + ", title=" + title + ", latestVersion=" + latestVersion + ", latestMetadata="
				+ latestMetadata + ", versionHistory=" + versionHistory + "]";
	}
   
}
