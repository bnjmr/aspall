package ir.jahanmir.araxx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.events.EventOnClickSlecetChargeOnlinePakage;
import ir.jahanmir.araxx.gson.ChargeOnlineGroupPackage;


public class AdapterChargeOnlineGroupPackage extends RecyclerView.Adapter<AdapterChargeOnlineGroupPackage.PackageViewHolder> {

    List<ChargeOnlineGroupPackage> packages;
    int isClub;
    long groupCode;
    public AdapterChargeOnlineGroupPackage(List<ChargeOnlineGroupPackage> packages, int isClub, long groupCode) {
        this.packages = packages;
        this.isClub = isClub;
        this.groupCode = groupCode;
    }
    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_charge_online_package_item, parent, false);
        return new PackageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PackageViewHolder holder, int position) {
        final ChargeOnlineGroupPackage pack = packages.get(position);
        String time = "";

        if (!pack.month.equals("0")) {
            time += pack.month + " ماه ";
            if (!pack.day.equals("0")) {
                time += pack.day + " روز " + "و ";
            }
        } else if (!pack.day.equals("0")) {
            time += pack.day + "روز";
        }
        if (pack.day.equals("0") && pack.month.equals("0")){
            holder.layPackageTime.setVisibility(View.GONE);
        }
        holder.txtPackageTime.setText(time);
        holder.txtPackageTraffic.setText(pack.traffic);


        holder.txtMBl.setVisibility(View.VISIBLE);

        if (pack.traffic.equals("111")){
            holder.txtMBl.setVisibility(View.GONE);
            holder.txtPackageTraffic.setText("نامحدود");

        }

        holder.txtPackageName.setText("" + pack.name);
        holder.txtPackageScore.setText(" " + pack.score);
        holder.txtPackagePrice.setText("" + G.formatterPrice.format(pack.price) + " "+G.currentUserInfo.unit);
        if(isClub == 1){
            holder.txtPackageTakhfif.setText("" + G.formatterPrice.format(pack.discount) + " "+G.currentUserInfo.unit);
            holder.txtPackagePreScore.setText("" + pack.preScore);
        }else{
            holder.layPackagePreScore.setVisibility(View.GONE);
            holder.layPackageTakhfif.setVisibility(View.GONE);
        }
        holder.layBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickSlecetChargeOnlinePakage(pack.code,groupCode));
            }
        });
    }
    @Override
    public int getItemCount() {
        return packages.size();
    }
    public class PackageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtPackageName)
        TextView txtPackageName;
        @Bind(R.id.txtPackageTraffic)
        TextView txtPackageTraffic;
        @Bind(R.id.txtPackageTime)
        TextView txtPackageTime;
        @Bind(R.id.txtPackageScore)
        TextView txtPackageScore;
        @Bind(R.id.txtPackagePrice)
        TextView txtPackagePrice;
        @Bind(R.id.txtPackagePreScore)
        TextView txtPackagePreScore;
        @Bind(R.id.txtPackageTakhfif)
        TextView txtPackageTakhfif;
        @Bind(R.id.layTakhfif)
        LinearLayout layTakhfif;
        @Bind(R.id.layPackageTakhfif)
        LinearLayout layPackageTakhfif;
        @Bind(R.id.layPackageTime)
        LinearLayout layPackageTime;
        @Bind(R.id.layPackagePreScore)
        LinearLayout layPackagePreScore;
        @Bind(R.id.layBtnBuy)
        LinearLayout layBtnBuy;

        @Bind(R.id.txtMB)
        PersianTextViewNormal txtMBl;
        public PackageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void updateList(List<ChargeOnlineGroupPackage> data) {
        packages = data;
        notifyDataSetChanged();
    }
}
