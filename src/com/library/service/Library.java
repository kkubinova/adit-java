package com.library.service;

import com.library.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Library class manages items, users, and borrow records.
 * Demonstrates composition: Library contains LibraryItems and Users.
 */
public class Library {
    private String name;
    private List<LibraryItem> items;
    private List<User> users;
    private List<BorrowRecord> borrowRecords;

    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.users = new ArrayList<>();
        this.borrowRecords = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<LibraryItem> getItems() {
        return items;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }
    
    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean borrowItem(User user, LibraryItem item, String recordId, java.time.LocalDate borrowDate, java.time.LocalDate dueDate) {
        if (user.borrowItem(item)) {
            BorrowRecord record = new BorrowRecord(recordId, user, item, borrowDate, dueDate);
            borrowRecords.add(record);
            return true;
        }
        return false;
    }

    public boolean returnItem(User user, LibraryItem item) {
        boolean returned = user.returnItem(item);
        if (returned) {
            for (BorrowRecord record : borrowRecords) {
                if (record.getUser().equals(user) && record.getItem().equals(item) && record.getReturnDate() == null) {
                    record.markAsReturned();
                    break;
                }
            }
        }
        return returned;
    }

    public List<LibraryItem> getAvailableItems() {
        return items.stream().filter(LibraryItem::isAvailable).collect(Collectors.toList());
    }

    public List<BorrowRecord> getOverdueRecords() {
        return borrowRecords.stream()
                .filter(record -> record.getReturnDate() == null && record.isOverdue())
                .collect(Collectors.toList());
    }

    public List<String> borrowMultipleItems(User user, List<LibraryItem> itemsToBorrow, int borrowDays) {
        List<String> results = new ArrayList<>();

        java.time.LocalDate borrowDate = java.time.LocalDate.now();

        for (LibraryItem item : itemsToBorrow) {
            String title = item.getTitle();
            if (item.isAvailable()) {
                if (user.getBorrowedItemsCount() < user.getMaxBorrowLimit()) {
                    String recordId = "R-" + user.getUserId() + "-" + item.getId() + "-" + System.currentTimeMillis();
                    java.time.LocalDate dueDate = DateUtils.addDays(borrowDate, borrowDays);
                    
                    if (borrowItem(user, item, recordId, borrowDate, dueDate)) {
                        results.add("SUCCESS: Borrowed " + title);
                    } else {
                        results.add("FAILED: Could not borrow " + title);
                    }
                } else {
                    results.add("LIMIT_REACHED: Cannot borrow " + title + " - limit reached");
                }
            } else {
                results.add("UNAVAILABLE: " + title + " is not available");
            }
        }

        return results;
    }

    public Map<User, Double> processOverdueItems() {
        Map<User, Double> overdueFeesMap = new HashMap<>();

        for (BorrowRecord record : borrowRecords) {
            if (record.isOverdue() && record.getReturnDate() == null) {
                double fee = record.calculateLateFee();
                User user = record.getUser();

                if (overdueFeesMap.containsKey(user)) {
                    overdueFeesMap.put(user, overdueFeesMap.get(user) + fee);
                } else {
                    overdueFeesMap.put(user, fee);
                }
            }
        }

        return overdueFeesMap;
    }

    public List<LibraryItem> findItemsByTitle(String searchTerm) {
        List<LibraryItem> foundItems = new ArrayList<>();

        for (LibraryItem item : items) {
            if (item.getTitle() != null && item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                foundItems.add(item);
            }
        }

        if (foundItems.isEmpty()) {
            System.out.println("No items found matching: " + searchTerm);
        }

        return foundItems;
    }

    public String chainCallStart(User user, LibraryItem item) {
        LibraryStatistics stats = new LibraryStatistics(this);
        return stats.chainStep2(user, item);    
    }

    @Override
    public String toString() {
        return String.format("Library{name='%s', items=%d, users=%d, borrowRecords=%d}",
                name, items.size(), users.size(), borrowRecords.size());
    }
}