package com.example.api.logging.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private final byte[] content;

	public RepeatableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);

		InputStream is = request.getInputStream();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, length);
		}
		this.content = byteArrayOutputStream.toByteArray();
	}

	@Override
	public ServletInputStream getInputStream() {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.content);
		return new ServletInputStream() {
			@Override
			public int read() {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				return byteArrayInputStream.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
	}

	public String getContentAsString() {
		return new String(this.content, StandardCharsets.UTF_8);
	}

}
