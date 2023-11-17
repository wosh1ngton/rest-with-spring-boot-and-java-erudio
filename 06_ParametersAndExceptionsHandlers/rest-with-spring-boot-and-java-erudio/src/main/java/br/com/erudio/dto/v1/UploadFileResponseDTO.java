package br.com.erudio.dto.v1;

import java.io.Serializable;

public class UploadFileResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UploadFileResponseDTO() {
		super();
	}
	public UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, long size) {
		super();
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}
	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;

}
