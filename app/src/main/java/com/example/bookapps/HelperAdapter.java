package com.example.bookapps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HelperAdapter extends RecyclerView.Adapter {

    ArrayList<FetchData> fetchData;
    ArrayList<String> bookTypeList;

    public HelperAdapter(ArrayList<FetchData> fetchData) {
        this.fetchData = fetchData;
        bookTypeList = new ArrayList<>();
        for (FetchData temp: fetchData) {
            if (!bookTypeList.contains(temp.getType())) {
                bookTypeList.add(temp.getType());
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.titlebook_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        final String bookType = bookTypeList.get(position);
        viewHolderClass.mType.setText(bookType);
        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),ListOfBook.class);
                i.putExtra("key1", fetchData); //arraylist
                i.putExtra("key2", bookType);   //String
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookTypeList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView mType;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.type);
        }
    }
}
