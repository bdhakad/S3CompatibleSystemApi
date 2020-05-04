/**
 * 
 */
package com.amazonS3.utils;

import java.io.File;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bdhakad
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class S3ServiceRequestDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String endPoint;
	private String accessKey;
	private String accessSecret;
	
	private String bucketName;
	private String key;
	private File file;
	private String userId;
	
	
	public S3ServiceRequestDetails() {
		super();
	}
	public S3ServiceRequestDetails(String endPoint, String accessKey, String accessSecret) {
		super();
		this.endPoint = endPoint;
		this.accessKey = accessKey;
		this.accessSecret = accessSecret;
	}
	public S3ServiceRequestDetails(String endPoint, String accessKey, String accessSecret, String bucketName,
			String key, File file) {
		super();
		this.endPoint = endPoint;
		this.accessKey = accessKey;
		this.accessSecret = accessSecret;
		this.bucketName = bucketName;
		this.key = key;
		this.file = file;
	}
	
	
	
}
