package com.moringaschool.movieworld.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.movieworld.R;
import com.moringaschool.movieworld.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesArrayAdapter extends RecyclerView.Adapter<MoviesArrayAdapter.MoviesViewHolder> {
    private Context mContext;
    private List<Result> mMovies;
    private String[] mYear;
    public MoviesArrayAdapter(Context mContext, List<Result> movies){
        this.mContext = mContext;
        this.mMovies = movies;
        this.mYear = mYear;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_item, parent, false);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bindMovies(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movieAvatar) ImageView movies_Avatar;
        @BindView(R.id.movieTitleText) TextView titleTextView;
        @BindView(R.id.releaseDateText) TextView releaseDateTextView;
        @BindView(R.id.movieVote) TextView votesView;

        private Context mContext;

        public MoviesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
        public void bindMovies(Result mMovies){
             String image = "https://image.tmdb.org/t/p/w500" + mMovies.getPosterPath();
             Picasso.get().load(image).into(movies_Avatar);
            // http://image.tmdb.org/t/p/w500/kv3tPDvWyGTQ2Nb66LTXwK18iXR.jpg
              Log.d(TAG, "image path" + image);
            titleTextView.setText(mMovies.getTitle());
            releaseDateTextView.setText("Release: " + mMovies.getReleaseDate());
            votesView.setText("Votes: " + mMovies.getVoteAverage());
//            for(int i=0; i <mMovies.getPosterPath().length(); i++){
//                String image = "http://image.tmdb.org/t/p/w500" + mMovies.getPosterPath();
//                Log.d("Image Picture", "Image: " + image);
//                Picasso.get().load(image).into(movies_Avatar);
//            }
            Log.d(TAG, "bindMovies: " + mMovies.getPosterPath().length());
        }
    }
}

