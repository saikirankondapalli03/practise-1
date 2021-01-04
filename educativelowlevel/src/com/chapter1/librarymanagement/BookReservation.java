package com.chapter1.librarymanagement;

import java.util.Date;

import com.chapter1.librarymanagement.enums.ReservationStatus;

public class BookReservation {
	private Date creationDate;
	private ReservationStatus status;
	private String bookItemBarcode;
	private String memberId;
	public static BookReservation fetchReservationDetails(String barcode) {
		return new BookReservation();
	}
	public void sendBookAvailableNotification() {
		// TODO Auto-generated method stub
		
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public ReservationStatus getStatus() {
		return status;
	}
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	public String getBookItemBarcode() {
		return bookItemBarcode;
	}
	public void setBookItemBarcode(String bookItemBarcode) {
		this.bookItemBarcode = bookItemBarcode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}


