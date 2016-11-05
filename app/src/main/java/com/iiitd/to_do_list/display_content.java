package com.iiitd.to_do_list;

import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class display_content extends AppCompatActivity {

    String title,detail;
    int position;

    public static int NUM_PAGES = 0;
    public static ViewPager mViewPager;
    public static DataFragmentPageAdapter mMyFragmentPagerAdapter;
    static ArrayList<Detail> taskList;

    static DatabaseHelper myDb;
    int i = 0;

    public Toolbar toolbar;
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_content);

        myDb = new DatabaseHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        taskList = new ArrayList<>();


        title = this.getIntent().getStringExtra("title");
        detail = this.getIntent().getStringExtra("detail");
        position = this.getIntent().getIntExtra("position",-99);

        Cursor res = myDb.readData();

        while (res.moveToNext()) {
            taskList.add(new Detail(res.getString(0),res.getString(1)));
            i++;
        }

        NUM_PAGES = taskList.size() ;
        mMyFragmentPagerAdapter = new DataFragmentPageAdapter(getSupportFragmentManager(),taskList);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
        mViewPager.setCurrentItem(position);
    }
}
