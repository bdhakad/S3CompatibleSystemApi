package com.amazonS3.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AmazonS3Service {

	private static final String SUFFIX = "/";
	private static final String END_POINT ="**********************";
	private static final String ACCESS_KEY ="********************";
	private static final String SECRET_ACCESS_KEY ="**************";

	/*********************************************************************************************************************************************/
	
	public static AmazonS3Service getInstance() {
		return new AmazonS3Service();
	}
	

	public static AmazonS3 getAmazonS3Client(String endPoint, String accessKey, String accessSecret) {
		/*
		 * Include the AWS Access Key ID. Include the Secret Access Key. Create an AWS
		 * Java SDK Connection Specifying Credentials Provided as Strings in Code
		 */
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);

		/*
		 * create a client connection based on credentials Set the endpoint for the new
		 * S3 compatible system to https://systemname.example.com.
		 */
		AmazonS3 s3client = new AmazonS3Client(credentials);
		s3client.setEndpoint(endPoint);

		return s3client;
	}
	
	
	public static void uploadFileToS3(S3ServiceRequestDetails requestDetails) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(requestDetails.getBucketName(),
				requestDetails.getKey(), requestDetails.getFile());
		getAmazonS3Client(requestDetails.getEndPoint(), requestDetails.getAccessKey(), requestDetails.getAccessSecret())
				.putObject(putObjectRequest);
	}
	
	public static S3Object downloadS3ObjectToFile(S3ServiceRequestDetails requestDetails) {
		AmazonS3 client = getAmazonS3Client(requestDetails.getEndPoint(), requestDetails.getAccessKey(), requestDetails.getAccessSecret());
		S3Object s3Object = client.getObject(requestDetails.getBucketName(), requestDetails.getKey());
		
//		GetObjectRequest request = new GetObjectRequest(requestDetails.getBucketName(), requestDetails.getKey());
//		ObjectMetadata obj = client.getObject(request,
//				new File("C:/Users/bdhakad/Pictures/Screenshots/new_retrieved_testImage.png"));
		return s3Object;
	}
	
	public static void createBucket(S3ServiceRequestDetails requestDetails) {
		AmazonS3 client = getAmazonS3Client(requestDetails.getEndPoint(), requestDetails.getAccessKey(), requestDetails.getAccessSecret());
		// create bucket
		if(client.doesBucketExist(requestDetails.getBucketName())) {
			System.out.println(requestDetails.getBucketName() +" already exists....");
		}else {
			client.createBucket(requestDetails.getBucketName());
		}
	}

	public static void deleteBucket(S3ServiceRequestDetails requestDetails) {
		// deletes bucket
		AmazonS3 client = getAmazonS3Client(requestDetails.getEndPoint(), requestDetails.getAccessKey(), requestDetails.getAccessSecret());
		// create bucket
		if(client.doesBucketExist(requestDetails.getBucketName())) {
			client.deleteBucket(requestDetails.getBucketName());
		}else {
			System.out.println(requestDetails.getBucketName() +" doesn't exists....");
		}
	}

	public static void createFolder(S3ServiceRequestDetails requestDetails) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(requestDetails.getBucketName(), "folderName" + SUFFIX, emptyContent,
				metadata);

		// send request to S3 to create folder
		getAmazonS3Client(requestDetails.getEndPoint(), requestDetails.getAccessKey(), requestDetails.getAccessSecret())
		           .putObject(putObjectRequest);
	}

	/**
	 * This method first deletes all the files in given folder and than the folder
	 * itself
	 */
	public static void deleteFolder(S3ServiceRequestDetails requestDetails) {
		AmazonS3 client = getAmazonS3Client(requestDetails.getEndPoint(), requestDetails.getAccessKey(), requestDetails.getAccessSecret());
		List<S3ObjectSummary> fileList = client.listObjects(requestDetails.getBucketName(), "folderName").getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(requestDetails.getBucketName(), file.getKey());
		}
		client.deleteObject(requestDetails.getBucketName(), "folderName");
	}

	
	/***************************************************************************************************************************************/
	
}