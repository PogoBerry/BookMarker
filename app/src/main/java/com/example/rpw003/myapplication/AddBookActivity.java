package com.example.rpw003.myapplication;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.net.Uri;
import android.database.Cursor;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.util.Log;

// used to be MainActivity
public class AddBookActivity extends AppCompatActivity
{
    private MySQLiteHelper dbManager;
    private String DA = "MainActivity";
    private SQLiteDatabase mDatabase;
    private static BookAdapter adapter;
    private static int idCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        dbManager = new MySQLiteHelper (this);

        adapter = new BookAdapter(this, R.layout.content_main);

        mDatabase = openOrCreateDatabase(MySQLiteHelper.DATABASE_NAME, MODE_PRIVATE, null);

    } // onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert, menu);
        return true;

    } // onCreateOptionsMenu

    public void insert ()
    {
        EditText    bookTitle  =  findViewById(R.id.text1);
        EditText    bookAuthor =  findViewById(R.id.text2);
        EditText    bookPages  =  findViewById(R.id.text3);

        String title        = bookTitle.getText().toString();
        String author       = bookAuthor.getText().toString();
        String stringPages  = bookPages.getText().toString();

        // insert book into the database here
        try {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String addDate = sdf.format (cal.getTime()); // today's date

            List <ReadingEntry> entries = new ArrayList<ReadingEntry>();

            final ReadingEntry re = new ReadingEntry(addDate, 0);
            entries.add (re);

            Integer pages = Integer.parseInt(stringPages);

            Book book = new Book (idCount, title, author, pages, entries);
            book.setID (idCount++);

            adapter.addBook (book);

            Toast.makeText (this, "Book added", Toast.LENGTH_SHORT).show();

            } // try

        catch (NumberFormatException nfe) {
            Toast.makeText (this, "Pages error", Toast.LENGTH_LONG).show();

            } // catch

        // clear the data
        bookTitle.setText ("");
        bookAuthor.setText("");
        bookPages.setText ("");

        finish();

     } // insert

     @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_add:
                Log.w ("AddBookActivity", "Done selected");
                insert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch

    } // onOptionsItemSelected

    protected void onStart ()
    {
        super.onStart();
        Log.w (DA, "Inside AddBookActivity:onStart\n");

    }

    protected void onRestart ()
    {
        super.onRestart();
        Log.w (DA, "Inside AddBookActivity:onRestart\n");

    }

    protected void onResume ()
    {
        super.onResume();
        Log.w (DA, "Inside AddBookActivity:onResume\n");

    }

    protected void onPause ()
    {
        super.onPause();
        Log.w (DA, "Inside AddBookActivity:onPause\n");

    }

    protected void onStop ()
    {
        super.onStop();
        adapter.notifyDataSetChanged();
        Log.w (DA, "Inside AddBookActivity:onStop\n");

    }

    protected void onDestroy ()
    {
        super.onDestroy();
        adapter.notifyDataSetChanged();
        Log.w (DA, "Inside AddBookActivity:onDestroy\n");

    }

} // class AddBookActivity
