package ir.jahanmir.aspa.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.List;

import butterknife.ButterKnife;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.model.Notify;

import static ir.jahanmir.aspa.R.id.web;


/**
 * Created by HaMiD on 3/13/2017.
 */

public class AdapterSingleNotify extends RecyclerView.Adapter<AdapterSingleNotify.NewsViewHolder> {

    List<Notify> notifies;

    public AdapterSingleNotify(List<Notify> notifies) {
        this.notifies = notifies;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.currentActivity).inflate(R.layout.l_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final Notify notify = notifies.get(position);
        holder.txtNewsTitle.setText("" + notify.Title);



        String s = "<html>" +
                "<head>" +
                "<style>body{direction:rtl;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                notify.message +
                "</body></html>";

        holder.webView.loadDataWithBaseURL("", s, "text/html", "utf-8", "");
        holder.webView.setBackgroundColor(0x00000000);
//        holder.webView.setBackgroundColor(Color.RED);

//        holder.txtNewsDate.setText("" + news.newsDate);

    }

    @Override
    public int getItemCount() {
        return notifies.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        //        @Bind(R.id.txtNewsTitle)
        PersianTextViewNormal txtNewsTitle;
        //        @Bind(R.id.txtNewsDate)
//        PersianTextViewThin txtNewsDate;
        //        @Bind(R.id.newsCardView)
        CardView newsCardView;
        //        @Bind(R.id.web)
        WebView webView;


        //        @Bind(R.id.txtNewsImportant) PersianTextViewThin txtNewsImportant;
        public NewsViewHolder(final View itemView) {
            super(itemView);

//            ButterKnife.bind(G.currentActivity, itemView);

            txtNewsTitle = (PersianTextViewNormal) itemView.findViewById(R.id.txtNewsTitle);
//            txtNewsDate = (PersianTextViewThin) itemView.findViewById(R.id.txtNewsDate);
//            newsCardView = (CardView) itemView.findViewById(R.id.newsCardView);
            webView = (WebView) itemView.findViewById(web);


            ButterKnife.bind(this, itemView);

            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    public void updateList(List<Notify> data) {
        notifies = data;
        notifyDataSetChanged();
    }
}
