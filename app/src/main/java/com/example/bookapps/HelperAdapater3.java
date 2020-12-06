package com.example.bookapps;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HelperAdapater3 extends RecyclerView.Adapter {
    List<String> fetchData;
    ArrayList<String> subItemListFinal = new ArrayList<>();

    public HelperAdapater3(List<String> fetchData) {
        this.fetchData = fetchData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_of_book_story,parent,false);
        HelperAdapater3.ViewHolderClass viewHolderClass = new HelperAdapater3.ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HelperAdapater3.ViewHolderClass viewHolderClass = (HelperAdapater3.ViewHolderClass) holder;
        final String fetchDataList = fetchData.get(position);
        viewHolderClass.mType.setText(fetchDataList);
        viewHolderClass.mType.setTextColor(Color.parseColor("#07aba5"));
    }

    @Override
    public int getItemCount() {
        return fetchData.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView mType;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.bookDetail);
        }
    }
}
