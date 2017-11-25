package ir.jahanmir.araxx.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.model.TicketDetail;


public class AdapterTicketDetails extends RecyclerView.Adapter<AdapterTicketDetails.TicketViewHolder> {

    final int SEND_MESSAGE = 1;
    final int RECEIVE_MESSAGE = 2;
    List<TicketDetail> tickets;
    public AdapterTicketDetails(List<TicketDetail> tickets) {
        this.tickets = tickets;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == SEND_MESSAGE)
            view  = LayoutInflater.from(G.context).inflate(R.layout.l_ticket_detail_right_item,parent,false);
        else
            view  = LayoutInflater.from(G.context).inflate(R.layout.l_ticket_detail_left_item,parent,false);

        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        final TicketDetail ticket = tickets.get(position);
        holder.txtTicketDescription.setText(Html.fromHtml(ticket.Des));
        holder.txtTicketDate.setText(ticket.Date);
        holder.txtTicketState.setText(ticket.State);
    }

    @Override
    public int getItemViewType(int position) {
        final TicketDetail ticket = tickets.get(position);
        if(ticket.User.equals("مشترک"))
            return SEND_MESSAGE;
        else
            return RECEIVE_MESSAGE;
    }
    @Override
    public int getItemCount() {
        return tickets.size();
    }
    public class TicketViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtTicketDescription)
        TextView txtTicketDescription;
        @Bind(R.id.txtTicketDate)
        TextView txtTicketDate;
        @Bind(R.id.txtTicketState)
        TextView txtTicketState;
        public TicketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void updateList(List<TicketDetail> data){
        tickets = data;
        notifyDataSetChanged();
    }
}
