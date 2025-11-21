package com.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for users of the library.
 * Demonstrates inheritance: Student and Professor extend this class.
 * Association: User borrows LibraryItems.
 */
public abstract class User {
    private String userId;
    private String name;
    private String email;
    private List<LibraryItem> borrowedItems;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.borrowedItems = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    public abstract int getMaxBorrowLimit();

    public boolean borrowItem(LibraryItem item) {
        if (borrowedItems.size() >= getMaxBorrowLimit()) { 
            return false;
        }
        if (!item.isAvailable()) {
            return false;
        }
        try {
        item.borrowItem();
        borrowedItems.add(item);
        return true;
    }
        catch(IllegalStateException e) {
            return false;
        }
    }

    public boolean returnItem(LibraryItem item) {
        if (borrowedItems.contains(item)) {
            item.returnItem();
            borrowedItems.remove(item);
            return true;
        }
        return false;
    }

    public int getBorrowedItemsCount() {
        return borrowedItems.size();
    }

    public boolean renewItem(LibraryItem item, int additionalDays) {
        if (borrowedItems.contains(item)) {
            System.out.println("Renewing " + item.getTitle() + " for " + additionalDays + " days");
            return true;
        } else {
            System.out.println("Cannot renew - item not borrowed by this user");
            return false;
        }
    }

    public String chainStep3(LibraryItem item) {
        if (this instanceof Student) {
            return ((Student) this).chainStep4(item);
        } else if (this instanceof Professor) {
            return ((Professor) this).chainStep4(item);
        } else {
            return "chainStep3: unknown user type - " + getClass().getSimpleName();
        }
    }

    @Override
    public String toString() {
        return String.format("%s{id='%s', name='%s', email='%s', borrowedItems=%d}",
                getClass().getSimpleName(), userId, name, email, borrowedItems.size());
    }
}
