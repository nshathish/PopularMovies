package com.softinov.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softinov.popularmovies.Entities.Movie;
import com.softinov.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nshat on 09/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final String TAG = MovieAdapter.class.getSimpleName();
    private final MovieAdapterOnClickHandler m_clickHandler;

    private Context m_context;
    private List<Movie> m_movieList;

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        m_context = context;
        m_clickHandler = clickHandler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movieItem = m_movieList.get(position);
        ImageView imageView = movieAdapterViewHolder.getMovieListImageItem();
        Picasso.with(m_context).load(movieItem.getPosterListImageUri()).into(imageView);

    }

    @Override
    public int getItemCount() {
        if(m_movieList == null || m_movieList.isEmpty()) return 0;
        return m_movieList.size();
    }

    public void setMovieData(List<Movie> movieData) {
        m_movieList = movieData;
        notifyDataSetChanged();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView m_IV_movieListTb;

        public ImageView getMovieListImageItem() {
            return m_IV_movieListTb;
        }

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            m_IV_movieListTb = (ImageView)itemView.findViewById(R.id.iv_movie_list_tb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPos = getAdapterPosition();
            Movie movie = m_movieList.get(adapterPos);
            m_clickHandler.onClick(movie);
        }
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }
}
