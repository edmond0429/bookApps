package com.example.bookapps;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class addBookFragment extends Fragment {
    EditText mTypeOfBook, mTitleOfBook, mStoryOfBook, mUrlBook;
    Button btnAddBook;
    DatabaseReference mDatabaseReference;
    private ChipNavigationBar adminNav;
    private Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_add_book,container,false);

        mTypeOfBook = (EditText)view.findViewById(R.id.etType);
        mTitleOfBook = (EditText)view.findViewById(R.id.etTitle);
        mStoryOfBook = (EditText)view.findViewById(R.id.etExampleStory);
        mUrlBook = (EditText)view.findViewById(R.id.etUrl);
        btnAddBook = (Button)view.findViewById(R.id.btnAdd);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = mTypeOfBook.getText().toString();
                String title = mTitleOfBook.getText().toString();
                String story = mStoryOfBook.getText().toString();
                String imageOfBook = mUrlBook.getText().toString();

                FetchData mFetch = new FetchData(type,title,story,imageOfBook);
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("book");
                mDatabaseReference.push().setValue(mFetch);
                Toast.makeText(getActivity(), "Book added", Toast.LENGTH_SHORT).show();
            }
        });

        adminNav = view.findViewById(R.id.adminChipNavigation);
        adminNav.setItemSelected(R.id.addBook,true);
        adminNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener(){
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.addBook:
                        fragment = new addBookFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.adminProfile:
                        fragment = new adminLoggedInFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                }
            }
        });

        return view;
    }
}