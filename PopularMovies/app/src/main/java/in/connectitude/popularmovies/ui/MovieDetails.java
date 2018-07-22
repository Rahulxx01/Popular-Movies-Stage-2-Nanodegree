package in.connectitude.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.adapters.ReviewAdapter;
import in.connectitude.popularmovies.adapters.TrailerAdapter;
import in.connectitude.popularmovies.database.AppExecutors;
import in.connectitude.popularmovies.database.MovieDatabase;
import in.connectitude.popularmovies.entities.Constants;
import in.connectitude.popularmovies.entities.Movie;
import in.connectitude.popularmovies.utils.QueryUtils;

public class MovieDetails extends AppCompatActivity {


    @BindView(R.id.backdrop_ImageView)
    ImageView mBackDropImageView;
    @BindView(R.id.movieName_textView)
    TextView mMovieNameTextView;
    @BindView(R.id.genre_movies)
    TextView mGenresTextView;
    @BindView(R.id.movieRating_textView)
    TextView mMovieRatingTextView;
    @BindView(R.id.censor_textView)
    TextView mCensorTextView;
    @BindView(R.id.movieReleaseDate_textView)
    TextView mMovieReleaseDateTextView;
    @BindView(R.id.movieRunTime_textView)
    TextView mMovieRunTime;
    @BindView(R.id.movieWebsite_textView)
    TextView mMovieWebsite;
    @BindView(R.id.movieBudget_textView)
    TextView mMovieBudget;
    @BindView(R.id.movieDescription_TextView)
    TextView mMovieDescriptionTextView;
    @BindView(R.id.reviewCount_textView)
    TextView mReviewCountTextView;

    @BindView(R.id.favouritesButton)
    ToggleButton favouritesButton;


    @BindView(R.id.moviePoster_ImageView)
    ImageView mMoviePosterImageView;


    @BindView(R.id.trailers_recyclerView)
    RecyclerView trailerRecyclerView;

    @BindView(R.id.reviews_recyclerView)
    RecyclerView reviewsRecyclerView;

    @BindView(R.id.movieInformation)
    LinearLayout movieInformation;


    public static String month = "";
    public static String year = "";
    public static String date = "";
    public String DETAIL_URL;
    public String TRAILER_URL;
    public String REVIEW_URL;
    String movieId;

    public List<Movie> movieList;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;

    LinearLayoutManager mLinearLayoutManagerReviews;
    LinearLayoutManager mLinearLayoutManager;

    String posterImagePath = "";
    String backDropImagePath = "";
    Long budget;
    Long runtime;

    private Movie movie;
    MovieDatabase movieDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        if(!checkInternetConnectivity()){
            movieInformation.setVisibility(View.GONE);
        }


        mMovieNameTextView.setText(getIntent().getStringExtra("movie_title"));
        mMovieRatingTextView.setText(getIntent().getStringExtra("movie_rating") + "/10");
        mMovieReleaseDateTextView.setText(formatDate(getIntent().getStringExtra("movie_releaseDate")));
        mMovieDescriptionTextView.setText(getIntent().getStringExtra("movie_synopsis"));
        movieId = getIntent().getStringExtra("movie_id");
        DETAIL_URL = Constants.BASE_URL + movieId + Constants.API_KEY_DETAILS;
        TRAILER_URL = Constants.BASE_URL + movieId + Constants.API_TRAILER_KEY_DETAILS;
        REVIEW_URL = Constants.BASE_URL + movieId + Constants.API_REVIEW_KEY_DETAILS;

