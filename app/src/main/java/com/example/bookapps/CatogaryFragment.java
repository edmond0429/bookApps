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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class CatogaryFragment extends Fragment {

    RecyclerView mRecyclerView;
    ArrayList<FetchData> mFetchData;
    HelperAdapter mHelperAdapter;
    DatabaseReference mDatabaseReference;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

//    QuestionMember member;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_catogary,container,false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mFetchData = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("book");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    FetchData fetchDataList = ds.getValue(FetchData.class);
                    mFetchData.add(fetchDataList);
                }
                mHelperAdapter = new HelperAdapter(mFetchData);
                mRecyclerView.setAdapter(mHelperAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        chipNavigationBar = view.findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.category,true);
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
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            fragment = new BlankFavFragment();
                        }else{
                            fragment = new FavouriteFragment();
                        }
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
}