package com.library.model;

/**
 * Represents a student user.
 */
public class Student extends User {
    private String studentId;

    public Student(String userId, String name, String email, String studentId) {
        super(userId, name, email);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public int getMaxBorrowLimit() {
        return 3;
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
        return String.format("%s, studentId='%s'", super.toString(), studentId);
    }
}