package ir.jahanmir.aspa.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.events.EventOnClickedIspItem;
import ir.jahanmir.aspa.gson.SearchISPResponse;


/**
 * Created by Microsoft on 3/2/2016.
 */
public class AdapterISP extends RecyclerView.Adapter<AdapterISP.ISPViewHolder> {

    List<SearchISPResponse> ispes;
    public AdapterISP(List<SearchISPResponse> ispes) {
        this.ispes = ispes;
    }

    @Override
    public ISPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ISPViewHolder(LayoutInflater.from(G.context).inflate(R.layout.l_isp_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ISPViewHolder holder, int position) {
        final SearchISPResponse isp = ispes.get(position);
        holder.txtIspName.setText("" + isp.name);
        holder.layMainIspItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedIspItem(isp.url,isp.ispId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ispes.size();
    }

    public class ISPViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtIspName)
        TextView txtIspName;
        @Bind(R.id.layMainIspItem) CardView layMainIspItem;
        public ISPViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
