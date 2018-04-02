package com.virgilsecurity.e2ee.demo.server.domain;

public class TextMessage {

	private String author;

	private String body;

	public TextMessage() {
	}

	public TextMessage(String author, String message) {
		this.author = author;
		this.body = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
