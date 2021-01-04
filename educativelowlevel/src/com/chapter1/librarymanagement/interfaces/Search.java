package com.chapter1.librarymanagement.interfaces;

import java.util.Date;
import java.util.List;

import com.chapter1.librarymanagement.abstractclasses.Book;

public interface Search {
	public List<Book> searchByTitle(String title);

	public List<Book> searchByAuthor(String author);

	public List<Book> searchBySubject(String subject);

	public List<Book> searchByPubDate(Date publishDate);
}
