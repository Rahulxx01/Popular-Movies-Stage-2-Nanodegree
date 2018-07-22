package in.connectitude.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.entities.Movie;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewCardViewHolder> {

    public Context mContext;

    public List<Movie> mListItems;

    public ArrayList<Movie> mTheoryListItems;


    Movie listItem;


    public ReviewAdapter(Context context, List<Movie> mListItems) {

        mContext = context;
        this.mListItems = mListItems;

    }

    @Override
    public ReviewAdapter.ReviewCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_movie_list_item, parent, false);
        return new ReviewAdapter.ReviewCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ReviewCardViewHolder holder, int position) {
        listItem = mListItems.get(position);

        holder.authorTextView.setText(listItem.getmAuthor());
        holder.contentTextView.setText(listItem.getmAuthorContent());

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class ReviewCardViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.authorReview_textView)
        TextView authorTextView;
        public @BindView(R.id.contentReview_TextView)
        TextView contentTextView;


        public ReviewCardViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}



