package in.connectitude.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import in.connectitude.popularmovies.entities.Movie;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movieList;


    public LiveData<List<Movie>> getMoviesList() {
        return movieList;
    }

    public MovieViewModel(@NonNull Application application) {

        super(application);
        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        movieList = movieDatabase.movieDao().loadAllMovies();
    }
}
