package com.library.model;

/**
 * Represents a magazine in the library.
 * Inherits common behavior from LibraryItem.
 */
public class Magazine extends LibraryItem {
    private int issueNumber;
    private String publisher;

    public Magazine(String id, String title, int publicationYear, int issueNumber, String publisher) {
        super(id, title, publicationYear);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String getItemType() {
        return "Magazine";
    }

    public String getMagazineInfo() {
        return String.format("Magazine[id=%s, title=%s, issue=%d, publisher=%s]",
                getId(), getTitle(), issueNumber, publisher);
    }

    public String chainStep5() {
        return BorrowRecord.chainStep6(this);
    }

    @Override
    public String toString() {
        return String.format("%s, issue=%d, publisher='%s'", super.toString(), issueNumber, publisher);
    }
}