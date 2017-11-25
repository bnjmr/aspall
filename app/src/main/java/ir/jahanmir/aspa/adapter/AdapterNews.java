package ir.jahanmir.aspa.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.aspa.ActivityShowSingleNews;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.model.News;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.NewsViewHolder> {

    List<News> newses;

    public AdapterNews(List<News> newses) {
        this.newses = newses;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_news_item0, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final News news = newses.get(position);
        holder.txtNewsTitle.setText("" + news.title);
//        holder.txtNewsBodyText.setText("" + Html.fromHtml(news.bodyText));
        holder.txtNewsDate.setText("" + news.newsDate);
        if (!news.isSeen) {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.circle_background_color));
            news.isSeen = true;
            news.save();
        } else {
//            holder.newsCardView.setCardBackgroundColor(ContextCompat.getColor(G.context,R.color.dark_grey));
        }
//        holder.txtNewsImportant.setText("" + news.Title);
        holder.newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSingleNews = new Intent(G.currentActivity, ActivityShowSingleNews.class);
                showSingleNews.putExtra("title", "" + news.title);
                showSingleNews.putExtra("body", "" + news.bodyText);
                showSingleNews.putExtra("Date", "" + news.newsDate);
                G.currentActivity.startActivity(showSingleNews);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNewsTitle)
        PersianTextViewNormal txtNewsTitle;
        @Bind(R.id.txtNewsBodyText)
        PersianTextViewNormal txtNewsBodyText;
        @Bind(R.id.txtNewsDate)
        PersianTextViewNormal txtNewsDate;
        @Bind(R.id.newsCardView)
        LinearLayout newsCardView;

        public NewsViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<News> data) {
        newses = data;
        notifyDataSetChanged();
    }
}
