package com.example.fileSystem.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpHeadersValidator {

	@Value("${fileSystem.api.key}")
	private String apiKey;

	@Value("${fileSystem.app.id}")
	private String appId;

	/** The logger. */
	private static final Logger LOGGER =
			LoggerFactory.getLogger(HttpHeadersValidator.class);

	public boolean validateRequest(HttpServletRequest request) {
		if(validateAppIdAndApiKey(request, appId, apiKey)) {
			return true;
		}
		return false;
	}

	private boolean validateAppIdAndApiKey(HttpServletRequest request,
			String expectedAppId, String expectedApiKey) {
		String appId = request.getHeader("X-file-system-App-Id");
		String apiKey = request.getHeader("X-file-system-API-Key");
		return validateAppIdAndApiKey(expectedAppId, expectedApiKey, appId, apiKey);
	}

	private boolean validateAppIdAndApiKey(String expectedAppId, String
			expectedApiKey, String appId, String apiKey) {
		if (StringUtils.equals(apiKey, expectedApiKey) &&
				StringUtils.equals(appId, expectedAppId)) {
			return true;
		}
		LOGGER.debug("appId: {},apiKey: {}", appId, apiKey);
		// throw new InvoiceException("Either AppId or ApiKey is invalid.");
		return false;
	}
}
