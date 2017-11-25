package ir.jahanmir.aspa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import ir.jahanmir.aspa.R;
import ir.jahanmir.aspa.component.PersianTextViewNormal;
import ir.jahanmir.aspa.model.ModelPollOption;


/**
 * Created by FCC on 11/12/2017.
 */

public class AdapterPollOptions extends RecyclerView.Adapter<AdapterPollOptions.AdapterPollOptionsHolder> {

    List<ModelPollOption> list;
    String OId = "0";

    public AdapterPollOptions(List<ModelPollOption> list) {
        this.list = list;
    }

    @Override
    public AdapterPollOptionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_poll_options, parent, false);
        return new AdapterPollOptionsHolder(view);

    }

    @Override
    public void onBindViewHolder(AdapterPollOptionsHolder holder, final int position) {
        final ModelPollOption option = list.get(position);
        holder.txtText.setText(option.getOptionText());
        holder.chbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setSelected(false);
                    }
                    list.get(position).setSelected(true);
                    notifyDataSetChanged();
                    OId = option.getOptionID();
                }
            }
        });

        if (option.isSelected()) {
            holder.chbSelect.setChecked(true);
        } else {
            holder.chbSelect.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getSelectedOptionId() {
        return OId;
    }

    public class AdapterPollOptionsHolder extends RecyclerView.ViewHolder {
        PersianTextViewNormal txtText,txtDone;
        CheckBox chbSelect;

        public AdapterPollOptionsHolder(View itemView) {
            super(itemView);
            chbSelect = (CheckBox) itemView.findViewById(R.id.chbSelect);
            txtText = (PersianTextViewNormal) itemView.findViewById(R.id.txtText);


        }
    }

}
