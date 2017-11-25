package ir.jahanmir.aspa.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.aspa.ActivityChargeOnlineGroupPackage;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.gson.ChargeOnlineCategory;


/**
 * Created by HaMiD on 3/14/2017.
 */

public class AdapterChargeOnlineCategory extends RecyclerView.Adapter<AdapterChargeOnlineCategory.GroupViewHolder> {

    List<ChargeOnlineCategory> categories;
    int isClub;
    int whichMenuItem;
    long groupCode;

    public AdapterChargeOnlineCategory(List<ChargeOnlineCategory> categories, int isClub, int whichMenuItem, long groupCode) {
        this.categories = categories;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
        this.groupCode = groupCode;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_charge_online_group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        final ChargeOnlineCategory category = categories.get(position);
        holder.txtGroupName.setText("" + category.name);
//        if (category.now)
//            holder.img_current_service.setVisibility(View.VISIBLE);
////        holder.layMainGroup.setCardBackgroundColor(Color.parseColor(group.color));
        holder.layMainGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().post(new EventOnClickedChargeOnlineGroup(group.code, isClub, whichMenuItem));
                Intent intent = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                intent.putExtra("WHICH_MENU_ITEM", whichMenuItem);
                intent.putExtra("IS_CLUB", isClub);
                intent.putExtra("GROUP_CODE", groupCode);
                intent.putExtra("CATEGORY_CODE",category.code);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                G.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtGroupName)
        PersianTextViewThin txtGroupName;
        @Bind(R.id.layMainGroup)
        CardView layMainGroup;
        @Bind(R.id.img_current_service)
        ImageView img_current_service;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ChargeOnlineCategory> data) {
        categories = data;
        notifyDataSetChanged();
    }
}
