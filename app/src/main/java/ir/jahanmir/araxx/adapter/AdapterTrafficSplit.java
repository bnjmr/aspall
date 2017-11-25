package ir.jahanmir.araxx.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.gson.TrafficSplitNotMainModel;
import ir.jahanmir.araxx.model.ModelYesNoDialog;

import static ir.jahanmir.araxx.enums.EnumYesNoKind.StartTrafficSplit;


/**
 * Created by FCC on 10/10/2017.
 */

public class AdapterTrafficSplit extends RecyclerView.Adapter<AdapterTrafficSplit.AdapterTrafficSplitHolder> {

    List<TrafficSplitNotMainModel> modelList;
    Context context;
    DialogClass dialogClass;

    public AdapterTrafficSplit(List<TrafficSplitNotMainModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        dialogClass = new DialogClass((AppCompatActivity) context);
    }

    @Override
    public AdapterTrafficSplitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_trafficsplit_item, parent, false);
        return new AdapterTrafficSplitHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterTrafficSplitHolder holder, int position) {
        final TrafficSplitNotMainModel model = modelList.get(position);
        holder.txtRegDate.setText(model.getDate());
        holder.txtTrafficSplitName.setText(model.getPackage());
        holder.layStartTrafficSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = model.getCode()+"";
                ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمئن هستید میخواهید تقسیم ترافیک را شروع کنید؟", data, StartTrafficSplit);
                dialogClass.showYesNoDialog(modelYesNoDialog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class AdapterTrafficSplitHolder extends RecyclerView.ViewHolder {
        PersianTextViewNormal txtTrafficSplitName;
        PersianTextViewNormal txtRegDate;
        LinearLayout layStartTrafficSplit;

        public AdapterTrafficSplitHolder(View itemView) {
            super(itemView);
            txtTrafficSplitName = (PersianTextViewNormal) itemView.findViewById(R.id.txtTrafficSplitName);
            txtRegDate = (PersianTextViewNormal) itemView.findViewById(R.id.txtRegDate);
            layStartTrafficSplit = (LinearLayout) itemView.findViewById(R.id.layStartTrafficSplit);
        }
    }

}
