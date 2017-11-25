package ir.jahanmir.araxx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.ActivityChangePassword;
import ir.jahanmir.araxx.ActivityChargeOnline;
import ir.jahanmir.araxx.ActivityPayments;
import ir.jahanmir.araxx.ActivityShowClubScores;
import ir.jahanmir.araxx.ActivityShowConnections;
import ir.jahanmir.araxx.ActivityShowFactors;
import ir.jahanmir.araxx.ActivityShowFeshfeshe;
import ir.jahanmir.araxx.ActivityShowNews;
import ir.jahanmir.araxx.ActivityShowNotify;
import ir.jahanmir.araxx.ActivityShowTickets;
import ir.jahanmir.araxx.ActivityShowTrafficeSplite;
import ir.jahanmir.araxx.ActivityShowUserInfo;
import ir.jahanmir.araxx.ActivityUploadDocuments;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.classes.U;
import ir.jahanmir.araxx.classes.WebService;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.enums.EnumMainItemsTag;
import ir.jahanmir.araxx.events.EventOnDiActiveItem;
import ir.jahanmir.araxx.model.MainItems;


/**
 * Created by FCC on 10/8/2017.
 */

public class AdapterMainItems extends RecyclerView.Adapter<AdapterMainItems.AdapterMainItemsHolder> {

    List<MainItems> mainItemses;
    Context context;

    public AdapterMainItems(List<MainItems> mainItemses, Context context) {
        this.mainItemses = mainItemses;
        this.context = context;
    }

