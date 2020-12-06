package com.example.bookapps;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class BlankFavFragment extends Fragment {
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_fav, container, false);



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