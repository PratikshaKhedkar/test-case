package com.insurance.policy.exception;

import java.time.LocalDate;

public class ErrorDetails {

	private String message;
	private LocalDate date;
	private String details;

	public ErrorDetails() {
		// TODO Auto-generated constructor stub
	}

	public ErrorDetails(String message, LocalDate date, String details) {
		super();
		this.message = message;
		this.date = date;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
