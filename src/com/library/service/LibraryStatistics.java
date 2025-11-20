package com.library.service;

import com.library.model.*;
import java.util.*;

/**
 * Provides statistics and analytics for a Library.
 */
public class LibraryStatistics {
    private Library library;

    public LibraryStatistics(Library library) {
        this.library = library;
    }

    public int getTotalBooks() {
        return (int) library.getItems().stream()
                .filter(item -> item instanceof Book)
                .count();
    }

    public int getTotalMagazines() {
        return (int) library.getItems().stream()
                .filter(item -> item instanceof Magazine)
                .count();
    }

    public User getMostActiveUser() {
        return library.getUsers().stream()
                .max(Comparator.comparingInt(User::getBorrowedItemsCount))
                .orElse(null);
    }

    public double getAverageBorrowsPerUser() {
        List<User> users = library.getUsers();
        if (users.isEmpty()) return 0.0;
        int totalBorrows = library.getBorrowRecords().size();
        return (double) totalBorrows / users.size();
    }

    public String generateUserReport(User user) {
        StringBuilder report = new StringBuilder();
        report.append("=== User Report for ").append(user.getName()).append(" ===\n");

        int borrowedCount = user.getBorrowedItemsCount();
        report.append("Currently borrowed: ").append(borrowedCount).append("/")
              .append(user.getMaxBorrowLimit()).append("\n");

        if (borrowedCount > 0) {
            report.append("Borrowed items:\n");
            for (LibraryItem item : user.getBorrowedItems()) {
                report.append("  - ").append(item.getTitle())
                      .append(" (").append(item.getItemType()).append(")\n");
            }
        } else {
            report.append("No items currently borrowed\n");
        }

        if (user instanceof Student) {
            Student student = (Student) user;
            report.append("Student ID: ").append(student.getStudentId()).append("\n");
        } else if (user instanceof Professor) {
            Professor prof = (Professor) user;
            report.append("Department: ").append(prof.getDepartment()).append("\n");
        }

        return report.toString();
    }

    @Override
    public String toString() {
        return String.format("LibraryStatistics{totalBooks=%d, totalMagazines=%d, avgBorrowsPerUser=%.2f}",
                getTotalBooks(), getTotalMagazines(), getAverageBorrowsPerUser());
    }

    public String generateDetailedLibraryReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== DETAILED LIBRARY REPORT ===\n\n");

        report.append("Library: ").append(library.getName()).append("\n\n");

        report.append("Total Items: ").append(library.getItems().size()).append("\n");
        report.append("Total Books: ").append(getTotalBooks()).append("\n");
        report.append("Total Magazines: ").append(getTotalMagazines()).append("\n\n");

        User mostActive = getMostActiveUser();
        if (mostActive != null) {
            report.append("Most Active User: ").append(mostActive.getName()).append("\n");
            report.append(generateUserReport(mostActive)).append("\n");
        }

        List<BorrowRecord> overdueRecords = library.getOverdueRecords();
        report.append("Overdue Items: ").append(overdueRecords.size()).append("\n");

        if (!overdueRecords.isEmpty()) {
            BorrowRecord firstOverdue = overdueRecords.get(0);
            report.append("\nFirst Overdue Item Details:\n");
            report.append("  User: ").append(firstOverdue.getUser().getName()).append("\n");
            report.append("  Item: ").append(firstOverdue.getItem().getTitle()).append("\n");
            report.append("  Type: ").append(firstOverdue.getItem().getItemType()).append("\n");

            LibraryItem item = firstOverdue.getItem();
            if (item instanceof Book) {
                Book book = (Book) item;
                report.append("  Author: ").append(book.getAuthor()).append("\n");
                report.append("  ISBN: ").append(book.getIsbn()).append("\n");
            } else if (item instanceof Magazine) {
                Magazine magazine = (Magazine) item;
                report.append("  Publisher: ").append(magazine.getPublisher()).append("\n");
                report.append("  Issue: ").append(magazine.getIssueNumber()).append("\n");
            }

            report.append("  Due Date: ").append(firstOverdue.getDueDate()).append("\n");
            report.append("  Days Overdue: ").append(
                DateUtils.daysBetween(firstOverdue.getDueDate(), java.time.LocalDate.now())
            ).append("\n");
            report.append("  Late Fee: €").append(
                String.format("%.2f", firstOverdue.calculateLateFee())
            ).append("\n");
            report.append("  Notification: ").append(firstOverdue.sendOverdueNotification()).append("\n");
        }

        return report.toString();
    }

    public String chainStep2(User user, LibraryItem item) {
        return user.chainStep3(item);
    }
}