package in.connectitude.popularmovies.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by RAHUL YADAV on 5-06-2018.
 */
@Entity(tableName = "movie_favourites")
public class Movie {

    @PrimaryKey
    public Long mMovieId;
    public String mMovieTitle;
    public String mMovieReleaseDate;
    public String mMoviePlotSynopsis;
    public Double mMovieVoteAverage;
    public String mMoviePosterPath;
    public String mMovieBackDropPath;

    //public List<String> mGenres = null;
    public Boolean mCensor;


    public String mAuthor;
    public String mAuthorContent;
    public String mReviewUrl;

    public String mYoutubeKey;
    public String mYoutubeVideoTitle;

    public String castName;
    public String castImageUrl;

    public Long mMovieBudget;
    public Double mVoteCount;
    public Long mMovieRunTime;
    public String mMovieWebsite;


    public String genreString;

    public Boolean movieSaved
            ;



    @Ignore
    public Movie(String mYoutubeKey, String mYoutubeVideoTitle) {
        this.mYoutubeKey = mYoutubeKey;
        this.mYoutubeVideoTitle = mYoutubeVideoTitle;
    }

    @Ignore
    public Movie(String mAuthor, String mAuthorContent, String mReviewUrl) {
        this.mAuthor = mAuthor;
        this.mAuthorContent = mAuthorContent;
        this.mReviewUrl = mReviewUrl;
    }

    @Ignore
    public Movie(Long mMovieId, String mMovieTitle, String mMovieReleaseDate, String mMoviePlotSynopsis, String mMoviePosterPath, String mMovieBackDropPath, Double mMovieVoteAverage) {
        this.mMovieTitle = mMovieTitle;
        this.mMovieId = mMovieId;
        this.mMovieReleaseDate = mMovieReleaseDate;
        this.mMoviePlotSynopsis = mMoviePlotSynopsis;
        this.mMoviePosterPath = mMoviePosterPath;
        this.mMovieVoteAverage = mMovieVoteAverage;
        this.mMovieBackDropPath = mMovieBackDropPath;
    }

    public Movie(Long mMovieId, String mMovieTitle, String mMovieReleaseDate, String mMoviePlotSynopsis, String mMoviePosterPath, String mMovieBackDropPath, Double mMovieVoteAverage, String genreString, Long mMovieBudget, Double mVoteCount, Long mMovieRunTime, String mMovieWebsite, Boolean mCensor) {
        this.mMovieTitle = mMovieTitle;
        this.mMovieId = mMovieId;
        this.mMovieReleaseDate = mMovieReleaseDate;
        this.mMoviePlotSynopsis = mMoviePlotSynopsis;
        this.mMoviePosterPath = mMoviePosterPath;
        this.mMovieBackDropPath =mMovieBackDropPath;
        this.mMovieVoteAverage =mMovieVoteAverage;
        this.genreString = genreString;
        this.mMovieBudget = mMovieBudget;
        this.mVoteCount = mVoteCount;
        this.mMovieRunTime = mMovieRunTime;
        this.mMovieWebsite = mMovieWebsite;
        this.mCensor = mCensor;
    }

    @Ignore
    public Movie(Long mMovieBudget, Double mVoteCount, Long mMovieRunTime, String mMovieWebsite, String genreString, Boolean mCensor) {
        this.mMovieBudget = mMovieBudget;
        this.mVoteCount = mVoteCount;
        this.mMovieRunTime = mMovieRunTime;
        this.mMovieWebsite = mMovieWebsite;
        this.genreString = genreString;
        this.mCensor = mCensor;

    }


    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuthorContent() {
        return mAuthorContent;
    }

    public Long getmMovieBudget() {
        return mMovieBudget;
    }

    public Long getmMovieRunTime() {
        return mMovieRunTime;
    }

    public Double getmVoteCount() {
        return mVoteCount;
    }

    public String getmMovieWebsite() {
        return mMovieWebsite;
    }




/*    public List<String> getmGenres() {
        return mGenres;
    }*/

    public String getCastImageUrl() {
        return castImageUrl;
    }

    public String getCastName() {
        return castName;
    }


    public Long getmMovieId() {
        return mMovieId;
    }


    public String getmYoutubeKey() {
        return mYoutubeKey;
    }

    public String getmYoutubeVideoTitle() {
        return mYoutubeVideoTitle;
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

    public String getmMovieBackDropPath() {
        return mMovieBackDropPath;
    }


    public Boolean getmCensor() {
        return mCensor;
    }

    public String getmReviewUrl() {
        return mReviewUrl;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setmGenres(List<String> mGenres) {
    }

    public void setmAuthorContent(String mAuthorContent) {
        this.mAuthorContent = mAuthorContent;
    }

    public void setmCensor(Boolean mCensor) {
        this.mCensor = mCensor;
    }

    public void setmMovieBackDropPath(String mMovieBackDropPath) {
        this.mMovieBackDropPath = mMovieBackDropPath;
    }

    public void setmMovieId(Long mMovieId) {
        this.mMovieId = mMovieId;
    }

    public void setmMoviePlotSynopsis(String mMoviePlotSynopsis) {
        this.mMoviePlotSynopsis = mMoviePlotSynopsis;
    }

    public void setmMoviePosterPath(String mMoviePosterPath) {
        this.mMoviePosterPath = mMoviePosterPath;
    }

    public void setmMovieReleaseDate(String mMovieReleaseDate) {
        this.mMovieReleaseDate = mMovieReleaseDate;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public void setmMovieVoteAverage(Double mMovieVoteAverage) {
        this.mMovieVoteAverage = mMovieVoteAverage;
    }

    public void setmReviewUrl(String mReviewUrl) {
        this.mReviewUrl = mReviewUrl;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public void setCastImageUrl(String castImageUrl) {
        this.castImageUrl = castImageUrl;
    }

    public void setmYoutubeKey(String mYoutubeKey) {
        this.mYoutubeKey = mYoutubeKey;
    }

    public void setmYoutubeVideoTitle(String mYoutubeVideoTitle) {
        this.mYoutubeVideoTitle = mYoutubeVideoTitle;
    }

    public void setmMovieBudget(Long mMovieBudget) {
        this.mMovieBudget = mMovieBudget;
    }

    public void setmMovieRunTime(Long mMovieRunTime) {
        this.mMovieRunTime = mMovieRunTime;
    }

    public void setmMovieWebsite(String mMovieWebsite) {
        this.mMovieWebsite = mMovieWebsite;
    }

    public void setmVoteCount(Double mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public String getGenreString() {
        return genreString;
    }

    public Boolean getMovieSaved() {
        return movieSaved;
    }

    public void setMovieSaved(Boolean movieSaved) {
        this.movieSaved = movieSaved;
    }
}


