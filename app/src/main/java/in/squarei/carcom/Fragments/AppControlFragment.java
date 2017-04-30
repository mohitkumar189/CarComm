package in.squarei.carcom.Fragments;


import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.squarei.carcom.Adapters.AppsListAdapter;
import in.squarei.carcom.R;
import in.squarei.carcom.modal.InstalledAppsData;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppControlFragment extends Fragment {

    private List<InstalledAppsData> installedAppsData=new ArrayList<>();
    private RecyclerView recycler_view_apps_list;

    public AppControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_control, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getInstalledAppList();
        recycler_view_apps_list=(RecyclerView) getActivity().findViewById(R.id.recycler_view_apps_list);
        AppsListAdapter appsListAdapter=new AppsListAdapter(installedAppsData,getActivity());
        recycler_view_apps_list.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycler_view_apps_list.setAdapter(appsListAdapter);

    }

    void getInstalledAppList() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);
        for (Object object : pkgAppsList) {
            ResolveInfo info = (ResolveInfo) object;
            Drawable icon = getActivity().getPackageManager().getApplicationIcon(info.activityInfo.applicationInfo);
            String strAppName = info.activityInfo.applicationInfo.publicSourceDir.toString();
            String strPackageName = info.activityInfo.applicationInfo.packageName.toString();
            final String title = (String) ((info != null) ? getActivity().getPackageManager().getApplicationLabel(info.activityInfo.applicationInfo) : "???");
          //  installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
            switch (title){
                case "Contacts":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Dialer":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Messaging":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Gallery":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Music":
                    if(! installedAppsData.contains(new InstalledAppsData(icon, title, strPackageName)))
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Chrome":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Gmail":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
                case "Maps":
                    installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));
                    break;
            }
           //     installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));

               System.out.println("Apps :"+strAppName+" "+strPackageName);
        }
    }

}
