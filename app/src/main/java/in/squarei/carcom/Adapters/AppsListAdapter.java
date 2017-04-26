package in.squarei.carcom.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.squarei.carcom.R;
import in.squarei.carcom.modal.InstalledAppsData;

/**
 * Created by mohit kumar on 4/26/2017.
 */

public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.MyViewHolder> {
    private List<InstalledAppsData> installedAppsDataList;
    private Context context;

    public AppsListAdapter(List<InstalledAppsData> installedAppsDataList, Context context) {
        this.installedAppsDataList = installedAppsDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.top_view_apps_list, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvAppName.setText(installedAppsDataList.get(position).getAppName());
        holder.tvAppPackage.setText(installedAppsDataList.get(position).getAppPackage());
        holder.ivAppIcon.setImageDrawable(installedAppsDataList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return installedAppsDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppName, tvAppPackage;
        ImageView ivAppIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvAppName = (TextView) itemView.findViewById(R.id.tvAppName);
            tvAppPackage = (TextView) itemView.findViewById(R.id.tvAppPackage);
            ivAppIcon = (ImageView) itemView.findViewById(R.id.ivAppIcon);

        }
    }
}
