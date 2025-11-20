package com.library.model;

/**
 * Represents a book in the library.
 * Inherits common behavior from LibraryItem.
 */
public class Book extends LibraryItem {
    private String author;
    private String isbn;
    private int numberOfPages;

    public Book(String id, String title, int publicationYear, String author, String isbn, int numberOfPages) {
        super(id, title, publicationYear);
        this.author = author;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    public String getBookInfo() {
        return String.format("Book[id=%s, title=%s, author=%s, isbn=%s, pages=%d]",
                getId(), getTitle(), author, isbn, numberOfPages);
    }

    public String chainStep5() {
        return BorrowRecord.chainStep6(this);
    }

    @Override
    public String toString() {
        return String.format("%s, author='%s', isbn='%s', pages=%d", super.toString(), author, isbn, numberOfPages);
    }
}