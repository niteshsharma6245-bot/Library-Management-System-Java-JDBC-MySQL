package com.nitesh.model;

public class Book
{
    private int bookId ;
    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    private String title ;
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private String author ;
    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    private int availableCopies ;
    public int getAvailableCopies()
    {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies)
    {
        this.availableCopies = availableCopies;
    }

    public Book(  String title , String author , int availableCopies )
    {
        this.title = title ;
        this.author = author ;
        this.availableCopies = availableCopies ;
    }
    public Book(int bookId, String title, String author, int availableCopies)
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }



}
