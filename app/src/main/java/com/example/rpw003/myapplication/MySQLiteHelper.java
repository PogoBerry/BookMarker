package com.example.rpw003.myapplication;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BooksDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BOOKS = "books";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_PAGES = "pages";

    private SQLiteDatabase db;

    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    } // MySQLiteHelper

    @Override
    public void onCreate(SQLiteDatabase db)
    {
           /* // SQL statement to create book table
            String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "author TEXT," +
                "pages INTEGER )";
*/          String CREATE_BOOK_TABLE = "create table " + TABLE_BOOKS + "( " + KEY_ID;
        CREATE_BOOK_TABLE += " integer primary key autoincrement, " + KEY_TITLE;
        CREATE_BOOK_TABLE += " text, " + KEY_AUTHOR + " text, " + KEY_PAGES + " integer )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);

     } // onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);

    } // onUpGrade
//---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books +  delete all books
     */

// Books table name

    private static final String[] COLUMNS = {KEY_TITLE,KEY_AUTHOR,KEY_PAGES};

    public void addBook(Book book)
    {
        Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, book.getID()); // get id
        values.put(KEY_TITLE, book.getTitle()); // get title
        values.put(KEY_AUTHOR, book.getAuthor()); // get author
        values.put(KEY_PAGES, book.getPages()); // get pages


        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close

    } // addBook

    public Book getBook(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BOOKS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Book book = null;
        book.setTitle(cursor.getString(0));
        book.setAuthor(cursor.getString(1));
        book.setPages (Integer.parseInt(cursor.getString(2)));

        Log.d("getBook("+id+")", book.toString());

        // 5. return book
        return book;
    }

    // Get All Books
    public ArrayList<Book> getAllBooks() {

        ArrayList<Book> books = new ArrayList<Book>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOOKS;

        // 2. get reference to writable DB
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Book b;
        if (cursor.moveToFirst()) {
            do {
                List <ReadingEntry> r = new ArrayList<ReadingEntry>();

                b = new Book (0,"","",0, r);

                // issue: every time I leave the app and return, the last entry view gets replaced with this
                ReadingEntry re = new ReadingEntry ("",0);

                b.getEntries().add(re);
                b.setTitle(cursor.getString(1));
                b.setAuthor(cursor.getString(2));
                b.setPages(Integer.parseInt(cursor.getString(3)));
                //b.getEntries().get (b.getEntries().size()-1).setDate(cursor.getString(4));
                //b.getEntries().get (b.getEntries().size()-1).setPagesRead(Integer.parseInt(cursor.getString (5)));

                // Add book to books
                books.add(b);

             } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        cursor.close ();

        return books;
    }

    // Updating single book
    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put ("pages", book.getPages());

        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_TITLE+" = ?", // selections
                new String[] { String.valueOf(book.getTitle()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOOKS,
                KEY_TITLE+" = ?",
                new String[] { String.valueOf(book.getTitle()) });

        // 3. close
        db.close();

        Log.d("deleteBook", book.toString());

    }
}
