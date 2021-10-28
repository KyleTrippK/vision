package com.moringaschool.movieworld.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.movieworld.Constants;
import com.moringaschool.movieworld.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference mSearchedMovie;
    private ValueEventListener mSearchedMovieListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.searchButton) Button search_Button;
    @BindView(R.id.searchBar) EditText search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedMovie = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.MOVIE_TITLE_CHILD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        search_Button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view == search_Button){
            String query = search.getText().toString();
            if(!(query).equals("")){
                addToSharedPreferences(query);
                saveMovieToFirebase(query);
            }
            Intent intent = new Intent(MainActivity.this, MoviesList.class);
            intent.putExtra("query", query);
            startActivity(intent);
        }
    }
    private void addToSharedPreferences(String query){
        mEditor.putString(Constants.SEARCH_PREFERENCE, query).apply();
    }

    public void saveMovieToFirebase(String QueriedMovie) {
        mSearchedMovie.push().setValue(QueriedMovie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}