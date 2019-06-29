package com.example.rpw003.myapplication;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
{
    public static final String DATABASE_NAME = "mybookdatabase";
    private String MA = "DataActivity";

    private SQLiteDatabase mDatabase;
    private ListView listViewBooks;
    private BookAdapter adapter;
    private MySQLiteHelper helper = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbooks);

        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewBooks = findViewById(R.id.listViewBooks);

        //opening the database
        mDatabase = openOrCreateDatabase(MySQLiteHelper.DATABASE_NAME, MODE_PRIVATE, null);

        showBooksFromDatabase();

    } // onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    } // onCreateOptionsMenu

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
                Log.w ("MainActivity", "Add selected");
                Intent addIntent = new Intent (this, AddBookActivity.class);
                this.startActivity (addIntent);
                return true;
            case R.id.action_sort:
                Log.w ("MainActivity", "Sort selected");
                return true;
            case R.id.action_search:
                Log.w ("MainActivity", "Search selected");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch

    } // onOptionsItemSelected

    private void showBooksFromDatabase ()
    {
       adapter = new BookAdapter(this, R.layout.content_main, helper.getAllBooks(), mDatabase);

       listViewBooks.setAdapter(adapter);

    } // showBooksFromDatabase

    protected void onStart ()
    {
        super.onStart();
        Log.w (MA, "Inside  AddBookActivity:onStart\n");

    }

    protected void onRestart ()
    {
        super.onRestart();
        Log.w (MA, "Inside AddBookActivity:onRestart\n");

    }

    protected void onResume ()
    {
        super.onResume();
        Log.w (MA, "Inside AddBookActivity:onResume\n");

    }

    protected void onPause ()
    {
        super.onPause();
        Log.w (MA, "Inside AddBookActivity:onPause\n");

    }

    protected void onStop ()
    {
        super.onStop();
        Log.w (MA, "Inside AddBookActivity:onStop\n");

    }

    protected void onDestroy ()
    {
        super.onDestroy();
        Log.w (MA, "Inside AddBookActivity:onDestroy\n");

    }

} // class MainActivity


