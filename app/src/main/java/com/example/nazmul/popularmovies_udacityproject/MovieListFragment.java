package com.example.nazmul.popularmovies_udacityproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ram Rooter on 10/22/2016.
 */

public class MovieListFragment extends Fragment {

    private static final String TAG = "MovieListFragment";
    private RecyclerView mMovieRecyclerView;
    private List<Movie> mMovies = new ArrayList<>();
    private ThumbnailDownloader<MovieHolder> mThumbnailDownloader;


    public static MovieListFragment newInstance(){
        return new MovieListFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchMovieTask().execute();

        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setmThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<MovieHolder>(){
                    public void onThumbnailDownloaded(MovieHolder movieHolder, Bitmap bitmap){
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        movieHolder.bindDrawable(drawable);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieRecyclerView=(RecyclerView)v.findViewById(R.id.fragment_movie_list);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setUpAdapter();
        return v;
    }

    public void onDestroyView(){
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    public void onDestroy(){
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    private void setUpAdapter(){
        if(isAdded()){
            mMovieRecyclerView.setAdapter(new MovieAdapter(mMovies));
        }
    }

    protected class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>>{
        protected List<Movie> doInBackground(Void... params){
            return new MovieFetcher().fetchItems();
        }
    }

    protected void onPostExecute(List<Movie>movies){
        mMovies = movies;
        setUpAdapter();
    }

    private class MovieHolder extends RecyclerView.ViewHolder{
        private ImageView mItemImageView;
        public MovieHolder(View itemView){
            super(itemView);
            // mTitleTextView = (TextView)itemView;
            mItemImageView = (ImageView) itemView.findViewById(R.id.movie_list_item);
        }
        public void bindDrawable(Drawable drawable){
            mItemImageView.setImageDrawable(drawable);
        }

    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        private List<Movie> mMovies;

        public MovieAdapter(List<Movie>movies){
            mMovies = movies;
        }

        public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            //TextView textView = new TextView(getActivity());
            //return new MovieHolder(textView);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
            return new MovieHolder(view);
        }

        public void onBindViewHolder(MovieHolder movieHolder, int position){
            Movie movie = mMovies.get(position);
            // movieHolder.bindMovie(movie);
            Drawable placeholder = getResources().getDrawable(R.drawable.sunset);
            movieHolder.bindDrawable(placeholder);
            mThumbnailDownloader.queueThumbnail(movieHolder, movie.getUrl());
        }

        public int getItemCount(){
            return mMovies.size();
        }
    }



}
