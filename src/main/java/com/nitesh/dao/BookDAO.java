package com.nitesh.dao;
import com.nitesh.model.Book;
import com.nitesh.util.DBConnection;
import java.sql.PreparedStatement;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO
{
    public void addBook( Book book ) throws SQLException
    {
        Connection con = DBConnection.getConnection() ;
        String query = "INSERT INTO books ( title , author , available_copies) VALUES( ? , ? , ?)";
        PreparedStatement ps = con.prepareStatement(query) ;

        ps.setString( 1 , book.getTitle()) ;
        ps.setString( 2 , book.getAuthor());
        ps.setInt( 3 , book.getAvailableCopies());
        int rows = ps.executeUpdate() ;

        if(rows > 0)
        {
            System.out.println("Book Added Successfully");
        }
        else
        {
            System.out.println("Book Not Added");
        }
        ps.close();
        con.close() ;


    }

    public void viewAllBooks() throws SQLException
    {
        Connection con = DBConnection.getConnection() ;
        String query = "SELECT * FROM books" ;
        PreparedStatement ps = con.prepareStatement(query) ;
        ResultSet rs = ps.executeQuery() ;
        while( rs.next())
        {
            System.out.println("Book ID: " + rs.getInt("book_id"));
            System.out.println("Title: " + rs.getString("title"));
            System.out.println("Author: " + rs.getString("author"));
            System.out.println("Available Copies: " + rs.getInt("available_copies"));
            System.out.println("------------------------");
        }
        rs.close() ;
        ps.close();
        con.close();
    }
    public Book searchBookById(int bookId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM books WHERE book_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, bookId);

        ResultSet rs = ps.executeQuery();

        Book book = null;

        if(rs.next())
        {
            book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("available_copies")
            );
        }

        rs.close();
        ps.close();
        con.close();

        return book;
    }

    public Book searchBookByTitle(String title) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM books WHERE title = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, title);

        ResultSet rs = ps.executeQuery();

        Book book = null;

        if(rs.next())
        {
            book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("available_copies")
            );
        }

        rs.close();
        ps.close();
        con.close();

        return book;
    }
    public void deleteBook(int bookId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "DELETE FROM books WHERE book_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, bookId);

        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0)
        {
            System.out.println("Book Deleted Successfully");
        }
        else
        {
            System.out.println("Book Not Found");
        }

        ps.close();
        con.close();
    }

    public void updateBook(int bookId,
                           String title,
                           String author,
                           int availableCopies) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "UPDATE books SET title = ?, author = ?, available_copies = ? WHERE book_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, title);
        ps.setString(2, author);
        ps.setInt(3, availableCopies);
        ps.setInt(4, bookId);

        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0)
        {
            System.out.println("Book Updated Successfully");
        }
        else
        {
            System.out.println("Book Not Found");
        }

        ps.close();
        con.close();
    }

    public void searchBooksByAuthor(String author) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM books WHERE author = ?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setString(1, author);

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while(rs.next())
        {
            found = true;

            System.out.println("Book ID: "
                    + rs.getInt("book_id"));

            System.out.println("Title: "
                    + rs.getString("title"));

            System.out.println("Author: "
                    + rs.getString("author"));

            System.out.println("Available Copies: "
                    + rs.getInt("available_copies"));

            System.out.println("------------------------");
        }

        if(!found)
        {
            System.out.println("No Books Found");
        }

        rs.close();
        ps.close();
        con.close();
    }

    public void searchBooksByTitle(String keyword) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM books WHERE title LIKE ?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setString(1, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while(rs.next())
        {
            found = true;

            System.out.println("Book ID: "
                    + rs.getInt("book_id"));

            System.out.println("Title: "
                    + rs.getString("title"));

            System.out.println("Author: "
                    + rs.getString("author"));

            System.out.println("Available Copies: "
                    + rs.getInt("available_copies"));

            System.out.println("------------------------");
        }

        if(!found)
        {
            System.out.println("No Books Found");
        }

        rs.close();
        ps.close();
        con.close();
    }




}
