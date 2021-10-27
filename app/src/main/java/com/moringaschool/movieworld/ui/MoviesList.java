package com.moringaschool.movieworld.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.movieworld.Constants;
import com.moringaschool.movieworld.R;
import com.moringaschool.movieworld.adapters.MoviesArrayAdapter;
import com.moringaschool.movieworld.models.Result;
import com.moringaschool.movieworld.models.VisionBusiness;
import com.moringaschool.movieworld.network.MovieWorldApi;
import com.moringaschool.movieworld.network.MovieWorldClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesList extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String TAG = MoviesList.class.getSimpleName();
    @BindView(R.id.moviesChoice) TextView moviesChoice;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    private String mSearchHistory;

    private MoviesArrayAdapter adapter;
    public List<Result> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);
        ButterKnife.bind(this);

        // shared preferences

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSearchHistory = mSharedPreferences.getString(Constants.SEARCH_PREFERENCE, null);

        Log.d("Shared Pref Location ", "message" + mSearchHistory);

//        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MoviesList.this, MovieDetails.class);
//                startActivity(intent);
//            }
//        });

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        MovieWorldApi client = MovieWorldClient.getClient();
        Call<VisionBusiness> call = client.getMovies(query, Constants.api_key);
        call.enqueue(new Callback<VisionBusiness>() {
            @Override
            public void onResponse(Call<VisionBusiness> call, Response<VisionBusiness> response) {
                hideProgressBar();

                if (response.isSuccessful()) {
                    movies = response.body().getResults();
                    adapter = new MoviesArrayAdapter(MoviesList.this, movies);

                    mRecyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MoviesList.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showMovies();
                }

                else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<VisionBusiness> call, Throwable t) {
                Log.e("Error Message", "onFailure: ",t );
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void addToSharedPreferences(String query){
        mEditor.putString(Constants.SEARCH_PREFERENCE, query).apply();
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showMovies() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
