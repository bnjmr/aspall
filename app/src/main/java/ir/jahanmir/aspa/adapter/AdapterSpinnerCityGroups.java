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
import ir.jahanmir.aspa.gson.CityGroupResponse;

public class AdapterSpinnerCityGroups extends ArrayAdapter<CityGroupResponse> {

    List<CityGroupResponse> cityGroups;
    public AdapterSpinnerCityGroups(List<CityGroupResponse> cityGroups) {
        super(G.context, R.layout.s_item_white,cityGroups);
        this.cityGroups = cityGroups;
        Logger.d("AdapterSpinnerCity : cities size is " + cityGroups.size());
    }
    @Override
    public int getCount() {
        return cityGroups.size();
    }
    @Override
    public CityGroupResponse getItem(int position) {
        return cityGroups.get(position);
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
        if(position != 0)
            txtUserSelect.setText("گروه : " + cityGroups.get(position).Name);
        else
            txtUserSelect.setText(cityGroups.get(position).Name);
        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(cityGroups.get(position).Name);
        txtChoice.setGravity(Gravity.RIGHT);
        return view;
    }
    public void updateList(List<CityGroupResponse> data){
        cityGroups = data;
        notifyDataSetChanged();
    }
}