        posterImagePath = getIntent().getStringExtra("movie_poster_path");
        backDropImagePath = getIntent().getStringExtra("movie_backdropImage_path");


        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + getIntent().getStringExtra("movie_poster_path")).into(mMoviePosterImageView);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w780/" + getIntent().getStringExtra("movie_backdropImage_path")).into(mBackDropImageView);

        new MovieDetailsAsynTask().execute();






        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(mLinearLayoutManager);

        mLinearLayoutManagerReviews = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewsRecyclerView.setLayoutManager(mLinearLayoutManagerReviews);


        movieDatabase = MovieDatabase.getInstance(getApplicationContext());
        // starredMovie = (Movie) getIntent().getSerializableExtra("favourites");


        new MovieTrailerAsynTask().execute();
        new MovieReviewAsynTask().execute();
        movieDatabase = MovieDatabase.getInstance(getApplicationContext());


        if(exists(Long.parseLong(movieId))){
                //favouritesButton.setBackground(getDrawable(R.drawable.ic_star_selected));
            favouritesButton.setChecked(true);
        }else{
            //favouritesButton.setBackground(getDrawable(R.drawable.ic_star_not_selected));
            favouritesButton.setChecked(false);
        }

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(exists(Long.parseLong(movieId))){
                    deleteMovie(Long.parseLong(movieId));
                    favouritesButton.setChecked(false);
                    Toast.makeText(getApplicationContext(),getIntent().getStringExtra("movie_title")+" removed from Favourites",Toast.LENGTH_LONG).show();

                }else{

                    onSaveButtonClicked(movieId);
                    favouritesButton.setChecked(true);
                    Toast.makeText(getApplicationContext(),getIntent().getStringExtra("movie_title")+" added to Favourites",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public static String formatDate(String movieReleaseDate) {
        char releaseDateArray[] = movieReleaseDate.toCharArray();
        for (int i = 0; i < 4; i++) {
            year = year + releaseDateArray[i];
        }
        for (int i = 8; i <= 9; i++) {
            date = date + releaseDateArray[i];
        }
        for (int i = 5; i < 6; i++) {
            if (i == 5) {
                if (releaseDateArray[i] == '1') {
                    switch (releaseDateArray[i + 1]) {
                        case '0': {
                            month = "Oct";
                            break;
                        }
                        case '1': {
                            month = "Nov";
                            break;
                        }
                        case '2': {
                            month = "Dec";
                            break;
                        }

                    }
                }
                if (releaseDateArray[i] == '0') {
                    switch (releaseDateArray[i + 1]) {
                        case '1': {
                            month = "Jan";
                            break;
                        }
                        case '2': {
                            month = "Feb";
                            break;
                        }
                        case '3': {
                            month = "Mar";
                            break;
                        }
                        case '4': {
                            month = "Apr";
                            break;
                        }
                        case '5': {
                            month = "May";
                            break;
                        }
                        case '6': {
                            month = "June";
                            break;
                        }
                        case '7': {
                            month = "July";
                            break;
                        }
                        case '8': {
                            month = "Aug";
                            break;
                        }
                        case '9': {
                            month = "Sept";
                            break;
                        }


                    }
                }

            }
        }
        char dateArray[] = date.toCharArray();
        if (dateArray[1] == '2') {
            movieReleaseDate = date + "nd-" + month + "-" + year;
        } else if (dateArray[1] == '1') {
            movieReleaseDate = date + "st-" + month + "-" + year;
        } else {
            movieReleaseDate = date + "th-" + month + "-" + year;
        }

        date = "";
        month = "";
        year = "";
        return movieReleaseDate;
    }


    private class MovieDetailsAsynTask extends AsyncTask<String, Void, Movie> {


        @Override
        protected Movie doInBackground(String... strings) {

            Movie movie = QueryUtils.fetchMovieDataDetails(DETAIL_URL);
            return movie;
        }


        @Override
        protected void onPostExecute(final Movie movie) {
            super.onPostExecute(movie);

            if (movie == null) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            } else {
                mMovieWebsite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getmMovieWebsite()));
                        startActivity(websiteIntent);
                    }
                });
                budget = movie.getmMovieBudget();
                runtime = movie.getmMovieRunTime();
                mMovieBudget.setText(movie.getmMovieBudget().toString() + "\t$");
                mMovieRunTime.setText((movie.getmMovieRunTime().toString() + " min"));

                if (movie.getmCensor()) {
                    mCensorTextView.setText("Adult");
                } else {
                    mCensorTextView.setText("U/A");
                }


                /*for (String genres : movie.getmGenres()) {
                    mGenresTextView.setText(movie.getGenreString());
                }*/

                mGenresTextView.setText(movie.getGenreString());
            }
        }
    }


    private class MovieTrailerAsynTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            List<Movie> result = QueryUtils.fetchTrailerData(TRAILER_URL);
            return result;
            //  return null;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            //super.onPostExecute(pcmSimplexes);
            if (list != null && !list.isEmpty()) {
                movieList = list;
                trailerAdapter = new TrailerAdapter(getApplicationContext(), list);
                trailerRecyclerView.setAdapter(trailerAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "Trailers not present", Toast.LENGTH_LONG).show();
            }
        }

    }


    private class MovieReviewAsynTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            List<Movie> result = QueryUtils.fetchReviewData(REVIEW_URL);
            return result;
            //  return null;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {

            if (list != null && !list.isEmpty()) {
                movieList = list;
                mReviewCountTextView.setText("(" + movieList.size() + ")");
                reviewAdapter = new ReviewAdapter(getApplicationContext(), movieList);
                reviewsRecyclerView.setAdapter(reviewAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "Reviews not present", Toast.LENGTH_LONG).show();
            }
        }

    }




    public void onSaveButtonClicked(String id) {
        Long movieId = Long.parseLong(id);
        String movieTitle = mMovieNameTextView.getText().toString();
        String movieReleaseDate = getIntent().getStringExtra("movie_releaseDate");
        String moviePlotSynopsis = mMovieDescriptionTextView.getText().toString();

        String moviePosterPath = posterImagePath;
        String movieBackDropPath = backDropImagePath;
        Double movieVoteAverage = Double.parseDouble(getIntent().getStringExtra("movie_rating"));
        String genreString = mGenresTextView.getText().toString();
        Long movieBudget = budget;
        Double voteCount = 10.0;
        Long movieRunTime = runtime;
        String movieWebsite = mMovieWebsite.getText().toString();
        Boolean censor = checkCensor(mCensorTextView.getText().toString());
        final Movie movie = new Movie(movieId, movieTitle, movieReleaseDate, moviePlotSynopsis, moviePosterPath, movieBackDropPath, movieVoteAverage, genreString, movieBudget, voteCount, movieRunTime, movieWebsite, censor);
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDatabase.movieDao().insertMovies(movie);
            }
        });

    }

    public void deleteMovie(final Long movieId){
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDatabase.movieDao().deleteMovies(movieId);
            }
        });
    }


    public boolean exists(final Long movieId){


      if(movieDatabase.movieDao().checkIfMovieExists(movieId)){
          return true;
      }else{
          return false;
      }



    }



    public Boolean checkCensor(String censor) {
        if (censor.equals("U/A")) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkInternetConnectivity()){
            movieInformation.setVisibility(View.VISIBLE);
            new MovieTrailerAsynTask().execute();
            new MovieReviewAsynTask().execute();
        }
    }

    public boolean checkInternetConnectivity() {
        //Check internet connection//
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network//
        NetworkInfo netInformation = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, then fetch data//
        if (netInformation != null && netInformation.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


}
