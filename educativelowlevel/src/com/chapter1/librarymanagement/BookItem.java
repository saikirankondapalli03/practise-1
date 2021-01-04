package com.chapter1.librarymanagement;

import java.time.LocalDate;
import java.util.Date;

import com.chapter1.librarymanagement.abstractclasses.Book;
import com.chapter1.librarymanagement.enums.BookFormat;
import com.chapter1.librarymanagement.enums.BookStatus;

public class BookItem extends Book {
	private String barcode;
	private boolean isReferenceOnly;
	private Date borrowed;
	private LocalDate dueDate;
	private double price;
	private BookFormat format;
	private BookStatus status;
	private Date dateOfPurchase;
	private Date publicationDate;
	private Rack placedAt;

	public boolean checkout(String memberId) {
		if (this.isReferenceOnly()) {
			System.out.println("This book is Reference only and can't be issued");
			return false;
		}
		if (!BookLending.lendBook(this.getBarcode(), memberId)) {
			return false;
		}
		this.setStatus(BookStatus.LOANED);
		return true;
	}
	
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public boolean isReferenceOnly() {
		return isReferenceOnly;
	}

	public void setReferenceOnly(boolean isReferenceOnly) {
		this.isReferenceOnly = isReferenceOnly;
	}

	public Date getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Date borrowed) {
		this.borrowed = borrowed;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate( LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public BookFormat getFormat() {
		return format;
	}

	public void setFormat(BookFormat format) {
		this.format = format;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Rack getPlacedAt() {
		return placedAt;
	}

	public void setPlacedAt(Rack placedAt) {
		this.placedAt = placedAt;
	}


}
