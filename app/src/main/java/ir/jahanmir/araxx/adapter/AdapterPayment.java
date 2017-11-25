package ir.jahanmir.araxx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.classes.Logger;
import ir.jahanmir.araxx.component.PersianTextViewNormal;
import ir.jahanmir.araxx.model.Payment;


public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.PaymentViewHolder> {

    List<Payment> payments;

    public AdapterPayment(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_payment_item, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        final Payment payment = payments.get(position);
        holder.txtPaymentDate.setText(payment.date);
        String pattern = "#,###";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        long a = Long.parseLong(payment.price);
        holder.txtPaymentPrice.setText(myFormatter.format(a));
        holder.txtUnit.setText(G.currentUserInfo.unit);
        holder.txtPaymentType.setText(payment.type);
//        switch (payment.type) {
//            case EnumPaymentType.Cash:
//                holder.txtPaymentType.setText("نقدی");
//                break;
//            case EnumPaymentType.ChargeOnline:
//                holder.txtPaymentType.setText("شارژ آنلاین");
//                break;
//            case EnumPaymentType.PayOnline:
//                holder.txtPaymentType.setText("پرداخت آنلاین");
//                break;
//            default:
//                holder.txtPaymentType.setText(" - ");
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtPaymentDate)
        TextView txtPaymentDate;
        @Bind(R.id.txtPaymentPrice)
        TextView txtPaymentPrice;
        @Bind(R.id.txtPaymentType)
        TextView txtPaymentType;
        @Bind(R.id.txtUnit)
        PersianTextViewNormal txtUnit;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Payment> data) {
        payments = data;
        notifyDataSetChanged();
        Logger.d("AdapterPayment : payments size is " + payments.size());
    }
}
