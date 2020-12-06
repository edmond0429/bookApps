package com.example.bookapps;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookDetails extends AppCompatActivity {

    TextView tvStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_book_story);

        tvStory = findViewById(R.id.TStory);
        Intent i = this.getIntent();
        Bundle bundle = i.getExtras();
        FetchData fetchData = (FetchData) bundle.getSerializable("key");
        tvStory.setText(fetchData.getExampleStory().replace("_b","\n"));

    }
}
