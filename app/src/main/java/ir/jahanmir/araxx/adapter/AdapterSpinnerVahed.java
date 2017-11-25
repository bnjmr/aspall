package ir.jahanmir.araxx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.model.Unit;


public class AdapterSpinnerVahed extends ArrayAdapter<Unit> {

    List<Unit> units;
    public AdapterSpinnerVahed(List<Unit> units) {
        super(G.context, R.layout.s_item_white,units);
        this.units = units;
    }
    @Override
    public int getCount() {
        return units.size();
    }
    @Override
    public Unit getItem(int position) {
        return units.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        txtUserSelect.setText("واحد ارجاع : " + units.get(position).name);
        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(units.get(position).name);
        return view;
    }
    public void updateList(List<Unit> data){
        units = data;
        notifyDataSetChanged();
    }
}
