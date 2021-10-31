package com.moringaschool.movieworld.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcel;

import com.moringaschool.movieworld.R;
import com.moringaschool.movieworld.adapters.MoviesPagerAdapter;
import com.moringaschool.movieworld.models.Result;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    private MoviesPagerAdapter adapterViewpager;
    List<Result> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        mMovies = Parcels.unwrap(getIntent().getParcelableExtra("movies"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewpager = new MoviesPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mMovies);
        mViewPager.setAdapter(adapterViewpager);
        mViewPager.setCurrentItem(startingPosition);
    }
}