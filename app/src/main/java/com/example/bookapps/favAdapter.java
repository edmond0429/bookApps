package com.example.bookapps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import www.sanju.motiontoast.MotionToast;

public class favAdapter extends FirebaseRecyclerAdapter<favBook,favAdapter.favViewHolder> {
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private Context mContext;
    public favAdapter(@NonNull FirebaseRecyclerOptions<favBook> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull final favViewHolder favViewHolder, int i, @NonNull final favBook favBook) {
        favViewHolder.mType.setText(favBook.getExample());
        favViewHolder.btnUnFav.setImageResource(R.drawable.ic_baseline_red_24);
        Glide.with(favViewHolder.mImageView.getContext()).load(favBook.getUrl()).into(favViewHolder.mImageView);
        favViewHolder.btnUnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favViewHolder.btnUnFav.setImageResource(R.drawable.shadow_login);
                deleteFav(favBook.getExample());
            }
        });
    }

    private void deleteFav(String example) {
        DatabaseReference dbFav = FirebaseDatabase.getInstance().getReference("FavouriteBook").child(mFirebaseAuth.getCurrentUser().getUid()).child(example);
        dbFav.removeValue();
        MotionToast.Companion.darkToast((Activity) mContext, "Unfavourited!", "Selected novel had been unfavourited",
                MotionToast.TOAST_DELETE,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(mContext, R.font.helvetica_regular));
    }

    @NonNull
    @Override
    public favViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_favourite_layout,parent,false);
        return new favViewHolder(view);
    }

    class favViewHolder extends RecyclerView.ViewHolder{

        TextView mType;
        CircleImageView mImageView;
        ImageButton btnUnFav;

        public favViewHolder(@NonNull View itemView) {
            super(itemView);
            mType = (TextView) itemView.findViewById(R.id.type);
            mImageView = (CircleImageView) itemView.findViewById(R.id.imgBook);
            btnUnFav = (ImageButton)itemView.findViewById(R.id.UnFavBtn);

        }
    }

}
