package com.library.model;

/**
 * Represents a professor user.
 */
public class Professor extends User {
    private String department;

    public Professor(String userId, String name, String email, String department) {
        super(userId, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public int getMaxBorrowLimit() {
        return 10;
    }

    public String chainStep4(LibraryItem item) {
        if (item instanceof Book) {
            return ((Book) item).chainStep5();
        } else if (item instanceof Magazine) {
            return ((Magazine) item).chainStep5();
        } else {
            return "chainStep4: unknown item type - " + item.getClass().getSimpleName();
        }
    }

    @Override
    public String toString() {
        return String.format("%s, department='%s'", super.toString(), department);
    }
}