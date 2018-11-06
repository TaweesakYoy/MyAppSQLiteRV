package com.example.taweesak.myappsqliterv;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.taweesak.myappsqliterv.Data.Model;
import com.example.taweesak.myappsqliterv.Data.SqliteDatabase;
import com.example.taweesak.myappsqliterv.adapter.MyAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView rv = (RecyclerView)findViewById(R.id.alarm_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(this);
        List<Model> allModel = mDatabase.listModel();


        if(allModel.size() > 0){
            rv.setVisibility(View.VISIBLE);
            MyAdapter mAdapter = new MyAdapter(this, allModel);
            rv.setAdapter(mAdapter);

        }else {
            rv.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });


    }

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_alarm_layout, null);

        final EditText titleField = (EditText)subView.findViewById(R.id.enter_title);
        final EditText contentField = (EditText)subView.findViewById(R.id.enter_content);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new alarm");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String title = titleField.getText().toString();
                final String content = contentField.getText().toString();

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Model newModel = new Model(title, content);
                    mDatabase.addAlarm(newModel);

                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }
            }

        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }


}
