package example.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import example.com.adapter.MyViewPagerAdapter;
import example.com.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tl);
        mViewPager = findViewById(R.id.vp);

        MyViewPagerAdapter mViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(mViewPagerAdapter);

//        MapFragment mapFragment;
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch(position) {
                case 0:
                    tab.setText("List");
                    break;
                case 1:
                    tab.setText("Map");
                    break;
            }
        }).attach();
    }
}