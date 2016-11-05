package com.iiitd.to_do_list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    FloatingActionButton addItem;
    static DatabaseHelper myDb;

    public String[] myList;
    int i = 0;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private RecyclerViewAdapter mAdapter;

    public static Toolbar toolbar;
    public static ImageButton Del;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        addItem = (FloatingActionButton) findViewById(R.id.add_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Del = (ImageButton) findViewById(R.id.delete);
        setSupportActionBar(toolbar);

        Cursor res = myDb.readData();

        //entering the data from database to list
        myList = new String[100];
        myList[0] = "No content";
        while (res.moveToNext()) {
            myList[i] = res.getString(0);
            i++;
        }

        //calling recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new RecyclerViewAdapter(getApplicationContext(), myList);
        mRecyclerView.setAdapter(mAdapter);


        //Custom dialog box
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("To Do");
                final EditText title = (EditText) dialog.findViewById(R.id.title);
                final EditText detail = (EditText) dialog.findViewById(R.id.detail);

                dialog.show();
                Button save = (Button) dialog.findViewById(R.id.save);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (title.getText().toString().equals("") || detail.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "Fill all the details.", Toast.LENGTH_SHORT).show();
                        } else {
                            Boolean isInserted = myDb.insertData(title.getText().toString(), detail.getText().toString());
                            if (!isInserted)
                                Toast.makeText(MainActivity.this, "Error in inserting data", Toast.LENGTH_SHORT).show();
                            else {
                                myList[i] = title.getText().toString();
                                mRecyclerView = (RecyclerView) findViewById(R.id.list);
                                mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setLayoutManager(mGridLayoutManager);
                                mAdapter = new RecyclerViewAdapter(getApplicationContext(), myList);
                                mRecyclerView.setAdapter(mAdapter);
                                i++;
                            }
                            dialog.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    //displaying the data in recycler view
    public static void display(Context context,String title,int position) {
        Cursor res1 = myDb.getdetail(title);
        StringBuffer buffer = new StringBuffer();
        res1.moveToNext();
        if (res1.getCount() == 0)
            Toast.makeText(context, "No detail Exist", Toast.LENGTH_SHORT).show();
        else {
            buffer.append(res1.getString(0));
            Intent a = new Intent(context, display_content.class);
            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.putExtra("title", title);
            a.putExtra("detail", buffer.toString());
            a.putExtra("position",position);
            context.startActivity(a);
        }

    }

    //deleting data from recycler view using the TOOLBAR button
    public static void delete(final Context context, final String title) {
        MainActivity.Del.setVisibility(View.VISIBLE);
        MainActivity.Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteData(title);
                if (deletedRows > 0) {
                    Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(context, MainActivity.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(a);
                }
            }
        });
    }

}
