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
import ir.jahanmir.aspa.model.ModelServiceKind;

/**
 * Created by FCC on 11/6/2017.
 */

public class AdapterSpinnerServiceKind extends ArrayAdapter<ModelServiceKind> {

    List<ModelServiceKind> modelServiceKinds;

    public AdapterSpinnerServiceKind(List<ModelServiceKind> modelServiceKinds) {
        super(G.context, R.layout.s_item_white, modelServiceKinds);
        this.modelServiceKinds = modelServiceKinds;
        Logger.d("AdapterSpinnerServiceKind : ServiceKinds size is " + modelServiceKinds.size());
    }

    @Override
    public int getCount() {
        return modelServiceKinds.size();
    }

    @Override
    public ModelServiceKind getItem(int position) {
        return modelServiceKinds.get(position);
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
        txtUserSelect.setText(modelServiceKinds.get(position).getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(modelServiceKinds.get(position).getName());
        txtChoice.setGravity(Gravity.RIGHT);
        return view;
    }

    public void updateList(List<ModelServiceKind> data) {
        modelServiceKinds = data;
        notifyDataSetChanged();
    }


}
