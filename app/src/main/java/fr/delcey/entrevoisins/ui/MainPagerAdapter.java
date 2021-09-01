package fr.delcey.entrevoisins.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fr.delcey.entrevoisins.R;
import fr.delcey.entrevoisins.ui.list.NeighboursFragment;

@SuppressWarnings("deprecation")
public class MainPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_neighbour_title, R.string.tab_favorites_title};

    @NonNull
    private final Context context;

    public MainPagerAdapter(@NonNull Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NeighboursFragment.newInstance(position != 0);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

}
