package com.mpay.wallet.View.Fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Fragment.home.model.ItemAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemAdapter> mList;
    private Context mContext;
    public RecentTransactionAdapter(List<ItemAdapter> list, Context context){
        super();
        mList = list;
        mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_recenttransaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ItemAdapter itemAdapter = mList.get(position);
        ((ViewHolder) viewHolder).txt_username.setText(itemAdapter.getName());
        ((ViewHolder) viewHolder).txt_date.setText(itemAdapter.getDate());
        ((ViewHolder) viewHolder).txt_transactionType.setText(itemAdapter.getTransaction_type());

        if(itemAdapter.getTransaction_type().toString().equals("Withdrawal"))
        {
            ((ViewHolder) viewHolder).txt_amount.setText("-"+itemAdapter.getAmount());
            ((ViewHolder) viewHolder).txt_amount.setTextColor(mContext.getResources().getColor(R.color.transaction_minus));
        }
        else if(itemAdapter.getTransaction_type().toString().equals("Cash in"))
        {
            ((ViewHolder) viewHolder).txt_amount.setText("+"+itemAdapter.getAmount());
            ((ViewHolder) viewHolder).txt_amount.setTextColor(mContext.getResources().getColor(R.color.transaction_plus));
//            ((ViewHolder) viewHolder).txt_amount.setTextColor(Color.alpha(R.color.transaction_plus));
        }
        Glide.with(mContext).load(itemAdapter.getProfilePic()).into(((ViewHolder) viewHolder).userImg);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mainLayout;
        private CircleImageView userImg;
        private TextView txt_username, txt_date, txt_amount, txt_transactionType;
        private ImageView img_call;
        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            userImg = itemView.findViewById(R.id.userImg);
            txt_username = itemView.findViewById(R.id.txt_username);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_transactionType = itemView.findViewById(R.id.txt_transactionType);
        }
    }
}