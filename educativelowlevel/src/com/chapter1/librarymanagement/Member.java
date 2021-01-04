package com.chapter1.librarymanagement;

import java.time.LocalDate;
import java.util.Date;

import com.chapter1.librarymanagement.abstractclasses.Account;
import com.chapter1.librarymanagement.enums.BookStatus;
import com.chapter1.librarymanagement.enums.ReservationStatus;

public class Member extends Account {
	private Date dateOfMembership;
	private int totalBooksCheckedout;

	public int getTotalBooksCheckedout() {
		return totalBooksCheckedout;
	}

	public boolean reserveBookItem(BookItem bookItem) {
		return true;
	}

	private void incrementTotalBooksCheckedout() {
		totalBooksCheckedout++;
	}

	private void decrementTotalBooksCheckedout() {
		totalBooksCheckedout--;
	}

	public boolean checkoutBookItem(BookItem bookItem) {
		if (totalBooksCheckedout >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
			System.out.println("The user has already checked-out maximum number of books");
			return false;
		}
		BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
		if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
			// book item has a pending reservation from another user
			System.out.println("This book is reserved by another member");
			return false;
		} else if (bookReservation != null) {
			// book item has a pending reservation from the give member, update it
			bookReservation.setStatus(ReservationStatus.COMPLETED);
		}

		if (!bookItem.checkout(this.getId())) {
			return false;
		}

		this.incrementTotalBooksCheckedout();
		return true;
	}

	private void checkForFine(String bookItemBarcode) {
		BookLending bookLending = BookLending.fetchLendingDetails(bookItemBarcode);
		Date dueDate = bookLending.getDueDate();
		Date today = new Date();
		// check if the book has been returned within the due date
		if (today.compareTo(dueDate) > 0) {
			long diff = today.getTime() - dueDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			Fine.collectFine(getId(), diffDays);
		}
	}

	public void returnBookItem(BookItem bookItem) {
		this.checkForFine(bookItem.getBarcode());
		BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
		if (bookReservation != null) {
			// book item has a pending reservation
			bookItem.setStatus(BookStatus.RESERVED);
			bookReservation.sendBookAvailableNotification();
		}
		bookItem.setStatus(BookStatus.AVAILABLE);
	}

	public boolean renewBookItem(BookItem bookItem) {
		this.checkForFine(bookItem.getBarcode());
		BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
		// check if this book item has a pending reservation from another member
		if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
			System.out.println("This book is reserved by another member");
			this.decrementTotalBooksCheckedout();
			bookItem.setStatus(BookStatus.RESERVED);
			bookReservation.sendBookAvailableNotification();
			return false;
		} else if (bookReservation != null) {
			// book item has a pending reservation from this member
			bookReservation.setStatus(ReservationStatus.COMPLETED);
		}
		BookLending.lendBook(bookItem.getBarcode(), this.getId());
		bookItem.setDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS));
		return true;
	}

	@Override
	public boolean resetPassword() {
		// TODO Auto-generated method stub
		return false;
	}
}
