package ir.jahanmir.araxx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.R;
import ir.jahanmir.araxx.component.PersianTextViewThin;
import ir.jahanmir.araxx.events.EventOnBankSelected;
import ir.jahanmir.araxx.gson.LoadBanksResponse;

public class AdapterBank extends RecyclerView.Adapter<AdapterBank.GroupViewHolder> {

    LoadBanksResponse[] banks;
    int isClub;
    int whichMenuItem;

    public AdapterBank(LoadBanksResponse[] banks) {
        this.banks = banks;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_bank_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        final LoadBanksResponse bank = banks[position];
        holder.txtBankName.setText("" + bank.name);
//        holder.layMainGroup.setCardBackgroundColor(Color.parseColor(group.color));
        holder.layMainBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnBankSelected(bank.code, bank.name));
            }
        });

        Picasso.with(G.context)
                .load(bank.logo)
                .error(R.drawable.img_bank)
                .into(holder.imgBankLogo);
    }

    @Override
    public int getItemCount() {
        return banks.length;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtBankName)
        PersianTextViewThin txtBankName;
        @Bind(R.id.imgBankLogo)
        ImageView imgBankLogo;
        @Bind(R.id.layMainBank)
        LinearLayout layMainBank;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(LoadBanksResponse[] data) {
        banks = data;
        notifyDataSetChanged();
    }
}
