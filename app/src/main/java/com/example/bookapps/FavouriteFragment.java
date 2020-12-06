package com.example.bookapps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;

public class FavouriteFragment extends Fragment {
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    private FirebaseRecyclerOptions<favBook> mFavBookFirebaseRecyclerOptions;
    private RecyclerView mRecyclerView;
    DatabaseReference mDatabaseReference;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    favAdapter mFavAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_favourite,container,false);
        mRecyclerView = view.findViewById(R.id.favRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(mAuth.getCurrentUser().getUid() == null) {
            fragment = new BlankFavFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            }else{
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("FavouriteBook").child(mAuth.getCurrentUser().getUid());
                mFavBookFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<favBook>().setQuery(mDatabaseReference, favBook.class).build();

                mFavAdapter = new favAdapter(mFavBookFirebaseRecyclerOptions, getActivity());
                mRecyclerView.setAdapter(mFavAdapter);
            }
        chipNavigationBar = view.findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.favourite,true);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener(){
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.category:
                        fragment = new CatogaryFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.favourite:
                        fragment = new FavouriteFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.profile:
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            fragment = new ProfileFragment();
                        }else{
                            fragment = new LogInFragment();
                        }
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                }
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        mFavAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFavAdapter.stopListening();
    }
}