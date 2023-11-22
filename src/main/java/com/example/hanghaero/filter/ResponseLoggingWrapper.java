package com.example.hanghaero.filter;

import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseLoggingWrapper extends ContentCachingResponseWrapper {
	/**
	 * Create a new ContentCachingResponseWrapper for the given servlet response.
	 *
	 * @param response the original servlet response
	 */
	public ResponseLoggingWrapper(HttpServletResponse response) {
		super(response);
	}
}
