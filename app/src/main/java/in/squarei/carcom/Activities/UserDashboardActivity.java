package in.squarei.carcom.Activities;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.squarei.carcom.Fragments.BluetoothConnectFragment;
import in.squarei.carcom.Fragments.UserDashboardFragment;
import in.squarei.carcom.R;
import in.squarei.carcom.modal.InstalledAppsData;
import in.squarei.carcom.services.BluetoothChatService;


public class UserDashboardActivity extends SocialConnectBaseActivity implements BluetoothConnectFragment.InformUserDashboardActivity {

    private static final String TAG = "UserDashboardActivity";
    RecyclerView recycler_view;
    List<InstalledAppsData> installedAppsData = new ArrayList<>();
    private TextView tvConnectStatus;
    private boolean status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        status = true;
    }

    @Override
    protected void initViews() {
        //  recycler_view=(RecyclerView) findViewById(R.id.recycler_view);
        tvConnectStatus = (TextView) findViewById(R.id.tvConnectStatus);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GetInstalledAppList();
                handler.sendEmptyMessage(0);
            }
        });
        // thread.start();   // commented for later use
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setUprecyclerView();
        }
    };

    private void setUprecyclerView() {
        //recycler_view.setLayoutManager(new LinearLayoutManager(context));
        //  recycler_view.setAdapter(new AppsListAdapter(installedAppsData,context));
    }

    @Override
    protected void initContext() {
        context = UserDashboardActivity.this;
        currentActivity = UserDashboardActivity.this;
    }

    @Override
    protected void initListners() {
        attachDashboardFragment();
    }

    private void attachDashboardFragment() {
        Log.i(TAG, "=======Fragment Attached========");
        switchFragment(new BluetoothConnectFragment(), true, false, "bluetoothConnectFragment");
       // switchFragment(new UserDashboardFragment(), false, false, "userDashboardFragment");
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    protected boolean isHomeButton() {
        return false;
    }

    @Override
    protected boolean isNavigationView() {
        return false;
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

    @Override
    protected void onPause() {
        super.onPause();
        status = false;
    }

    // method to get the list of instadded applicatios
    void GetInstalledAppList() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (Object object : pkgAppsList) {
            ResolveInfo info = (ResolveInfo) object;
            Drawable icon = getBaseContext().getPackageManager().getApplicationIcon(info.activityInfo.applicationInfo);
            String strAppName = info.activityInfo.applicationInfo.publicSourceDir.toString();
            String strPackageName = info.activityInfo.applicationInfo.packageName.toString();
            final String title = (String) ((info != null) ? getBaseContext().getPackageManager().getApplicationLabel(info.activityInfo.applicationInfo) : "???");

            if (title.equals("Chrome"))
                installedAppsData.add(new InstalledAppsData(icon, title, strPackageName));

            //    System.out.println("Apps :"+strAppName+" "+strPackageName);
        }
    }

    @Override
    public void sendMessage(int msg) {
        // toast("Message received at activity",false);
        switch (msg) {
            case BluetoothChatService.STATE_CONNECTED:
                tvConnectStatus.setText("CONNECTED");
                    switchFragment(new UserDashboardFragment(), false, false, "userDashboardFragment");
                break;
            case BluetoothChatService.STATE_CONNECTING:
                tvConnectStatus.setText("CONNECTING...");

                if (status)
                //    switchFragment(new BluetoothConnectFragment(), false, false, "bluetoothConnectFragment");
                break;
            case BluetoothChatService.STATE_LISTEN:
                tvConnectStatus.setText("LISTENING...");
                if (status)
                  //  switchFragment(new BluetoothConnectFragment(), false, false, "bluetoothConnectFragment");
                break;
            case BluetoothChatService.STATE_NONE:
                tvConnectStatus.setText("NOT CONNECTED");
                if (status)
                    switchFragment(new BluetoothConnectFragment(), false, false, "bluetoothConnectFragment");
                break;
        }
    }
}
