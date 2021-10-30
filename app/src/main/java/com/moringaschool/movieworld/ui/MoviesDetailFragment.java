package com.moringaschool.movieworld.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.movieworld.R;
import com.moringaschool.movieworld.models.Result;
import com.moringaschool.movieworld.models.VisionBusiness;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesDetailFragment extends Fragment {
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView mNameLabel;
    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Result mMovies;
//    private VisionBusiness moviesList;

    public MoviesDetailFragment() {
        // Required empty public constructor
    }


    public static MoviesDetailFragment newInstance(Result movies) {
        MoviesDetailFragment fragment = new MoviesDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("movies", Parcels.wrap(movies));
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mMovies = Parcels.unwrap(getArguments().getParcelable("movies"));
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mMovies.getPosterPath()).into(mImageLabel);

        List<String> categories = new ArrayList<>();

//        for (Locale.Category category: mMovies.get)

        mNameLabel.setText(mMovies.getTitle());
        mRatingLabel.setText(Double.toString(mMovies.getVoteAverage()));
        return view;
    }
}