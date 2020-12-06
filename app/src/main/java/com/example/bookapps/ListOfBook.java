package com.example.bookapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListOfBook extends AppCompatActivity {

    HelperAdapater2 mHelperAdapater2;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_book);

        mRecyclerView = findViewById(R.id.recycleViewListOfBook);
        Intent i = this.getIntent();
        Bundle bundle = i.getExtras();
        ArrayList<FetchData> fetchData = (ArrayList<FetchData>) bundle.getSerializable("key1");
        String bookType = bundle.getString("key2");
        ArrayList<FetchData> temp = new ArrayList<>();
        for (FetchData fd: fetchData) {
            if (fd.getType().equals(bookType)){
                temp.add(fd);
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHelperAdapater2 = new HelperAdapater2(temp);
        mRecyclerView.setAdapter(mHelperAdapater2);
    }
}