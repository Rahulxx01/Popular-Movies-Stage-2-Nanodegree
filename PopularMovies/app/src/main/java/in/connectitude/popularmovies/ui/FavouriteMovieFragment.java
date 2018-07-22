package in.connectitude.popularmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.adapters.MovieAdapter;
import in.connectitude.popularmovies.database.MovieDatabase;
import in.connectitude.popularmovies.database.MovieViewModel;
import in.connectitude.popularmovies.entities.Movie;


public class FavouriteMovieFragment extends Fragment {

    @BindView(R.id.favouritesMovie_recyclerView)
    RecyclerView favouritesRecyclerView;
    private MovieDatabase movieDatabase;
    MovieAdapter movieAdapter;
    @BindView(R.id.noMoviesAdded)
    TextView noMoviesAddedTextView;

    public FavouriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite_movie, container, false);
        ButterKnife.bind(this, rootView);

        movieDatabase = MovieDatabase.getInstance((getContext()));
        favouritesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setupViewModel();
        return rootView;
    }


    private void setupViewModel() {
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMoviesList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter = new MovieAdapter(getContext(), movies);
                favouritesRecyclerView.setAdapter(movieAdapter);
                noMoviesAddedTextView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
