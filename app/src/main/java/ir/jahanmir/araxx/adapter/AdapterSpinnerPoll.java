package ir.jahanmir.araxx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.classes.PollOption;


public class AdapterSpinnerPoll extends ArrayAdapter<PollOption> {

    PollOption[] options;
    public AdapterSpinnerPoll(PollOption[] options) {
        super(G.context, R.layout.s_item_white,options);
        this.options = options;
        Logger.d("AdapterSpinnerPoll : option size is " + options.length);
    }
    @Override
    public int getCount() {
        return options.length;
    }
    @Override
    public PollOption getItem(int position) {
        return options[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        txtUserSelect.setText(options[position].name);
        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(options[position].name);
        return view;
    }
    public void updateList(PollOption[] data){
        options = data;
        notifyDataSetChanged();
    }
}
