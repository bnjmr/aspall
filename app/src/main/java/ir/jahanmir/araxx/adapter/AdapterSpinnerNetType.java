package ir.jahanmir.araxx.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.model.NetType;


/**
 * Created by FCC on 10/24/2017.
 */

public class AdapterSpinnerNetType extends ArrayAdapter<NetType> {

    List<NetType> netTypes;

    public AdapterSpinnerNetType(List<NetType> codes) {
        super(G.context, R.layout.s_item_white, codes);
        this.netTypes = codes;
    }

    @Override
    public int getCount() {
        return netTypes.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);

        view.setTag(netTypes.get(position).getValue());
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        txtUserSelect.setText(netTypes.get(position).getKey());
        txtUserSelect.setTag(netTypes.get(position).getValue());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(netTypes.get(position).getKey());
        txtChoice.setTextSize(14);

        return view;
    }

    public void updateList(List<NetType> data) {
        netTypes = data;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public NetType getItem(int position) {
        return super.getItem(position);
    }
}