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
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.events.EventOnClickedTicketItem;
import ir.jahanmir.araxx.model.Ticket;


public class AdapterTicket extends RecyclerView.Adapter<AdapterTicket.TicketViewHolder> {

    List<Ticket> tickets;
    public AdapterTicket(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_ticket_item,parent,false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        final Ticket ticket = tickets.get(position);
        holder.txtCode.setText(ticket.code+"");
        holder.txtTicketTitle.setText("" + ticket.title);
        holder.txtTicketDate.setText("" + ticket.date);
        holder.txtTicketStatus.setText("" + ticket.state);
        holder.txtTicketPriority.setText("" + ticket.PriorityName);
        holder.layMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedTicketItem(ticket.open,ticket.code));

                try {
                    G.currentAccount.setCountTicket(G.currentAccount.getCountTicket() - ticket.CountUnread);
                    G.currentAccount.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        if (ticket.CountUnread != 0) {
            holder.layCount.setVisibility(View.VISIBLE);
            holder.txtCount.setText(String.valueOf(ticket.CountUnread));
        } else {
            holder.layCount.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return tickets.size();
    }
    public class TicketViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtTicketTitle)
        TextView txtTicketTitle;
        @Bind(R.id.txtTicketDate)
        TextView txtTicketDate;
        @Bind(R.id.txtTicketStatus)
        TextView txtTicketStatus;
        @Bind(R.id.txtTicketPriority)
        TextView txtTicketPriority;
        @Bind(R.id.txtCode)
        TextView txtCode;
//        @Bind(R.id.cardTicketMain) CardView cardTicketMain;
        @Bind(R.id.layMoreDetail)
LinearLayout layMoreDetail;

        @Bind(R.id.layCount)
        LinearLayout layCount;
        @Bind(R.id.txtCount)
        PersianTextViewThin txtCount;
        public TicketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void updateList(List<Ticket> data){
        tickets = data;
        notifyDataSetChanged();
    }
}
