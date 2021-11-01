package com.moringaschool.movieworld.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.movieworld.R;
import com.moringaschool.movieworld.models.Result;
import com.moringaschool.movieworld.ui.MovieDetailsActivity;
import com.moringaschool.movieworld.util.ItemTouchHelperAdapter;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesArrayAdapter extends RecyclerView.Adapter<MoviesArrayAdapter.MoviesViewHolder> implements ItemTouchHelperAdapter {
    private Context mContext;
    private List<Result> mMovies;
    private String[] mYear;
    private ItemTouchHelper mTouchHelper;
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

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Result fromMovie = mMovies.get(fromPosition);
        mMovies.remove(fromMovie);
        mMovies.add(toPosition, fromMovie);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.mTouchHelper = touchHelper;
    }
    @Override
    public void onItemDismiss(int position) {
        mMovies.remove(position);
        notifyItemRemoved(position);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements
            View.OnTouchListener,
            GestureDetector.OnGestureListener {
        @BindView(R.id.movieAvatar) ImageView movies_Avatar;
        @BindView(R.id.movieTitleText) TextView titleTextView;
        @BindView(R.id.releaseDateText) TextView releaseDateTextView;
        @BindView(R.id.movieVote) TextView votesView;

        private Context mContext;
        GestureDetector mGestureDetector;

        public MoviesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            mGestureDetector = new GestureDetector(itemView.getContext(), this);

//            itemView.setOnClickListener(this);
            itemView.setOnTouchListener(this);
        }
        public void bindMovies(Result mMovies){
             String image = "https://image.tmdb.org/t/p/w500" + mMovies.getPosterPath();
             Picasso.get().load(image).into(movies_Avatar);
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

//        @Override
//        public void onClick(View v){
//            int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
//            intent.putExtra("position", itemPosition);
//            intent.putExtra("movies", Parcels.wrap(mMovies));
//            mContext.startActivity(intent);
//        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, MovieDetailsActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("movies", Parcels.wrap(mMovies));
            mContext.startActivity(intent);
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            mTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
    }
}

