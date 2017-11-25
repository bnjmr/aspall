package ir.jahanmir.aspa.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ir.jahanmir.aspa.G;
import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.classes.Logger;
import ir.jahanmir.aspa.model.ModelRegions;


public class AdapterSpinnerRegions extends ArrayAdapter<ModelRegions> {

    List<ModelRegions> regionsList;

    public AdapterSpinnerRegions(List<ModelRegions> cityGroups) {
        super(G.context, R.layout.s_item_white, cityGroups);
        this.regionsList = cityGroups;
        Logger.d("AdapterSpinnerCity : cities size is " + cityGroups.size());
    }

    @Override
    public int getCount() {
        return regionsList.size();
    }

    @Override
    public ModelRegions getItem(int position) {
        return regionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        txtUserSelect.setGravity(Gravity.RIGHT);

        txtUserSelect.setText(regionsList.get(position).getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(regionsList.get(position).getName());
        txtChoice.setGravity(Gravity.RIGHT);
        return view;
    }

    public void updateList(List<ModelRegions> data) {
        regionsList = data;
        notifyDataSetChanged();
    }
}
