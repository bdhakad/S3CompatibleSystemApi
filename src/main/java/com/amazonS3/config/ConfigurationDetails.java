/**
 * 
 */
package com.amazonS3.config;

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
public class ConfigurationDetails {

	private String endPoint;
	private String accessKey;
	private String accessSecret;
}
