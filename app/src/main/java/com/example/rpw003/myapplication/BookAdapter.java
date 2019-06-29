package com.example.rpw003.myapplication;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private Context mCtx;
    private int listLayoutRes;
    private static List<Book> bookList;
    private static SQLiteDatabase mDatabase;
    public static BookAdapter adapter;

    public BookAdapter (Context context , int listLayoutRes)
    {
        super (context,listLayoutRes);
        this.mCtx = context;
        this.listLayoutRes = listLayoutRes;

    } // BookAdapter


    public BookAdapter(Context mCtx, int listLayoutRes, List<Book> bookList, SQLiteDatabase mDatabase)
    {
        super(mCtx, listLayoutRes, bookList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.bookList = bookList;
        this.mDatabase = mDatabase;


    } // BookAdapter

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.content_main, null);

        //getting book of the specified position
        final Book book = bookList.get(position);

        //getting views
        final TextView bookTitle = view.findViewById(R.id.bookTitle);
        final TextView bookAuthor = view.findViewById(R.id.bookAuthor);
        final TextView bookPages = view.findViewById(R.id.bookPages);
        final TextView lastEntry = view.findViewById(R.id.lastEntry);

        //adding data to views
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPages.setText(String.valueOf(book.getPages()) + " pages");

        // calls the Context method getResources, which concatenates the string resource "last entry" with entry Date and Page
        String entry = mCtx.getResources().getString(R.string.last_entry, book.getEntries().get(book.getEntries().size() - 1).getDate(),
                book.getEntries().get(book.getEntries().size() - 1).getPagesRead());
        lastEntry.setText (entry);

        Button buttonView   = view.findViewById(R.id.buttonViewBook);
        Button buttonDelete = view.findViewById(R.id.buttonDeleteBook);

        //the delete operation

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                        {
                            String sql = "DELETE FROM books WHERE id = ?";

                            mDatabase.execSQL(sql, new Integer[]{book.getID()});

                            remove (book);

                            notifyDataSetChanged();

                            refreshBooksFromDatabase();

                        } // onClick

                }); // setPositiveButton

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }

                }); // setNegativeButton

                AlertDialog dialog = builder.create();

                dialog.show();

            } // onClick

        }); // buttonDelete

            buttonView.setOnClickListener (new View.OnClickListener ()
                {
                    @Override
                    public void onClick (View view)
                    {
                        viewBook(book);

                    } // onClick

                 });

         return view;

    } // getView

    public void viewBook (final Book book)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.activity_view, null);

        builder.setView(view);

        final ProgressBar progress = view.findViewById(R.id.progressBar2);

        final TextView viewTitle = view.findViewById(R.id.viewTitle);

        final TextView viewAuthor = view.findViewById(R.id.viewAuthor);

        final TextView viewPages = view.findViewById(R.id.viewPages);

        viewTitle.setText(book.getTitle());

        viewAuthor.setText(book.getAuthor());

        viewPages.setText(String.valueOf(book.getPages()) + " pages");

        progress.setMax(book.getPages());
        progress.setProgress(book.getEntries().get(book.getEntries().size() - 1).getPagesRead());

        final AlertDialog dialog = builder.create();

        dialog.show();

        view.findViewById(R.id.action_edit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                updateBook (book);
                dialog.dismiss();

            } // onClick

        }); // buttonEditButton;

        view.findViewById(R.id.entry_add).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addEntry(book);
                dialog.dismiss();
            } // onClick

        }); // buttonEditButton;

    } // updateBook

    // the edit operation
    public void updateBook (final Book book)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.activity_edit, null);

        builder.setView(view);

        final EditText editTitle = view.findViewById(R.id.editTitle);

        final EditText editAuthor = view.findViewById(R.id.editAuthor);

        final EditText editPages = view.findViewById(R.id.editPages);

        final ProgressBar progress = view.findViewById(R.id.progressBar3);

        editTitle.setText(book.getTitle());

        editAuthor.setText(book.getAuthor());

        editPages.setText(String.valueOf(book.getPages()));

        progress.setMax(book.getPages());

        // returns the number of pagesRead from the most recent Reading Entry
        progress.setProgress(book.getEntries().get(book.getEntries().size() - 1).getPagesRead());

        final AlertDialog dialog = builder.create();

        dialog.show();

        view.findViewById(R.id.action_done).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String title = editTitle.getText().toString().trim();
                String author = editAuthor.getText().toString().trim();
                String pages  = editPages.getText().toString().trim();

                if (title.isEmpty())
                {
                    editTitle.setError("The title can't be blank");
                    editTitle.requestFocus();
                    return;

                } // if

                if (author.isEmpty())
                {
                    editAuthor.setError("The author can't be blank");
                    editAuthor.requestFocus();
                    return;

                } // if

                if (pages.isEmpty())
                {
                    editPages.setError("The title can't be blank");
                    editPages.requestFocus();
                    return;

                } // if

                String sql = "UPDATE books \n" +
                        "SET title = ?, \n" +
                        "author = ?, \n" +
                        "pages = ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{title, author, pages, String.valueOf(book.getID())});


                Toast.makeText(mCtx, "Book Updated", Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();

                refreshBooksFromDatabase();
                dialog.dismiss();

              } // onClick

        }); // buttonEditButton;

    } // updateBook


    private void refreshBooksFromDatabase()
    {
        Cursor cursorBook = mDatabase.rawQuery("SELECT * FROM books", null);

        if (cursorBook.moveToFirst())
        {
            bookList.clear();

            do {
                // a new ReadingEntry arraylist is created with a default object that contains today's date and 0 pages
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String addDate = sdf.format (cal.getTime()); // today's date

                List <ReadingEntry> entries = new ArrayList<ReadingEntry>();

                final ReadingEntry re = new ReadingEntry(addDate, 0);
                entries.add (re);

                bookList.add(new Book
                        (
                            cursorBook.getInt(0),
                            cursorBook.getString(1),
                            cursorBook.getString(2),

                            cursorBook.getInt(3),

                            entries
                       ));

            } while (cursorBook.moveToNext());

            notifyDataSetChanged();

        } // if

        cursorBook.close();

    } // refreshBooksFromDatabase

    public void addBook (final Book book)
        {
            String title     = book.getTitle();
            String author    = book.getAuthor();
            String pages     = Integer.toString(book.getPages());

            String insertSQL = "INSERT INTO books \n" +
                    "(title,author,pages)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";

            mDatabase.execSQL(insertSQL, new String[]{title,author,pages});

            add(book);

            notifyDataSetChanged();

            refreshBooksFromDatabase();

         } // addBook

    public void addEntry (final Book book)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.activity_addentry, null);

        builder.setView(view);

        final EditText entryDate = view.findViewById(R.id.entryDate);

        final EditText entryPages = view.findViewById(R.id.entryPages);

        final AlertDialog dialog = builder.create();

        dialog.show();

        view.findViewById(R.id.buttonAddEntry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
                {
                    List <ReadingEntry> entries = new ArrayList<ReadingEntry>();

                    String date = entryDate.getText().toString();

                    String stringPages = entryPages.getText().toString();

                    int pages = Integer.parseInt(stringPages);

                    book.setEntries(entries);

                    ReadingEntry entry = new ReadingEntry(date, pages);

                    book.getEntries().add(entry);

                    Toast.makeText (mCtx, "Entry added ", Toast.LENGTH_SHORT).show();

                    notifyDataSetChanged();

                    dialog.dismiss();

                } // onClick

        }); // onClickListener

        notifyDataSetChanged();

    } // addEntry

} // class BookAdapter
