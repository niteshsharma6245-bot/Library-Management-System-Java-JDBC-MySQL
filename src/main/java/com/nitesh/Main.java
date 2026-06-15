package com.nitesh;

import com.nitesh.dao.BookDAO;
import com.nitesh.dao.IssueRecordDAO;
import com.nitesh.dao.StudentDAO;
import com.nitesh.model.Book;
import com.nitesh.model.IssueRecord;
import com.nitesh.model.Student;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        BookDAO bookDAO = new BookDAO();
        StudentDAO studentDAO = new StudentDAO();
        IssueRecordDAO issueDAO = new IssueRecordDAO();

        // =========================
        // BOOK MODULE
        // =========================

        System.out.println("\n========== ALL BOOKS ==========");
        bookDAO.viewAllBooks();

        System.out.println("\n========== SEARCH BOOK BY ID ==========");
        Book book = bookDAO.searchBookById(1);

        if(book != null)
        {
            System.out.println("Book Found");
            System.out.println("ID: " + book.getBookId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Available Copies: "
                    + book.getAvailableCopies());
        }

        System.out.println("\n========== SEARCH BOOK BY TITLE ==========");
        bookDAO.searchBookByTitle("Java Programming");

        System.out.println("\n========== SEARCH BOOKS BY AUTHOR ==========");
        bookDAO.searchBooksByAuthor("James Gosling");

        System.out.println("\n========== SEARCH BOOKS BY PARTIAL TITLE ==========");
        bookDAO.searchBooksByTitle("Java");


        // =========================
        // STUDENT MODULE
        // =========================

        System.out.println("\n========== ALL STUDENTS ==========");
        studentDAO.viewAllStudents();

        System.out.println("\n========== SEARCH STUDENT BY ID ==========");

        Student student = studentDAO.searchStudentById(1);

        if(student != null)
        {
            System.out.println("Student Found");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Phone: " + student.getPhone());
        }

        System.out.println("\n========== SEARCH STUDENT BY EMAIL ==========");

        Student studentByEmail =
                studentDAO.searchStudentByEmail("nitesh@gmail.com");

        if(studentByEmail != null)
        {
            System.out.println("Student Found");
            System.out.println("Name: "
                    + studentByEmail.getName());
        }


        // =========================
        // ISSUE RECORD MODULE
        // =========================

        System.out.println("\n========== ALL ISSUE RECORDS ==========");
        issueDAO.viewIssuedBooks();

        System.out.println("\n========== ACTIVE ISSUES ==========");
        issueDAO.viewActiveIssues();

        System.out.println("\n========== SEARCH ISSUE ==========");

        IssueRecord issue =
                issueDAO.searchIssueById(1);

        if(issue != null)
        {
            System.out.println("Issue Found");
            System.out.println("Issue ID: "
                    + issue.getIssueId());
            System.out.println("Student ID: "
                    + issue.getStudentId());
            System.out.println("Book ID: "
                    + issue.getBookId());
        }

        System.out.println("\n========== OVERDUE BOOKS ==========");
        issueDAO.viewOverdueBooks();

        System.out.println("\n========== FINE CALCULATION ==========");
        issueDAO.calculateFine(1);

        System.out.println("\n========== PROJECT DEMO COMPLETED ==========");
    }
}