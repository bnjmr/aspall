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
import ir.jahanmir.aspa.component.PersianTextViewBold;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.model.News;


public class AdapterSingleNews extends RecyclerView.Adapter<AdapterSingleNews.NewsViewHolder> {

    List<News> newses;

    public AdapterSingleNews(List<News> newses) {
        this.newses = newses;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.currentActivity).inflate(R.layout.l_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final News news = newses.get(position);
        holder.txtNewsTitle.setText("" + news.title);
        String s = "<html>" +
                "<head>" +
                "<style>body{direction:rtl;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                news.bodyText +
                "</body></html>";
        holder.webView.loadData( s, "Text/html; charset=utf-8", "UTF-8");
        holder.webView.setBackgroundColor(0x00000000);

        holder.txtNewsDate.setText("" + news.newsDate);
        if (!news.isSeen) {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
            news.isSeen = true;
            news.save();
        } else {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
        }
//        holder.txtNewsImportant.setText("" + news.Title);
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        //        @Bind(R.id.txtNewsTitle)
        PersianTextViewNormal txtNewsTitle;
        //        @Bind(R.id.txtNewsDate)
        PersianTextViewBold txtNewsDate;
        //        @Bind(R.id.newsCardView)
        CardView newsCardView;
        //        @Bind(R.id.web)
        WebView webView;


        //        @Bind(R.id.txtNewsImportant) PersianTextViewThin txtNewsImportant;
        public NewsViewHolder(final View itemView) {
            super(itemView);

//            ButterKnife.bind(G.currentActivity, itemView);

            txtNewsTitle = (PersianTextViewNormal) itemView.findViewById(R.id.txtNewsTitle);
            txtNewsDate = (PersianTextViewBold) itemView.findViewById(R.id.txtNewsDate);
            newsCardView = (CardView) itemView.findViewById(R.id.newsCardView);
            webView = (WebView) itemView.findViewById(R.id.web);


            ButterKnife.bind(this, itemView);

            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    public void updateList(List<News> data) {
        newses = data;
        notifyDataSetChanged();
    }
}
