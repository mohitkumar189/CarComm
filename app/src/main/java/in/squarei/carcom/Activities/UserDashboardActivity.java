package in.squarei.carcom.Activities;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import in.squarei.carcom.Adapters.AppsListAdapter;
import in.squarei.carcom.R;
import in.squarei.carcom.modal.InstalledAppsData;


public class UserDashboardActivity extends SocialConnectBaseActivity {

    RecyclerView recycler_view;
    List<InstalledAppsData> installedAppsData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }

    @Override
    protected void initViews() {
        recycler_view=(RecyclerView) findViewById(R.id.recycler_view);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                GetInstalledAppList();
                handler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setUprecyclerView();
        }
    };

    private void setUprecyclerView(){
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setAdapter(new AppsListAdapter(installedAppsData,context));
    }
    @Override
    protected void initContext() {
        context=UserDashboardActivity.this;
    }

    @Override
    protected void initListners() {

    }

    @Override
    protected boolean isActionBar() {
        return true;
    }

    @Override
    protected boolean isHomeButton() {
        return false;
    }

    @Override
    protected boolean isNavigationView() {
        return true;
    }

    @Override
    protected boolean isTabs() {
        return false;
    }

    @Override
    protected boolean isFab() {
        return false;
    }

    @Override
    protected boolean isDrawerListener() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    void GetInstalledAppList()
    {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
        for (Object object : pkgAppsList)
        {
            ResolveInfo info = (ResolveInfo) object;
            Drawable icon    = getBaseContext().getPackageManager().getApplicationIcon(info.activityInfo.applicationInfo);
            String strAppName  	= info.activityInfo.applicationInfo.publicSourceDir.toString();
            String strPackageName  = info.activityInfo.applicationInfo.packageName.toString();
            final String title 	= (String)((info != null) ? getBaseContext().getPackageManager().getApplicationLabel(info.activityInfo.applicationInfo) : "???");

            if(title.equals("Chrome")) installedAppsData.add(new InstalledAppsData(icon,title,strPackageName));

        //    System.out.println("Apps :"+strAppName+" "+strPackageName);
        }
    }
}
