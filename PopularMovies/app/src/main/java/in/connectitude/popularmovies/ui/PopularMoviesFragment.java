package in.connectitude.popularmovies.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.adapters.MovieAdapter;
import in.connectitude.popularmovies.entities.Constants;
import in.connectitude.popularmovies.entities.Movie;
import in.connectitude.popularmovies.utils.QueryUtils;


public class PopularMoviesFragment extends Fragment {
    public List<Movie> movieList;
    MovieAdapter movieAdapter;
    public SwipeRefreshLayout mSwipeRefresh;
    RecyclerView mMovieRecyclerView;
    ProgressBar mProgressBar;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        mMovieRecyclerView = rootView.findViewById(R.id.popularMovies_recyclerView);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.popularMovies_ProgressBar);


        if (checkInternetConnectivity()) {

            new MovieAsynTask().execute();

        } else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_popularMovies);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               /* LoaderManager loaderManager = getActivity().getLoaderManager();
                loaderManager.restartLoader(PCM_ID, null,PcmFragment.this).forceLoad();*/
                new MovieAsynTask().execute();

            }
        });

        return rootView;
    }


    private class MovieAsynTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            List<Movie> result = QueryUtils.fetchMovieData(Constants.MOVIES_POPULAR);
            return result;
            //  return null;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            //super.onPostExecute(pcmSimplexes);
            if (list != null && !list.isEmpty()) {
                movieList = list;

                movieAdapter = new MovieAdapter(getContext(), list);

                mMovieRecyclerView.setAdapter(movieAdapter);
                mProgressBar.setVisibility(View.GONE);


            } else {
                Toast.makeText(getContext(), "Something Went Wrong in the Server", Toast.LENGTH_LONG).show();
            }
        }

    }


    public boolean checkInternetConnectivity() {
        //Check internet connection//
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
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
