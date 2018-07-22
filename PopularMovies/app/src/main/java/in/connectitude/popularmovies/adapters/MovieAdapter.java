package in.connectitude.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.entities.Movie;
import in.connectitude.popularmovies.ui.MovieDetails;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieCardViewHolder> {

    public Context mContext;

    public List<Movie> mListItems;

    public ArrayList<Movie> mTheoryListItems;

    public String favourites="null";


    Movie listItem;


    public MovieAdapter(Context context, List<Movie> mListItems) {

        mContext = context;
        this.mListItems = mListItems;


    }

    public MovieAdapter(Context context, List<Movie> mListItems,String favourites) {

        mContext = context;
        this.mListItems = mListItems;
        this.favourites = favourites;

    }


    @Override
    public MovieAdapter.MovieCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movies, parent, false);
        return new MovieAdapter.MovieCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieAdapter.MovieCardViewHolder holder, int position) {


        listItem = mListItems.get(position);
        String movieImageLink = listItem.getmMoviePosterPath();
        String imagePosterLink = "http://image.tmdb.org/t/p/w500/" + movieImageLink;

        holder.title.setText(listItem.getmMovieTitle());
        Picasso.with(mContext).load(imagePosterLink).into(holder.movieImageView);




    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class MovieCardViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.movie_Title)
        TextView title;
        public @BindView(R.id.movie_imageView)
        ImageView movieImageView;


        public MovieCardViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), MovieDetails.class);
                    intent.putExtra("movie_title", mListItems.get(position).getmMovieTitle());
                    intent.putExtra("movie_poster_path", mListItems.get(position).getmMoviePosterPath());
                    intent.putExtra("movie_backdropImage_path", mListItems.get(position).getmMovieBackDropPath());
                    intent.putExtra("movie_synopsis", mListItems.get(position).getmMoviePlotSynopsis());
                    intent.putExtra("movie_rating", mListItems.get(position).getmMovieVoteAverage().toString());
                    intent.putExtra("movie_releaseDate", mListItems.get(position).getmMovieReleaseDate());
                    intent.putExtra("movie_id", mListItems.get(position).getmMovieId().toString());

                    if(!favourites.equals("null")){
                        intent.putExtra("favourites", favourites);
                    }
                    context.startActivity(intent);

                }
            });


        }
    }


}


