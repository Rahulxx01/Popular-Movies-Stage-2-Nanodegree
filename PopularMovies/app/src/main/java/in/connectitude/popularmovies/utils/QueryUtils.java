package in.connectitude.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import in.connectitude.popularmovies.entities.Movie;

/**
 * Created by RAHUL YADAV on 5-06-2018.
 */

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils() {

    }

    //to create url//
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in creating url", e);
            e.printStackTrace();
        }
        return url;
    }

    public static List<Movie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movies = extractMovies(jsonResponse);
        return movies;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(50000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Response Code" + responseCode);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem in retreiving", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Movie> extractMovies(String movieJson) {
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        List<Movie> movies = new ArrayList<Movie>();
        try {
            JSONObject root = new JSONObject(movieJson);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject movieData = results.getJSONObject(i);
                String movieTitle = movieData.getString("original_title");
                String movieReleaseDate = movieData.getString("release_date");
                String moviePlotSynopsis = movieData.getString("overview");
                String moviePosterPath = movieData.getString("poster_path");
                String movieBackDropPath = movieData.getString("backdrop_path");
                Double movieVoteAverage = movieData.getDouble("vote_average");
                Long movieID = movieData.getLong("id");
                movies.add(new Movie(movieID, movieTitle, movieReleaseDate, moviePlotSynopsis, moviePosterPath,movieBackDropPath, movieVoteAverage));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }


    /////////////////

    public static Movie fetchMovieDataDetails(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Movie movies = extractMovieDetails(jsonResponse);
        return movies;
    }

    private static Movie extractMovieDetails(String movieJson) {
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        String genreString = "";
        Movie movieDetails=null;
       // List<Movie> movies = new ArrayList<Movie>();
        try {
            JSONObject root = new JSONObject(movieJson);
            Long movieID = root.getLong("id");
            String movieTitle = root.getString("original_title");
            String movieReleaseDate = root.getString("release_date");
            String moviePlotSynopsis = root.getString("overview");
            String moviePosterPath = root.getString("poster_path");
            String movieBackDropPath = root.getString("backdrop_path");
            Double movieVoteAverage = root.getDouble("vote_average");
            Long movieBudget = root.getLong("budget");
            ArrayList<String> genreList = new ArrayList<String>();
             Double voteCount = root.getDouble("vote_count");
            Long movieRunTime = root.getLong("runtime");
            String movieWebsite = root.getString("homepage");
            Boolean censor = root.getBoolean("adult");
            JSONArray genres = root.getJSONArray("genres");
            for (int i = 0; i < genres.length(); i++) {
                JSONObject movieData = genres.getJSONObject(i);
                genreList.add(movieData.getString("name"));
                genreString = genreString+"("+movieData.getString("name")+") ";
            }


           // movieDetails = new Movie(movieBudget,voteCount,movieRunTime,movieWebsite,genreList,censor);
            movieDetails = new Movie(movieID,movieTitle,movieReleaseDate,moviePlotSynopsis,moviePosterPath,movieBackDropPath,movieVoteAverage,genreString,movieBudget,voteCount,movieRunTime,movieWebsite,censor);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDetails;
    }


    private static List<Movie> extractMovieTrailer(String movieJson) {
        if(TextUtils.isEmpty(movieJson)){
            return null;
        }
        List<Movie> moviesData = new ArrayList<Movie>();
        try{
            JSONObject root = new JSONObject(movieJson);
            JSONArray results = root.getJSONArray("results");
            for(int i=0;i<=results.length();i++){
                JSONObject movieData = results.getJSONObject(i);
                String key = movieData.getString("key");
                String name = movieData.getString("name");
                moviesData.add(new Movie(key,name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesData;
    }



    public static List<Movie> fetchTrailerData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movies = extractMovieTrailer(jsonResponse);
        return movies;
    }


    public static List<Movie> fetchReviewData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movies = extractMovieReviews(jsonResponse);
        return movies;
    }


    private static List<Movie> extractMovieReviews(String movieJson) {
        if(TextUtils.isEmpty(movieJson)){
            return null;
        }
        List<Movie> moviesData = new ArrayList<Movie>();
        try{
            JSONObject root = new JSONObject(movieJson);
            JSONArray results = root.getJSONArray("results");
            for(int i=0;i<=results.length();i++){
                JSONObject movieData = results.getJSONObject(i);
                String author = movieData.getString("author");
                String content = movieData.getString("content");
                String url = movieData.getString("url");
                moviesData.add(new Movie(author,content,url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesData;
    }





}