    @Override
    public AdapterMainItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_main_items, parent, false);
        return new AdapterMainItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterMainItemsHolder holder, int position) {
        final MainItems mainItem = mainItemses.get(position);
        holder.imgImage.setImageResource(mainItem.getImage());
        holder.txtName.setText(mainItem.getName());
        holder.txtName.setTextSize(12);
        holder.layCount.setVisibility(View.GONE);
        int width = U.getDeviceWidth() - 65;
        int itemWidth = width / 4;
        int tag = mainItem.getTag();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(itemWidth, itemWidth);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(itemWidth + itemWidth / 4, itemWidth + itemWidth / 4);
        FrameLayout.LayoutParams BiglayoutParams = new FrameLayout.LayoutParams(itemWidth * 2, itemWidth * 2);
        layoutParams.setMargins(6, 6, 6, 6);
        BiglayoutParams.setMargins(6, 6, 6, 6);

        switch (tag) {

            case EnumMainItemsTag.empty:
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_payment));
                holder.layMain.setLayoutParams(layoutParams);
                holder.layMain.setVisibility(View.INVISIBLE);

                break;
            case EnumMainItemsTag.chargeOnline:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_charg_online));
                holder.imgSize.setLayoutParams(imageParams);
                holder.layMain.setLayoutParams(BiglayoutParams);
                break;
            case EnumMainItemsTag.connections:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_connections));
                holder.layMain.setLayoutParams(layoutParams);


                break;
            case EnumMainItemsTag.factors:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.imgSize.setLayoutParams(imageParams);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_factor));
                holder.layMain.setLayoutParams(BiglayoutParams);


                break;
            case EnumMainItemsTag.feshfeshe:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_feshfeshe));
                holder.layMain.setLayoutParams(layoutParams);


                break;
            case EnumMainItemsTag.Messages:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_notify));
                holder.layMain.setLayoutParams(layoutParams);

                if (G.currentAccount.getCountNotify() != 0) {
                    holder.layCount.setVisibility(View.VISIBLE);
                    holder.txtCount.setText(String.valueOf(G.currentAccount.getCountNotify()));
                } else {
                    holder.layCount.setVisibility(View.GONE);
                }
                break;
            case EnumMainItemsTag.news:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_news));
                holder.layMain.setLayoutParams(layoutParams);


                break;
            case EnumMainItemsTag.payments:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_payments));
                holder.layMain.setLayoutParams(layoutParams);

                break;
            case EnumMainItemsTag.profile:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_profile));
                holder.layMain.setLayoutParams(BiglayoutParams);
                holder.imgSize.setLayoutParams(imageParams);
                holder.txtName.setTextSize(18);

                break;
            case EnumMainItemsTag.support:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_charg_online));
                holder.layMain.setLayoutParams(layoutParams);

                if (G.currentAccount.getCountTicket() != 0) {
                    holder.layCount.setVisibility(View.VISIBLE);
                    holder.txtCount.setText(String.valueOf(G.currentAccount.getCountTicket()));
                } else {
                    holder.layCount.setVisibility(View.GONE);
                }
                break;
            case EnumMainItemsTag.trafficeSplite:
                holder.txtName.setTextSize(8);
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_traffic_split));
                holder.layMain.setLayoutParams(layoutParams);

                break;
            case EnumMainItemsTag.scores:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_club));
                holder.layMain.setLayoutParams(layoutParams);

                break;
            case EnumMainItemsTag.changePassword:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_chanepass));
                holder.layMain.setLayoutParams(layoutParams);

                break;
            case EnumMainItemsTag.game:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_charg_online));
                holder.layMain.setLayoutParams(layoutParams);

                break;

            case EnumMainItemsTag.testSpeed:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_charg_online));
                holder.layMain.setLayoutParams(layoutParams);

                break;

            case EnumMainItemsTag.inviteFriends:
                holder.txtName.setTextSize(8);
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_chanepass));
                holder.layMain.setLayoutParams(layoutParams);

                break;

            case EnumMainItemsTag.upload_doc:
                holder.txtName.setTextSize(8);
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_charg_online));
                holder.layMain.setLayoutParams(layoutParams);

                break;

            case EnumMainItemsTag.exit:
                holder.layMain.setVisibility(View.VISIBLE);
                holder.layMain.setBackgroundColor(context.getResources().getColor(R.color.color_factor));
                holder.layMain.setLayoutParams(layoutParams);

                break;

        }

        holder.layMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = mainItem.getTag();
                switch (tag) {
                    case EnumMainItemsTag.chargeOnline:
                        if (G.currentLicense.Chargeonline) {
                            if (G.currentLicense != null) {
                                if (G.currentLicense.Chargeonline) {
                                    context.startActivity(new Intent(context, ActivityChargeOnline.class));
//                        finish();
                                } else {
                                    U.toast("امکان شارژ آنلاین برای شما فعال     نمیباشد.");
                                }
                            }
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }

                        break;
                    case EnumMainItemsTag.connections:
                        if (G.currentLicense.connection) {
                            context.startActivity(new Intent(context, ActivityShowConnections.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.factors:
                        if (G.currentLicense.Factor) {
                            context.startActivity(new Intent(context, ActivityShowFactors.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.feshfeshe:
                        if (G.currentLicense.Feshfeshe) {
                            context.startActivity(new Intent(context, ActivityShowFeshfeshe.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.Messages:
                        context.startActivity(new Intent(context, ActivityShowNotify.class));
                        break;
                    case EnumMainItemsTag.news:
                        context.startActivity(new Intent(context, ActivityShowNews.class));
                        break;
                    case EnumMainItemsTag.payments:
                        if (G.currentLicense.Payment) {
                            context.startActivity(new Intent(context, ActivityPayments.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.profile:
                        if (G.currentLicense.profile) {
                            context.startActivity(new Intent(context, ActivityShowUserInfo.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.support:
                        context.startActivity(new Intent(context, ActivityShowTickets.class));
                        break;
                    case EnumMainItemsTag.trafficeSplite:
                        if (G.currentLicense.trafficSplit) {
                            context.startActivity(new Intent(context, ActivityShowTrafficeSplite.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.scores:
                        if (G.currentLicense.Club) {
                            context.startActivity(new Intent(context, ActivityShowClubScores.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.changePassword:
                        if (G.currentLicense.ChangePass) {
                            context.startActivity(new Intent(context, ActivityChangePassword.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.upload_doc:
                        if (G.currentLicense.Document) {
                            context.startActivity(new Intent(context, ActivityUploadDocuments.class));
                        } else {
                            EventBus.getDefault().post(new EventOnDiActiveItem());
                        }
                        break;
                    case EnumMainItemsTag.game:
//                        context.startActivity(new Intent(context, ActivityPoints.class));
                        break;
                    case EnumMainItemsTag.exit:
                        DialogClass dialogExit = new DialogClass();
                        dialogExit.showExitDialog();
                        break;
                    case EnumMainItemsTag.inviteFriends:
                        WebService.sendGetRepresenterURL();
                        break;


                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mainItemses.size();
    }

    public class AdapterMainItemsHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        PersianTextViewNormal txtName;
        FrameLayout layMain;
        FrameLayout layCount;
        PersianTextViewThin txtCount;
        LinearLayout imgSize;

        public AdapterMainItemsHolder(View itemView) {
            super(itemView);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            txtName = (PersianTextViewNormal) itemView.findViewById(R.id.txtName);
            layMain = (FrameLayout) itemView.findViewById(R.id.layMain);
            layCount = (FrameLayout) itemView.findViewById(R.id.layCount);
            txtCount = (PersianTextViewThin) itemView.findViewById(R.id.txtCount);
            imgSize = (LinearLayout) itemView.findViewById(R.id.imgSize);
        }
    }
}

