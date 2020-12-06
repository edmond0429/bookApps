package com.example.bookapps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import www.sanju.motiontoast.MotionToast;

public class HelperAdapater2 extends RecyclerView.Adapter {

    List<FetchData> fetchData;
    DatabaseReference mDatabaseReference ;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private Context mContext;

    public HelperAdapater2(List<FetchData> fetchData) {
        this.fetchData = fetchData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookitem_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        final FetchData fetchDataList = fetchData.get(position);
        viewHolderClass.mType.setText(fetchDataList.getExample());
        viewHolderClass.mType.setTextColor(Color.parseColor("#3b0101"));
        Picasso.get().load(fetchDataList.getUrl()).into(viewHolderClass.mImageView);

        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), BookDetails.class);
                i.putExtra("key", fetchDataList);
                view.getContext().startActivity(i);
            }
        });

        if (mFirebaseAuth.getCurrentUser() != null) {
            viewHolderClass.btnFav.setTag(R.drawable.ic_baseline_shadow_24);
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("FavouriteBook")
                    .child(mFirebaseAuth.getCurrentUser().getUid());
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String FavKey = ds.getKey();
                        if(FavKey.equals(fetchDataList.getExample())){
                            viewHolderClass.btnFav.setImageResource(R.drawable.ic_baseline_red_24);
                            viewHolderClass.btnFav.setTag(R.drawable.ic_baseline_red_24);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            viewHolderClass.btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer res = (Integer) viewHolderClass.btnFav.getTag();
                    if (res == R.drawable.ic_baseline_red_24) {
                        MotionToast.Companion.darkColorToast((Activity) view.getContext(),
                                "Unfavourited!", "You had Unfavourite the book",
                                MotionToast.TOAST_WARNING,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(view.getContext(),R.font.helvetica_regular));
                        viewHolderClass.btnFav.setImageResource(R.drawable.ic_baseline_shadow_24);
                        viewHolderClass.btnFav.setTag(R.drawable.ic_baseline_shadow_24);
                        DatabaseReference dbFav = mDatabase.getReference("FavouriteBook")
                                .child(mFirebaseAuth.getCurrentUser().getUid()).child(fetchDataList.getExample());
                        dbFav.removeValue();
                    } else {
                        MotionToast.Companion.darkColorToast((Activity) view.getContext(),"Favourited!",
                                "You had Favourite the novel",
                                MotionToast.TOAST_INFO,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(view.getContext(),R.font.helvetica_regular));
                        viewHolderClass.btnFav.setImageResource(R.drawable.ic_baseline_red_24);
                        viewHolderClass.btnFav.setTag(R.drawable.ic_baseline_red_24);
                        favBook mFavbook = new favBook(fetchDataList.getExample(), fetchDataList.getUrl(),
                                fetchDataList.getType(), fetchDataList.getExampleStory());

                        mDatabase.getReference("FavouriteBook")
                                .child(mFirebaseAuth.getCurrentUser().getUid())
                                .child(fetchDataList.getExample())
                                .setValue(mFavbook);
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return fetchData.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView mType;
        ImageView mImageView;
        ImageButton btnFav;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.type);
            mImageView = itemView.findViewById(R.id.imgBook);
            btnFav = itemView.findViewById(R.id.favBtn);
        }
    }
}
