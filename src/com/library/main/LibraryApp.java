package com.library.main;

import com.library.model.*;
import com.library.service.DateUtils;
import com.library.service.Library;
import com.library.service.LibraryStatistics;

import java.time.LocalDate;
import java.util.*;

/**
 * Simple main application demonstrating the Library Management System.
 * - Creates a Library, items and users
 * - Demonstrates borrowing and returning items
 * - Shows overdue items and fees
 * - Prints basic statistics and demonstrates new control-flow methods
 */
public class LibraryApp {
    public static void main(String[] args) {
        Library library = new Library("City Library");

        // Create items
        Book book1 = new Book("B1", "Effective Java", 2018, "Joshua Bloch", "9780134685991", 416);
        Book book2 = new Book("B2", "Clean Code", 2008, "Robert C. Martin", "9780132350884", 464);
        Magazine mag1 = new Magazine("M1", "Nature", 2025, 780, "Nature Publishing Group");
        Magazine mag2 = new Magazine("M2", "Time", 2024, 52, "Time Inc.");

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(mag1);
        library.addItem(mag2);

        // Create users
        Student alice = new Student("U1", "Alice", "alice@example.com", "S1001");
        Student bob = new Student("U2", "Bob", "bob@example.com", "S1002");
        Professor drSmith = new Professor("U3", "Dr. Smith", "smith@example.com", "Computer Science");

        library.addUser(alice);
        library.addUser(bob);
        library.addUser(drSmith);

        LocalDate today = LocalDate.now();

        // Borrow items: record IDs are simple strings for demonstration
        library.borrowItem(alice, book1, "R1", today, DateUtils.addDays(today, 14));
        library.borrowItem(alice, mag1, "R2", today, DateUtils.addDays(today, 7));
        // Create an overdue borrow: borrowed 30 days ago, due 16 days ago
        LocalDate past = today.minusDays(30);
        library.borrowItem(drSmith, book2, "R3", past, DateUtils.addDays(past, 14));

        System.out.println("Library after initial borrows:");
        System.out.println(library);

        System.out.println("\nAvailable items:");
        for (LibraryItem item : library.getAvailableItems()) {
            System.out.println(" - " + item);
        }

        System.out.println("\nOverdue records:");
        for (BorrowRecord record : library.getOverdueRecords()) {
            System.out.printf(" - %s, overdue by %.0f days, fee=%.2f%n",
                    record.getRecordId(),
                    (double) java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), LocalDate.now()),
                    record.calculateLateFee());
        }

        // Demonstrate borrowing multiple items at once for Bob
        System.out.println("\nAttempting to borrow multiple items for Bob:");
        List<LibraryItem> toBorrow = Arrays.asList(book2, mag2);
        List<String> multiResults = library.borrowMultipleItems(bob, toBorrow, 14);
        for (String res : multiResults) {
            System.out.println(" - " + res);
        }

        // Process overdue items and print aggregated fees per user
        System.out.println("\nProcessing overdue items (aggregated fees):");
        Map<User, Double> overdueFees = library.processOverdueItems();
        if (overdueFees.isEmpty()) {
            System.out.println(" - No overdue fees found");
        } else {
            for (Map.Entry<User, Double> e : overdueFees.entrySet()) {
                System.out.printf(" - %s owes €%.2f%n", e.getKey().getName(), e.getValue());
            }
        }

        // Search for items by title
        System.out.println("\nSearching for items with term 'clean':");
        List<LibraryItem> found = library.findItemsByTitle("clean");
        for (LibraryItem it : found) {
            System.out.println(" - Found: " + it);
        }

        // Generate user reports
        LibraryStatistics stats = new LibraryStatistics(library);
        System.out.println("\nUser report for Alice:");
        System.out.println(stats.generateUserReport(alice));

        System.out.println("User report for Bob:");
        System.out.println(stats.generateUserReport(bob));

        System.out.println("User report for Dr. Smith:");
        System.out.println(stats.generateUserReport(drSmith));

        // Send overdue notifications for each borrow record
        System.out.println("\nOverdue notifications for records:");
        for (BorrowRecord record : library.getBorrowRecords()) {
            System.out.println(" - " + record.getRecordId() + ": " + record.sendOverdueNotification());
        }

        // Return an item (Alice returns the magazine)
        boolean returned = library.returnItem(alice, mag1);
        System.out.println("\nAlice returned magazine M1: " + returned);

        // Mark the overdue item as returned via its borrow record (Dr. Smith returns book2)
        for (BorrowRecord record : library.getBorrowRecords()) {
            if ("R3".equals(record.getRecordId())) {
                System.out.println("\nMarking R3 as returned (overdue item):");
                record.markAsReturned();
                System.out.println(" - Record after return: " + record);
                System.out.println(" - Late fee was: " + record.calculateLateFee());
                break;
            }
        }

        // Show final library state and statistics
        System.out.println("\nFinal library state:");
        System.out.println(library);

        System.out.println("\nStatistics:");
        System.out.println(" - Total books: " + stats.getTotalBooks());
        System.out.println(" - Total magazines: " + stats.getTotalMagazines());
        System.out.println(" - Most active user: " + stats.getMostActiveUser());
        System.out.println(" - Average borrows per user: " + stats.getAverageBorrowsPerUser());
            
        // Demonstrate detailed library report (7+ class interaction)
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DETAILED LIBRARY REPORT (7+ Class Interaction)");
        System.out.println("=".repeat(60));
        String detailedReport = stats.generateDetailedLibraryReport();
        System.out.println(detailedReport);

        // Demonstrate nested call chain (8 consecutive nested calls)
        System.out.println("\n" + "=".repeat(60));
        System.out.println("NESTED CALL CHAIN DEMONSTRATION");
        System.out.println("=".repeat(60));
        System.out.println("Triggering a nested chain of 8 consecutive method calls:");
        System.out.println("Caller -> Library -> LibraryStatistics -> User -> Student -> Book -> BorrowRecord -> DateUtils");
        System.out.println();
        
        String chainResult = library.chainCallStart(alice, book1);
        System.out.println("Result: " + chainResult);
        System.out.println("\nThis single call triggered a nested chain of 8+ consecutive calls across different classes.");
    }
}