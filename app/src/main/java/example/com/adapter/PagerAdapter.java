package example.com.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import example.com.fragment.ListFragment;
import example.com.fragment.MapFragment;

public class PagerAdapter extends FragmentStateAdapter {

    private static final int ITEM_COUNT = 2;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1)
            return new MapFragment();

        return new ListFragment();
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }
}
