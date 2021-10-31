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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.movieworld.Constants;
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


public class MoviesDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView mNameLabel;
    @BindView(R.id.releaseTextView) TextView mReleaseLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.synopsisTextView) TextView mSynopsisText;
    @BindView(R.id.languageTextView) TextView mLanguageLabel;
//    @BindView(R.id.) TextView mAddressLabel;
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
        String image = "https://image.tmdb.org/t/p/w500" + mMovies.getPosterPath();
        Picasso.get().load(image).into(mImageLabel);

        List<String> categories = new ArrayList<>();

//        for (Locale.Category category: mMovies.get)

        mNameLabel.setText(mMovies.getTitle());
        mRatingLabel.setText(Double.toString(mMovies.getVoteAverage()));
        mReleaseLabel.setText(mMovies.getReleaseDate());
        mSynopsisText.setText(mMovies.getOverview());
        mLanguageLabel.setText(mMovies.getOriginalLanguage());
        return view;
    }

    @Override
    public void onClick(View view) {

        if(view == mSaveRestaurantButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference movieRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.MOVIE_TITLE_CHILD)
                    .child(uid);

            DatabaseReference pushRef = movieRef.push();
            String pushId = pushRef.getKey();
            mMovies.setPushId(pushId);
            pushRef.setValue(mMovies);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}