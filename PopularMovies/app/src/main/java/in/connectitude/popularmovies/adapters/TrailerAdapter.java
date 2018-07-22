package in.connectitude.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.popularmovies.R;
import in.connectitude.popularmovies.entities.Movie;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerCardViewHolder> {

    public Context mContext;

    public List<Movie> mListItems;

    public ArrayList<Movie> mTheoryListItems;


    Movie listItem;


    public TrailerAdapter(Context context, List<Movie> mListItems) {

        mContext = context;
        this.mListItems = mListItems;

    }

    @Override
    public TrailerAdapter.TrailerCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_movie_list_item, parent, false);
        return new TrailerAdapter.TrailerCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.TrailerCardViewHolder holder, int position) {
        listItem = mListItems.get(position);
        holder.trailerName.setText(listItem.getmYoutubeVideoTitle());
        Picasso.with(mContext).load("https://img.youtube.com/vi/" + listItem.getmYoutubeKey() + "/0.jpg").into(holder.trailerImageView);

        holder.trailerShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v="+listItem.getmYoutubeKey());
                v.getContext().startActivity(shareIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class TrailerCardViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.trailerImageView)
        ImageView trailerImageView;
        public @BindView(R.id.trailerName)
        TextView trailerName;
        public @BindView(R.id.trailerShare)
        ImageView trailerShare;


        public TrailerCardViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + mListItems.get(position).getmYoutubeKey()));
                    context.startActivity(intent);

                }
            });


        }
    }


}


