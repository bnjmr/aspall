package ir.jahanmir.aspa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.model.TicketCodes;


/**
 * Created by HaMiD on 3/11/2017.
 */

public class AdapterSpinnerCodes extends ArrayAdapter<TicketCodes> {

    List<TicketCodes> codes;

    public AdapterSpinnerCodes(List<TicketCodes> codes) {
        super(G.context, R.layout.s_item_white, codes);
        this.codes = codes;
    }

    @Override
    public int getCount() {
        return codes.size();
    }

    @Override
    public TicketCodes getItem(int position) {
        return codes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        view.setTag(codes.get(position).getParent());
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        txtUserSelect.setText(codes.get(position).getName());
        txtUserSelect.setTag(codes.get(position).getParent());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(codes.get(position).getName());
        return view;
    }

    public void updateList(List<TicketCodes> data) {
        codes = data;
        notifyDataSetChanged();
    }
}