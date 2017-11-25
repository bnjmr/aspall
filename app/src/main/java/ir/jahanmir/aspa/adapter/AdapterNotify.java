package ir.jahanmir.aspa.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.activeandroid.query.Select;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.aspa.ActivityShowSingleNotify;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.component.PersianTextViewThin;
import ir.jahanmir.aspa.model.Notify;


public class AdapterNotify extends RecyclerView.Adapter<AdapterNotify.NewsViewHolder> {

    List<Notify> notifies;
    DialogClass dialogClass = new DialogClass();

    public AdapterNotify(List<Notify> notifies) {
        this.notifies = notifies;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_notify_item0, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final Notify notify = notifies.get(position);
        holder.notifyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSingleNews = new Intent(G.currentActivity, ActivityShowSingleNotify.class);
                showSingleNews.putExtra("title", "" + notify.Title);
                showSingleNews.putExtra("body", "" + notify.message);
                showSingleNews.putExtra("date", "" + notify.getDate());

                G.currentActivity.startActivity(showSingleNews);
            }
        });
        holder.txtDate.setText(notify.getDate());

//        if (!notify.isSeen) {
//            holder.notifyCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
//            notify.isSeen = true;
//            notify.save();
//        } else {
//            holder.notifyCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
//        }
        holder.txtNotifyTitle.setText("" + notify.Title);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClass.deleteNotify(notify.notifyCode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifies.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNotifyTitle)
        PersianTextViewNormal txtNotifyTitle;
        @Bind(R.id.notifyCardView)
        LinearLayout notifyCardView;
        @Bind(R.id.btn_delete)
        ImageView btn_delete;
        @Bind(R.id.txtDate)
        PersianTextViewThin txtDate;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Notify> data) {
        notifies = data;
        notifyDataSetChanged();
    }

    public void getNotifyFromDB() {
        notifies = new Select()
                .from(Notify.class)
                .where("IsSeen = 0")
                .orderBy("NotifyCode desc")
                .execute();
        updateList(notifies);
    }
}
