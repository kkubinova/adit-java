package com.library.model;

import java.util.Objects;

/**
 * Abstract base class for library items.
 * Demonstrates inheritance: Book and Magazine extend this class.
 */
public abstract class LibraryItem {
    private String id;
    private String title;
    private int publicationYear;
    private boolean isAvailable;

    public LibraryItem(String id, String title, int publicationYear) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    protected void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public abstract String getItemType();

    public void borrowItem() {
        this.isAvailable = false;
    }

    public void returnItem() {
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return String.format("%s{id='%s', title='%s', year=%d, available=%s}",
                getItemType(), id, title, publicationYear, isAvailable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryItem that = (LibraryItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}