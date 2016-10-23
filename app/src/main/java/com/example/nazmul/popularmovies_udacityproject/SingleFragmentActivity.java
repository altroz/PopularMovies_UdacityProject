package com.example.nazmul.popularmovies_udacityproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Ram Rooter on 10/22/2016.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_movie);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.activity_movie, fragment)
                    .commit();
        }
    }
}
