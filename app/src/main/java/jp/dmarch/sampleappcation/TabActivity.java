package jp.dmarch.sampleappcation;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * http://kojiko-android.hatenablog.com/entry/2016/02/07/004842
 * http://androhi.hatenablog.com/entry/2015/06/17/083000
 * https://qiita.com/furu8ma/items/1602a4bbed4303fec5b1
 */

public class TabActivity extends AppCompatActivity {// implements ViewPager.OnPageChangeListener, PageFragment.OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tab);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);

        final String[] pageTitle = {"HOME", "EVENT", "SETTING"};

        FragmentManager manager = getSupportFragmentManager();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(position + 1);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pageTitle[position];
            }

            @Override
            public int getCount() {
                return pageTitle.length;
            }
        };

        viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
    }

    /*@Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }*/
}
