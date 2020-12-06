package com.example.bookapps;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class adapterBook  extends RecyclerView.Adapter<adapterBook.BookViewHold>  {

        ArrayList<bookHelper> bookLaocations;
        final private ListItemClickListener mOnClickListener;

        public adapterBook(ArrayList<bookHelper> bookLaocations, ListItemClickListener listener) {
            this.bookLaocations = bookLaocations;
            mOnClickListener = listener;
        }

        @NonNull

        @Override
        public BookViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_recyclercard, parent, false);
            return new BookViewHold(view);

        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHold holder, int position) {
            bookHelper bookhelper = bookLaocations.get(position);
            holder.image.setImageResource(bookhelper.getImage());
            holder.title.setText(bookhelper.getTitle());
            holder.relativeLayout.setBackground(bookhelper.getgradient());
        }

        @Override
        public int getItemCount() {
            return bookLaocations.size();

        }

        public interface ListItemClickListener {
            void onbookListClick(int clickedItemIndex);
        }

        public class BookViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView image;
            TextView title;
            RelativeLayout relativeLayout;

            public BookViewHold(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                //hooks

                image = itemView.findViewById(R.id.book_image);
                title = itemView.findViewById(R.id.book_title);
                relativeLayout = itemView.findViewById(R.id.background_color);
            }

            @Override
            public void onClick(View v) {
                int clickedPosition = getAdapterPosition();
                mOnClickListener.onbookListClick(clickedPosition);
            }
        }
}


