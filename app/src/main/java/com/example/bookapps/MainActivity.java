package com.example.bookapps;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements adapterBook.ListItemClickListener{

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    RecyclerView bookRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        bookRecycler = findViewById(R.id.my_recycler);
        bookRecycler();
        chipNavigationBar = findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Fragment()).commit();

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://wallpapercave.com/wp/wp3539293.jpg","Book"));
        slideModels.add(new SlideModel("https://i.ytimg.com/vi/P7xSKwoUT4Q/hqdefault.jpg?sqp=-oaymwEXCNACELwBSFryq4qpAwkIARUAAIhCGAE=&rs=AOn4CLASVmH5blEyuweMFUTB5LtEoVudxw","Soul of studying"));
        slideModels.add(new SlideModel("https://www.quotationof.com/images/leaning-quotes-4.jpg","Learning"));
        slideModels.add(new SlideModel("https://previews.123rf.com/images/alenast/alenast1608/alenast160800079/63392498-inspirational-motivational-quote-about-reading-vector-simple-design.jpg","Reading"));
        imageSlider.setImageList(slideModels,true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener(){
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.category:
                        fragment = new CatogaryFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.favourite:
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            fragment = new BlankFavFragment();
                        }else{
                            fragment = new FavouriteFragment();
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.profile:
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            fragment = new ProfileFragment();
                        }else{
                            fragment = new LogInFragment();
                        }
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit();
                }
            }
        });
    }
    private void bookRecycler() {
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                                        new int[]{0x00111111, 0x55111111,0x00111111});
        gradient1.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        bookRecycler.setHasFixedSize(true);
        bookRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ArrayList<bookHelper> booklocations = new ArrayList<>();
        booklocations.add(new bookHelper(gradient1, R.drawable.p1, "Pride and Prejudice by Jane Austen"));
        booklocations.add(new bookHelper(gradient1, R.drawable.p2, "Moby Dick by Herman Melville"));
        booklocations.add(new bookHelper(gradient1, R.drawable.p3, "The Face: A Novel by Dean Koontz"));
        booklocations.add(new bookHelper(gradient1, R.drawable.p4, "The Girl with the Dragon Tattoo by Stieg Larsson"));
        booklocations.add(new bookHelper(gradient1, R.drawable.p5, "The Sentinel by Jeremy Bishop"));

        adapter = new adapterBook(booklocations,this);
        bookRecycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onbookListClick(int clickedItemIndex) {
    }
}