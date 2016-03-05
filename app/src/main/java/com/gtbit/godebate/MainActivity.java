package com.gtbit.godebate;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Toolbar toolbar;
    ListView topicsList;
    ArrayAdapter adapter;
    String titles[];
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Resources resources = getResources();
        titles = resources.getStringArray(R.array.titles);

        topicsList = (ListView) findViewById(R.id.topics_list);
        adapter = new ArrayAdapter(this, R.layout.topics_row,R.id.topics, titles);
        topicsList.setAdapter(adapter);

        topicsList.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        main2Activity();
        this.position=position;

    }

    private void main2Activity() {

        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        i.putExtra("position", titles[position]);
        startActivity(i);

    }
}
