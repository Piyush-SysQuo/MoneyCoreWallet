package com.mpay.wallet.View.Activity.ManageAccount.Adapter;

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

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.ManageAccount.InterFace.ManageAccountInterFace;
import com.mpay.wallet.View.Activity.ManageAccount.Model.ManageAccountItemList;

import java.util.List;

public class ManageAccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ManageAccountItemList> mList;
    private Context mContext;
    int mCheckedPosition = -1;
    private ManageAccountInterFace manageAccountInterFace;
    public ManageAccountAdapter(List<ManageAccountItemList> list, Context context, ManageAccountInterFace manageAccountInterFace){
        super();
        mList = list;
        this.manageAccountInterFace = manageAccountInterFace;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_manage_account, parent, false);
        ManageAccountAdapter.ViewHolder viewHolder = new ManageAccountAdapter.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ManageAccountItemList itemAdapter = mList.get(position);
        try {
            ((ManageAccountAdapter.ViewHolder) viewHolder).Tv_Adpt_ManagAccBankName.setText(itemAdapter.getBankname());
            ((ManageAccountAdapter.ViewHolder) viewHolder).Tv_Adpt_ManagAccBankNo.setText(itemAdapter.getBankno());


            ((ManageAccountAdapter.ViewHolder) viewHolder).Iv_Adpt_ManagAcc_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, ((ManageAccountAdapter.ViewHolder) viewHolder).Tv_Adpt_ManagAccBankName.getText().toString(), Toast.LENGTH_SHORT).show();
                    manageAccountInterFace.getBankDetails(((ManageAccountAdapter.ViewHolder) viewHolder).Tv_Adpt_ManagAccBankName.getText().toString(), ((ViewHolder) viewHolder).Tv_Adpt_ManagAccBankNo.getText().toString());
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
        private LinearLayout MainLayout_Adpt_ManageAccount;
        private TextView Tv_Adpt_ManagAccBankName, Tv_Adpt_ManagAccBankNo;
        private ImageView Iv_Adpt_ManagAcc_Delete;
        public ViewHolder(View itemView) {
            super(itemView);
            MainLayout_Adpt_ManageAccount = itemView.findViewById(R.id.MainLayout_Adpt_ManageAccount);

            Tv_Adpt_ManagAccBankNo = itemView.findViewById(R.id.Tv_Adpt_ManagAccBankNo);
            Tv_Adpt_ManagAccBankName = itemView.findViewById(R.id.Tv_Adpt_ManagAccBankName);
            Iv_Adpt_ManagAcc_Delete = itemView.findViewById(R.id.Iv_Adpt_ManagAcc_Delete);

        }
    }
}