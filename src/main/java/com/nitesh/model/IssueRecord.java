package com.nitesh.model;

import java.sql.Date;

public class IssueRecord
{
    private int issueId;
    private int studentId;
    private int bookId;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;

    public IssueRecord(int studentId,
                       int bookId,
                       Date issueDate,
                       Date dueDate)
    {
        this.studentId = studentId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public IssueRecord(int issueId,
                       int studentId,
                       int bookId,
                       Date issueDate,
                       Date dueDate,
                       Date returnDate)
    {
        this.issueId = issueId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getIssueId() {
        return issueId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
