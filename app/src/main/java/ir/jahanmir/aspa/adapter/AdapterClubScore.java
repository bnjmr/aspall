package ir.jahanmir.aspa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.model.ClubScore;


public class AdapterClubScore extends RecyclerView.Adapter<AdapterClubScore.ClubScoreViewHolder> {

    List<ClubScore> scores;

    public AdapterClubScore(List<ClubScore> scores) {
        this.scores = scores;
    }

    @Override
    public ClubScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_club_score_item, parent, false);
        return new ClubScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClubScoreViewHolder holder, int position) {
        ClubScore clubScore = scores.get(position);
        holder.txtClubScoreTitle.setText("" + clubScore.title);
        holder.txtClubScoreDate.setText("" + clubScore.date);
        holder.txtClubScore.setText(" " + clubScore.score);
        if(clubScore.des.length() == 0)
            holder.layDescription.setVisibility(View.GONE);
        else
            holder.txtClubScoreDescription.setText("" + clubScore.des);
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ClubScoreViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtClubScoreTitle)
        TextView txtClubScoreTitle;
        @Bind(R.id.txtClubScoreDate)
        TextView txtClubScoreDate;
        @Bind(R.id.txtClubScore)
        TextView txtClubScore;
        @Bind(R.id.txtClubScoreDescription)
        TextView txtClubScoreDescription;
        @Bind(R.id.layDescription)
        LinearLayout layDescription;
        public ClubScoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void updateList(List<ClubScore> data) {
        scores = data;
        notifyDataSetChanged();
    }
}
