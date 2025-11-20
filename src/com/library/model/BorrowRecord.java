package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import com.library.service.DateUtils;

/**
 * Association class that represents a borrowing event.
 * Demonstrates dependency on User and LibraryItem.
 *
 * When a user borrows an item, a BorrowRecord is created with a borrowDate and dueDate.
 * markAsReturned() will register the return on both the user and the item.
 */
public class BorrowRecord {
    private String recordId;
    private User user;
    private LibraryItem item;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public static final double LATE_FEE_PER_DAY = 0.5;

    public BorrowRecord(String recordId, User user, LibraryItem item, LocalDate borrowDate, LocalDate dueDate) {
        this.recordId = recordId;
        this.user = Objects.requireNonNull(user, "user");
        this.item = Objects.requireNonNull(item, "item");
        this.borrowDate = Objects.requireNonNull(borrowDate, "borrowDate");
        this.dueDate = Objects.requireNonNull(dueDate, "dueDate");
        this.returnDate = null;
    }

    public String getRecordId() {
        return recordId;
    }

    public User getUser() {
        return user;
    }

    public LibraryItem getItem() {
        return item;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isOverdue() {
        LocalDate checkDate = (returnDate != null) ? returnDate : LocalDate.now();
        return checkDate.isAfter(dueDate);
    }

    public double calculateLateFee() {
        LocalDate checkDate = (returnDate != null) ? returnDate : LocalDate.now();
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, checkDate);
        if (daysOverdue <= 0) {
            return 0.0;
        }
        return daysOverdue * LATE_FEE_PER_DAY;
    }

    public void markAsReturned() {
        if (this.returnDate != null) {
            return;
        }
        this.returnDate = LocalDate.now();
        user.returnItem(item);
    }

    public String sendOverdueNotification() {
        if (isOverdue() && returnDate == null) {
            long daysOverdue = DateUtils.daysBetween(dueDate, LocalDate.now());
            double fee = calculateLateFee();

            if (daysOverdue > 7) {
                return "URGENT: Item '" + item.getTitle() + "' is " + daysOverdue
                       + " days overdue! Fee: €" + String.format("%.2f", fee)
                       + ". Please return immediately.";
            } else {
                return "REMINDER: Item '" + item.getTitle() + "' is " + daysOverdue
                       + " days overdue. Fee: €" + String.format("%.2f", fee);
            }
        } else {
            return "No notification needed - item not overdue";
        }
    }

    public static String chainStep6(LibraryItem item) {
        return DateUtils.chainStep7(item.getTitle());
    }

    @Override
    public String toString() {
        return String.format("BorrowRecord{id='%s', user=%s, item=%s, borrowDate=%s, dueDate=%s, returnDate=%s}",
                recordId, user.getUserId(), item.getId(), borrowDate, dueDate, returnDate);
    }
}