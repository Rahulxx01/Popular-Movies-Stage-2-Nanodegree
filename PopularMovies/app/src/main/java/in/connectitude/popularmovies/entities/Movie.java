package in.connectitude.popularmovies.entities;

import java.util.List;

/**
 * Created by RAHUL YADAV on 5-06-2018.
 */

public class Movie {

    private String mMovieTitle;
    private String mMovieReleaseDate;
    private String mMoviePlotSynopsis;
    private Double mMovieVoteAverage;
    private String mMoviePosterPath;
    private int mMovieId;
    private List<String> mGenres = null;

    private String mReview;
    private String mReviewverName;
    private String mYoutubeUrl;
    private String mYoutubeImageUrl;

    private String castName;
    private String castImageUrl;


    public Movie(int movieId, String movieTitle, String movieReleaseDate, String moviePlotSynopsis, String moviePosterPath, Double movieVoteAverage) {
        mMovieTitle = movieTitle;
        mMovieId = movieId;
        mMovieReleaseDate = movieReleaseDate;
        mMoviePlotSynopsis = moviePlotSynopsis;
        mMoviePosterPath = moviePosterPath;
        mMovieVoteAverage = movieVoteAverage;
    }

    public Movie(int movieId, String movieTitle, String movieReleaseDate, String moviePlotSynopsis, String moviePosterPath, Double movieVoteAverage, List<String> genres) {
        mMovieTitle = movieTitle;
        mMovieId = movieId;
        mMovieReleaseDate = movieReleaseDate;
        mMoviePlotSynopsis = moviePlotSynopsis;
        mMoviePosterPath = moviePosterPath;
        mMovieVoteAverage = movieVoteAverage;
        mGenres = genres;
    }


    public List<String> getmGenres() {
        return mGenres;
    }

    public String getCastImageUrl() {
        return castImageUrl;
    }

    public String getCastName() {
        return castName;
    }

    public String getmReview() {
        return mReview;
    }

    public int getmMovieId() {
        return mMovieId;
    }

    public String getmReviewverName() {
        return mReviewverName;
    }

    public String getmYoutubeImageUrl() {
        return mYoutubeImageUrl;
    }

    public String getmYoutubeUrl() {
        return mYoutubeUrl;
    }

    public Double getmMovieVoteAverage() {
        return mMovieVoteAverage;
    }

    public String getmMoviePlotSynopsis() {
        return mMoviePlotSynopsis;
    }

    public String getmMoviePosterPath() {
        return mMoviePosterPath;
    }

    public String getmMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }
}

