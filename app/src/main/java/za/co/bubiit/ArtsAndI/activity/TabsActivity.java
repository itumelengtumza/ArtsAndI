package za.co.bubiit.ArtsAndI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.fragments.AnnoucementFragment;
import za.co.bubiit.ArtsAndI.fragments.EventsFragment;
import za.co.bubiit.ArtsAndI.fragments.NoticeFragment;
import za.co.bubiit.ArtsAndI.fragments.TourismFragment;
import za.co.bubiit.ArtsAndI.helper_util.SQLiteHandler;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class TabsActivity extends AppCompatActivity {
    private SQLiteHandler db;
    private Fragment fourFragment = new NoticeFragment();
    private Fragment oneFragment = new EventsFragment();
    private SessionManager session;
    private TabLayout tabLayout;
    private Fragment threeFragment = new AnnoucementFragment();
    private Fragment twoFragment = new TourismFragment();
    private ViewPager viewPager;

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitleList.get(position);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.tabs);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.session = new SessionManager(getApplicationContext());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                startActivity(new Intent(this, WelcomeActivity.class));
                finish();
                return true;
            case R.id.logout:
                this.db = new SQLiteHandler(getApplicationContext(), ServerConnect.user);
                this.db.deleteSQLiteData();
                logoutUser();
                return true;
            case R.id.media:
                startActivity(new Intent(this, MediaActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(this.oneFragment, "EVENTS");
        adapter.addFragment(this.twoFragment, "TOURISM");
        adapter.addFragment(this.threeFragment, "ANNOUNCEMENTS");
        adapter.addFragment(this.fourFragment, "NOTICES");
        viewPager.setAdapter(adapter);
    }

    private void logoutUser() {
        this.session.setLogin(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }
}
