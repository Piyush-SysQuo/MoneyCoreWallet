package com.mpay.wallet.View.Activity.CashIn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.CashIn.InterFace.BankInterFace;
import com.mpay.wallet.View.Activity.CashIn.Model.BankItemList;

import java.util.List;

public class AddBankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BankItemList> mList;
    private Context mContext;
    int mCheckedPosition = -1;
    private BankInterFace bankInterFace;
    public AddBankAdapter(List<BankItemList> list, Context context, BankInterFace bankInterFace){
        super();
        mList = list;
        this.bankInterFace = bankInterFace;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_bank, parent, false);
        AddBankAdapter.ViewHolder viewHolder = new AddBankAdapter.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BankItemList itemAdapter = mList.get(position);
        try {
            ((AddBankAdapter.ViewHolder) viewHolder).Tv_Adpt_AddBank.setText(itemAdapter.getBankname());
            ((AddBankAdapter.ViewHolder) viewHolder).Tv_Adpt_AddNoBank.setText(itemAdapter.getBankno());


            ((AddBankAdapter.ViewHolder) viewHolder).Rb_Adpt_AddBank.setOnCheckedChangeListener(null);
            ((AddBankAdapter.ViewHolder) viewHolder).Rb_Adpt_AddBank.setChecked(position == mCheckedPosition);
            ((AddBankAdapter.ViewHolder) viewHolder).Rb_Adpt_AddBank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mCheckedPosition = position;
                    notifyDataSetChanged();
                    bankInterFace.getBanName(((AddBankAdapter.ViewHolder) viewHolder).Tv_Adpt_AddBank.getText().toString(), ((ViewHolder) viewHolder).Tv_Adpt_AddNoBank.getText().toString());
                    Toast.makeText(mContext, ((AddBankAdapter.ViewHolder) viewHolder).Tv_Adpt_AddBank.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout MainLayout_Adpt_AddBank;
        private TextView Tv_Adpt_AddBank, Tv_Adpt_AddNoBank;
        private RadioButton Rb_Adpt_AddBank;
        public ViewHolder(View itemView) {
            super(itemView);
            MainLayout_Adpt_AddBank = itemView.findViewById(R.id.MainLayout_Adpt_AddBank);

            Tv_Adpt_AddNoBank = itemView.findViewById(R.id.Tv_Adpt_AddNoBank);
            Tv_Adpt_AddBank = itemView.findViewById(R.id.Tv_Adpt_AddBank);
            Rb_Adpt_AddBank = itemView.findViewById(R.id.Rb_Adpt_AddBank);

        }
    }
}