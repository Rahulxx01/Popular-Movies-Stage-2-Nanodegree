package in.connectitude.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.entities.Movie;
import in.connectitude.popularmovies.ui.MovieDetails;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieCardViewHolder> {

    public Context mContext;

    public List<Movie> mListItems;

    public ArrayList<Movie> mTheoryListItems;


    Movie listItem;


    public MovieAdapter(Context context, List<Movie> mListItems) {

        mContext = context;
        this.mListItems = mListItems;

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

        public TextView title;
        public ImageView movieImageView;
        public LinearLayout mCardElement;

        public MovieCardViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movie_Title);
            movieImageView = (ImageView) itemView.findViewById(R.id.movie_imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), MovieDetails.class);
                   // intent.putExtra("link",mListItems.get(position).getmTheoryLink());
                    context.startActivity(intent);

                }
            });


        }
    }


}


