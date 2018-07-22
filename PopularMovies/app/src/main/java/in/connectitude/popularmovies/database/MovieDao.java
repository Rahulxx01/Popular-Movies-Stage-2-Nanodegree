package in.connectitude.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import in.connectitude.popularmovies.entities.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie_favourites")
    LiveData<List<Movie>> loadAllMovies();

    @Insert
    void insertMovies(Movie movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovies(Movie movies);

    @Query("DELETE FROM movie_favourites WHERE mMovieId= :mMovieId")
    void deleteMovies(Long mMovieId);

    @Query("SELECT * FROM movie_favourites WHERE mMovieId = :mMovieId")
    LiveData<List<Movie>> loadMovieByID(Long mMovieId);


    @Query("SELECT * FROM movie_favourites WHERE mMovieId= :mMovieId")
    boolean checkIfMovieExists(Long mMovieId);

}
