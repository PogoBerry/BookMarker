package com.example.rpw003.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Book
{
    private String title,
                   author,
                     date;

    private int id = 0;
    private int pages = 0;

    private int pagesRead = 0;

    private List <ReadingEntry> entries = new ArrayList <> ();

    public Book (int i, String t, String a, int p)
    {
        this.id        = i;
        this.title     = t;

        this.author    = a;
        this.pages     = p;

    } // Book constructor

    public Book (int i, String t, String a, int p, String d, int pr)
    {
        this.id         = i;
        this.title      = t;

        this.author     = a;
        this.pages      = p;

        this.date       = d;
        this.pagesRead  = pr;

    } // Book constructor

     public Book (int i, String t, String a, int p, List <ReadingEntry> e)
    {
        this.id          = i;
        this.title       = t;

        this.author      = a;
        this.pages       = p;
        this.entries     = e;

    } // Book constructor

    public int getID()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public int getPages()
    {
        return pages;
    }

    public String getDate() { return date; }

    public List<ReadingEntry> getEntries() { return entries; }

    public void setID (int id) {this.id = id;}

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setPages(int pages)
    {
        this.pages = pages;
    }

    public void setDate(String date) { this.date = date; }

    public void setPagesRead(int pagesRead) { this.pagesRead = pagesRead; }

    public void setEntries(List<ReadingEntry> entries) { this.entries = entries; }
    @Override
    public String toString() {
        return "Book{" +
                "id ='" + id + '\'' +
                ", title ='" + title + '\'' +
                ", author =" + author +
                ", pages=" + pages +
                '}';

    } // toString

} // class Book
