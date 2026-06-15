package com.nitesh.dao;

import com.nitesh.model.IssueRecord;
import com.nitesh.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssueRecordDAO
{
    public void issueBook(int studentId,
                          int bookId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        // Check Student Exists
        String studentQuery =
                "SELECT * FROM students WHERE student_id = ?";

        PreparedStatement studentPs =
                con.prepareStatement(studentQuery);

        studentPs.setInt(1, studentId);

        ResultSet studentRs =
                studentPs.executeQuery();

        if(!studentRs.next())
        {
            System.out.println("Student Not Found");

            studentRs.close();
            studentPs.close();
            con.close();

            return;
        }

        // Check Book Exists
        String bookQuery =
                "SELECT * FROM books WHERE book_id = ?";

        PreparedStatement bookPs =
                con.prepareStatement(bookQuery);

        bookPs.setInt(1, bookId);

        ResultSet bookRs =
                bookPs.executeQuery();

        if(!bookRs.next())
        {
            System.out.println("Book Not Found");

            studentRs.close();
            studentPs.close();

            bookRs.close();
            bookPs.close();

            con.close();

            return;
        }

        // Check Duplicate Issue
        String duplicateQuery =
                "SELECT * FROM issue_records " +
                        "WHERE student_id = ? " +
                        "AND book_id = ? " +
                        "AND return_date IS NULL";

        PreparedStatement duplicatePs =
                con.prepareStatement(duplicateQuery);

        duplicatePs.setInt(1, studentId);
        duplicatePs.setInt(2, bookId);

        ResultSet duplicateRs =
                duplicatePs.executeQuery();

        if(duplicateRs.next())
        {
            System.out.println(
                    "Student already has this book issued");

            duplicateRs.close();
            duplicatePs.close();

            studentRs.close();
            studentPs.close();

            bookRs.close();
            bookPs.close();

            con.close();

            return;
        }

        duplicateRs.close();
        duplicatePs.close();

        // Check Available Copies
        int availableCopies =
                bookRs.getInt("available_copies");

        if(availableCopies <= 0)
        {
            System.out.println("Book Not Available");

            studentRs.close();
            studentPs.close();

            bookRs.close();
            bookPs.close();

            con.close();

            return;
        }

        // Issue Book
        String issueQuery =
                "INSERT INTO issue_records(student_id, book_id, issue_date, due_date) VALUES (?, ?, ?, ?)";

        PreparedStatement issuePs =
                con.prepareStatement(issueQuery);

        Date issueDate =
                new Date(System.currentTimeMillis());

        Date dueDate =
                new Date(System.currentTimeMillis()
                        + (14L * 24 * 60 * 60 * 1000));

        issuePs.setInt(1, studentId);
        issuePs.setInt(2, bookId);
        issuePs.setDate(3, issueDate);
        issuePs.setDate(4, dueDate);

        int rowsAffected =
                issuePs.executeUpdate();

        if(rowsAffected > 0)
        {
            String updateQuery =
                    "UPDATE books " +
                            "SET available_copies = available_copies - 1 " +
                            "WHERE book_id = ?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateQuery);

            updatePs.setInt(1, bookId);

            updatePs.executeUpdate();

            updatePs.close();

            System.out.println("Book Issued Successfully");
        }
        else
        {
            System.out.println("Issue Failed");
        }

        studentRs.close();
        studentPs.close();

        bookRs.close();
        bookPs.close();

        issuePs.close();
        con.close();
    }

    public void returnBook(int issueId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String getBookQuery =
                "SELECT book_id, return_date FROM issue_records WHERE issue_id = ?";

        PreparedStatement getPs =
                con.prepareStatement(getBookQuery);

        getPs.setInt(1, issueId);

        ResultSet rs = getPs.executeQuery();

        if(!rs.next())
        {
            System.out.println("Issue Record Not Found");

            rs.close();
            getPs.close();
            con.close();

            return;
        }

        int bookId = rs.getInt("book_id");

        if(rs.getDate("return_date") != null)
        {
            System.out.println("Book Already Returned");

            rs.close();
            getPs.close();
            con.close();

            return;
        }

        String returnQuery =
                "UPDATE issue_records SET return_date = ? WHERE issue_id = ?";

        PreparedStatement returnPs =
                con.prepareStatement(returnQuery);

        Date returnDate =
                new Date(System.currentTimeMillis());

        returnPs.setDate(1, returnDate);
        returnPs.setInt(2, issueId);

        int rowsAffected = returnPs.executeUpdate();

        if(rowsAffected > 0)
        {
            String updateBookQuery =
                    "UPDATE books " +
                            "SET available_copies = available_copies + 1 " +
                            "WHERE book_id = ?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateBookQuery);

            updatePs.setInt(1, bookId);

            updatePs.executeUpdate();

            updatePs.close();

            System.out.println("Book Returned Successfully");
        }
        else
        {
            System.out.println("Return Failed");
        }

        rs.close();
        getPs.close();
        returnPs.close();
        con.close();
    }

    public void viewIssuedBooks() throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM issue_records";

        PreparedStatement ps =
                con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            System.out.println("Issue ID: "
                    + rs.getInt("issue_id"));

            System.out.println("Student ID: "
                    + rs.getInt("student_id"));

            System.out.println("Book ID: "
                    + rs.getInt("book_id"));

            System.out.println("Issue Date: "
                    + rs.getDate("issue_date"));

            System.out.println("Due Date: "
                    + rs.getDate("due_date"));

            System.out.println("Return Date: "
                    + rs.getDate("return_date"));

            System.out.println("--------------------");
        }

        rs.close();
        ps.close();
        con.close();
    }

    public void viewActiveIssues() throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM issue_records WHERE return_date IS NULL";

        PreparedStatement ps =
                con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            System.out.println("Issue ID: "
                    + rs.getInt("issue_id"));

            System.out.println("Student ID: "
                    + rs.getInt("student_id"));

            System.out.println("Book ID: "
                    + rs.getInt("book_id"));

            System.out.println("Issue Date: "
                    + rs.getDate("issue_date"));

            System.out.println("Due Date: "
                    + rs.getDate("due_date"));

            System.out.println("--------------------");
        }

        rs.close();
        ps.close();
        con.close();
    }

    public IssueRecord searchIssueById(int issueId)
            throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM issue_records WHERE issue_id = ?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, issueId);

        ResultSet rs = ps.executeQuery();

        IssueRecord record = null;

        if(rs.next())
        {
            record = new IssueRecord(
                    rs.getInt("issue_id"),
                    rs.getInt("student_id"),
                    rs.getInt("book_id"),
                    rs.getDate("issue_date"),
                    rs.getDate("due_date"),
                    rs.getDate("return_date")
            );
        }

        rs.close();
        ps.close();
        con.close();

        return record;
    }

    public void viewOverdueBooks() throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM issue_records " +
                        "WHERE due_date < CURDATE() " +
                        "AND return_date IS NULL";

        PreparedStatement ps =
                con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while(rs.next())
        {
            found = true;

            System.out.println("Issue ID: "
                    + rs.getInt("issue_id"));

            System.out.println("Student ID: "
                    + rs.getInt("student_id"));

            System.out.println("Book ID: "
                    + rs.getInt("book_id"));

            System.out.println("Issue Date: "
                    + rs.getDate("issue_date"));

            System.out.println("Due Date: "
                    + rs.getDate("due_date"));

            System.out.println("------------------------");
        }

        if(!found)
        {
            System.out.println("No Overdue Books");
        }

        rs.close();
        ps.close();
        con.close();
    }

    public void calculateFine(int issueId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM issue_records WHERE issue_id = ?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, issueId);

        ResultSet rs = ps.executeQuery();

        if(!rs.next())
        {
            System.out.println("Issue Record Not Found");

            rs.close();
            ps.close();
            con.close();

            return;
        }

        Date dueDate =
                rs.getDate("due_date");

        Date returnDate =
                rs.getDate("return_date");

        long currentTime;

        if(returnDate == null)
        {
            currentTime = System.currentTimeMillis();
        }
        else
        {
            currentTime = returnDate.getTime();
        }

        long difference =
                currentTime - dueDate.getTime();

        long daysLate =
                difference / (1000 * 60 * 60 * 24);

        if(daysLate <= 0)
        {
            System.out.println("No Fine");
        }
        else
        {
            long fine = daysLate * 5;

            System.out.println("Days Late: "
                    + daysLate);

            System.out.println("Fine Amount: ₹"
                    + fine);
        }

        rs.close();
        ps.close();
        con.close();
    }
}