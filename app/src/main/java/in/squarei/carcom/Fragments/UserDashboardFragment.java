package in.squarei.carcom.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.squarei.carcom.Adapters.ViewPagerAdapter;
import in.squarei.carcom.R;

public class UserDashboardFragment extends Fragment {

private TabLayout tabsUserDashboard;
    private ViewPager view_pager_user_dashboard;
    public UserDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_dashboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews(){
        tabsUserDashboard=(TabLayout) getActivity().findViewById(R.id.tabsUserDashboard);
        view_pager_user_dashboard=(ViewPager) getActivity().findViewById(R.id.view_pager_user_dashboard);
        setupViewPager(view_pager_user_dashboard);
        tabsUserDashboard.setupWithViewPager(view_pager_user_dashboard);


    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CarControlFragment(), "MY CAR");
        viewPagerAdapter.addFragment(new AppControlFragment(), "MY APPS");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
