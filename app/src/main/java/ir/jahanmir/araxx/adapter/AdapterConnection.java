package ir.jahanmir.araxx.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.model.Connection;

public class AdapterConnection extends RecyclerView.Adapter<AdapterConnection.FactorViewHolder> {

    List<Connection> connections;

    public AdapterConnection(List<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public FactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_connection_item, parent, false);
        return new FactorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactorViewHolder holder, int position) {
        final Connection connection = connections.get(position);
        holder.card_view.setBackgroundColor(Color.TRANSPARENT);

        try {
            if (connection.Reason != null && connection.Reason.equals("Sum")) {
                holder.layDuration.setVisibility(View.GONE);
                holder.layEndDate.setVisibility(View.GONE);
                holder.layStartDate.setVisibility(View.GONE);

                holder.card_view.setBackgroundColor(G.context.getResources().getColor(R.color.color_back));

                holder.txtConnectionStartDate.setText("" + connection.startTime);
                holder.txtConnectionExpireDate.setText("" + connection.endTime);
                holder.txtConnectionDuration.setText("" + connection.Duration);
                holder.txtConnectionSend.setText("" + connection.Send + " مگابایت");
                holder.txtConnectionReceive.setText("" + connection.Recieve + " مگابایت");
                holder.txtConnectionTrafficUsed.setText("" + connection.TrafficAll + " مگابایت");
            } else {
                holder.layDuration.setVisibility(View.VISIBLE);
                holder.layEndDate.setVisibility(View.VISIBLE);
                holder.layStartDate.setVisibility(View.VISIBLE);

                holder.card_view.setBackgroundColor(G.context.getResources().getColor(R.color.color_factor));

                holder.txtConnectionStartDate.setText("" + connection.startTime);
                holder.txtConnectionExpireDate.setText("" + connection.endTime);
                holder.txtConnectionDuration.setText("" + connection.Duration);
                holder.txtConnectionSend.setText("" + connection.Send + " مگابایت");
                holder.txtConnectionReceive.setText("" + connection.Recieve + " مگابایت");
                holder.txtConnectionTrafficUsed.setText("" + connection.TrafficUsed + " مگابایت");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    public class FactorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtConnectionStartDate)
        TextView txtConnectionStartDate;
        @Bind(R.id.txtConnectionExpireDate)
        TextView txtConnectionExpireDate;
        @Bind(R.id.txtConnectionDuration)
        TextView txtConnectionDuration;
        @Bind(R.id.txtConnectionSend)
        TextView txtConnectionSend;
        @Bind(R.id.txtConnectionReceive)
        TextView txtConnectionReceive;
        @Bind(R.id.txtConnectionTrafficUsed)
        TextView txtConnectionTrafficUsed;
        @Bind(R.id.layStartDate)
        LinearLayout layStartDate;
        @Bind(R.id.layEndDate)
        LinearLayout layEndDate;
        @Bind(R.id.layDuration)
        LinearLayout layDuration;
        @Bind(R.id.card_view)
        LinearLayout card_view;

        public FactorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Connection> data) {
        connections = data;
        notifyDataSetChanged();
    }
}
