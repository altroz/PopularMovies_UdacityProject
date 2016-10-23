package com.example.nazmul.popularmovies_udacityproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieListActivity extends SingleFragmentActivity {

    public Fragment createFragment(){
        return new MovieListFragment().newInstance();
    }
}
