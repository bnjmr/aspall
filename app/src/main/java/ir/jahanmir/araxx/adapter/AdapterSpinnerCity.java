package ir.jahanmir.araxx.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.model.ModelCities;


public class AdapterSpinnerCity extends ArrayAdapter<ModelCities> {

    List<ModelCities> cities;
    public AdapterSpinnerCity(List<ModelCities> cities) {
        super(G.context, R.layout.s_item_white,cities);
        this.cities = cities;
        Logger.d("AdapterSpinnerCity : cities size is " + cities.size());
    }
    @Override
    public int getCount() {
        return cities.size();
    }
    @Override
    public ModelCities getItem(int position) {
        return cities.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        if(position != 0){
            txtUserSelect.setText("شهر : " + cities.get(position).getName());
        }else
            txtUserSelect.setText(cities.get(position).getName());
        txtUserSelect.setGravity(Gravity.RIGHT);
        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(cities.get(position).getName());
        txtChoice.setGravity(Gravity.RIGHT);
        return view;
    }
    public void updateList(List<ModelCities> data){
        cities = data;
        notifyDataSetChanged();
    }
}
