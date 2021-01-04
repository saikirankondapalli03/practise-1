package com.chapter1.librarymanagement;

import com.chapter1.librarymanagement.abstractclasses.Account;

public class Librarian extends Account {
	public boolean addBookItem(BookItem bookItem) {
		return false;
	}

	public boolean blockMember(Member member) {
		return true;
	}

	public boolean unBlockMember(Member member) {
		return true;
	}

	@Override
	public boolean resetPassword() {
		// TODO Auto-generated method stub
		return false;
	}
}
