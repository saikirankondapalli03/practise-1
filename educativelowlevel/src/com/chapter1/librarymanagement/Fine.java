package com.chapter1.librarymanagement;

import java.util.Date;

class Fine {
	private Date creationDate;
	private double bookItemBarcode;
	private String memberId;

	public static void collectFine(String memberId, long days) {
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public double getBookItemBarcode() {
		return bookItemBarcode;
	}

	public void setBookItemBarcode(double bookItemBarcode) {
		this.bookItemBarcode = bookItemBarcode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
