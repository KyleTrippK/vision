package com.moringaschool.movieworld.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringaschool.movieworld.models.Result;
import com.moringaschool.movieworld.ui.MoviesDetailFragment;

import java.util.List;

public class MoviesPagerAdapter extends FragmentPagerAdapter {

    private List<Result> mMovies;

    public MoviesPagerAdapter(@NonNull FragmentManager fm, int behaviour, List<Result> movies){
        super(fm, behaviour);
        mMovies = movies;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MoviesDetailFragment.newInstance(mMovies.get(position));
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mMovies.get(position).getTitle();
    }
}
