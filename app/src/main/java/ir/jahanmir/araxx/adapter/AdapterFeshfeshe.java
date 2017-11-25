package ir.jahanmir.araxx.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.classes.DialogClass;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.model.Feshfeshe;
import ir.jahanmir.araxx.model.ModelYesNoDialog;

import static ir.jahanmir.araxx.enums.EnumYesNoKind.StartFeshfeshe;

public class AdapterFeshfeshe extends RecyclerView.Adapter<AdapterFeshfeshe.FeshfesheViewHolder> {

    List<Feshfeshe> feshfesheList;
    DialogClass dialogClass;
    Context context;

    public AdapterFeshfeshe(List<Feshfeshe> feshfesheList, Context context) {
        this.feshfesheList = feshfesheList;
        this.context = context;
        dialogClass = new DialogClass((AppCompatActivity) context);
    }

    @Override
    public FeshfesheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_feshfeshe_item2, parent, false);
        return new FeshfesheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeshfesheViewHolder holder, int position) {
        final Feshfeshe feshfeshe = feshfesheList.get(position);
        holder.txtFeshfesheName.setText("" + feshfeshe.packageName);


        String des = "";
        if (feshfeshe.day > 0 || feshfeshe.hour > 0) {
            des += "زمان : ";
            if (feshfeshe.day > 0) {
                des = +feshfeshe.day + "روز ";
            }
            if (feshfeshe.hour > 0) {
                des = +feshfeshe.hour + " و ساعت ";
            }
        }
        if (feshfeshe.traffic > 0) {
            des += "\n";
            des += " حجم "+feshfeshe.traffic + " مگابایت ";
        }
        holder.txtFeshfesheDes.setText(des);

        holder.layEndFeshfeshe.setVisibility(View.GONE);
        if (feshfeshe.started == 1) {
            holder.layEndFeshfeshe.setVisibility(View.GONE);

        } else {
            holder.layStartFeshfeshe.setVisibility(View.VISIBLE);
            holder.layStartFeshfeshe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمئن هستید میخواهید فشفشه را شروع کنید؟", feshfeshe.code + "", StartFeshfeshe);
                    dialogClass.showYesNoDialog(modelYesNoDialog);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return feshfesheList.size();
    }

    public class FeshfesheViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtFeshfesheName)
        TextView txtFeshfesheName;
        @Bind(R.id.layEndFeshfeshe)
        CardView layEndFeshfeshe;
        @Bind(R.id.layStartFeshfeshe)
        LinearLayout layStartFeshfeshe;
        @Bind(R.id.txtFeshfesheDes)
        PersianTextViewNormal txtFeshfesheDes;

        public FeshfesheViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Feshfeshe> data) {
        feshfesheList = data;
        notifyDataSetChanged();
    }
}
