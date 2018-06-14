package in.connectitude.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.connectitude.popularmovies.ui.PopularMoviesFragment;
import in.connectitude.popularmovies.ui.TopRatedMoviesFragment;

public class MainTabPageAdapter extends FragmentPagerAdapter {

    Context context;

    public MainTabPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularMoviesFragment();
            case 1:
                return new TopRatedMoviesFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Popular";
            case 1:
                return "Top Rated";
            default:
                return null;
        }

    }
}
