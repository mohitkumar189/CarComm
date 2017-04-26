package in.squarei.carcom.modal;

import android.graphics.drawable.Drawable;

/**
 * Created by mohit kumar on 4/26/2017.
 */

public class InstalledAppsData {
    private Drawable icon;
    private String appName;
    private String AppPackage;

    public InstalledAppsData(Drawable icon, String appName, String appPackage) {
        this.icon = icon;
        this.appName = appName;
        AppPackage = appPackage;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage() {
        return AppPackage;
    }

    public void setAppPackage(String appPackage) {
        AppPackage = appPackage;
    }
}
