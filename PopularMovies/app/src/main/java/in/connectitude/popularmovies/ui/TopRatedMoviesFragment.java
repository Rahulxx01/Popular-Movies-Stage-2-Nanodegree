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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.adapters.MovieAdapter;
import in.connectitude.popularmovies.entities.Constants;
import in.connectitude.popularmovies.entities.Movie;
import in.connectitude.popularmovies.utils.QueryUtils;


public class TopRatedMoviesFragment extends Fragment {

    @BindView(R.id.topRatedMovies_ProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.topRatedMovies_recyclerView)
    RecyclerView mMovieRecyclerView;
    @BindView(R.id.swipe_refresh_layout_topRatedMovies)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.reload_TopRated)
    ImageView reloadPopularMovies;
    @BindView(R.id.noInternetConnectionTopRated)
    TextView noInternetConnectionTextView;

    public List<Movie> movieList;
    MovieAdapter movieAdapter;


    public TopRatedMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_rated_movies, container, false);
        ButterKnife.bind(this, rootView);
        //mMovieRecyclerView = rootView.findViewById(R.id.topRatedMovies_recyclerView);
        //mProgressBar = (ProgressBar) rootView.findViewById(R.id.topRatedMovies_ProgressBar);


        if (checkInternetConnectivity()) {
            reloadPopularMovies.setVisibility(View.GONE);
            noInternetConnectionTextView.setVisibility(View.GONE);
            new MovieAsynTask().execute();

        } else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        reloadPopularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnectivity()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    new MovieAsynTask().execute();
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_topRatedMovies);
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
            List<Movie> result = QueryUtils.fetchMovieData(Constants.MOVIES_TOP_RATED);
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
                mSwipeRefresh.setVisibility(View.GONE);
                reloadPopularMovies.setVisibility(View.GONE);
                noInternetConnectionTextView.setVisibility(View.GONE);

            } else {
                //Toast.makeText(getContext(), "Something Went Wrong in the Server", Toast.LENGTH_LONG).show();
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
