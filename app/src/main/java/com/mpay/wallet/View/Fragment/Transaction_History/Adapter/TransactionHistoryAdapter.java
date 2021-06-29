package com.mpay.wallet.View.Fragment.Transaction_History.Adapter;

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
import com.mpay.wallet.View.Fragment.Notification.model.NotificationItemAdapter;
import com.mpay.wallet.View.Fragment.Transaction_History.Model.HistoryItemAdapter;

import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HistoryItemAdapter> mList;
    private Context mContext;
    public TransactionHistoryAdapter(List<HistoryItemAdapter> list, Context context){
        super();
        mList = list;
        mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_transaction_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        HistoryItemAdapter itemAdapter = mList.get(position);
        try {
            ((ViewHolder) viewHolder).txt_TransHstry_To.setText(itemAdapter.getPayto());
            ((ViewHolder) viewHolder).TextView_TransHstry_Date.setText(itemAdapter.getDate());
            ((ViewHolder) viewHolder).TextView_TransHstry_Status.setText(itemAdapter.getTransaction_status());
            ((ViewHolder) viewHolder).TextView_TransHstry_TransactionType.setText(itemAdapter.getTransaction_type());
            ((ViewHolder) viewHolder).TextView_TransHstry_Amount.setText(itemAdapter.getAmount());

        if(itemAdapter.getTransaction_type().toString().equals("Withdraw"))
        {
            ((ViewHolder) viewHolder).TextView_TransHstry_Amount.setText("-"+itemAdapter.getAmount());
            ((ViewHolder) viewHolder).TextView_TransHstry_Amount.setTextColor(mContext.getResources().getColor(R.color.transaction_minus));
        }
        else if(itemAdapter.getTransaction_type().toString().equals("Cash in"))
        {
            ((ViewHolder) viewHolder).TextView_TransHstry_Amount.setText("+"+itemAdapter.getAmount());
            ((ViewHolder) viewHolder).TextView_TransHstry_Amount.setTextColor(mContext.getResources().getColor(R.color.transaction_plus));
//            ((ViewHolder) viewHolder).TextView_TransHstry_Amount.setTextColor(Color.alpha(R.color.transaction_plus));
        }


            Glide.with(mContext).load(itemAdapter.getProfilePic()).into(((ViewHolder) viewHolder).Img_TransHstry);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mainLayout;
        private ImageView Img_TransHstry;
        private TextView txt_TransHstry_To, TextView_TransHstry_Date, TextView_TransHstry_Amount, TextView_TransHstry_Status, TextView_TransHstry_TransactionType;
        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            Img_TransHstry = itemView.findViewById(R.id.Img_TransHstry);
            txt_TransHstry_To = itemView.findViewById(R.id.txt_TransHstry_To);
            TextView_TransHstry_Date = itemView.findViewById(R.id.TextView_TransHstry_Date);
            TextView_TransHstry_Amount = itemView.findViewById(R.id.TextView_TransHstry_Amount);
            TextView_TransHstry_Status = itemView.findViewById(R.id.TextView_TransHstry_Status);
            TextView_TransHstry_TransactionType = itemView.findViewById(R.id.TextView_TransHstry_TransactionType);
        }
    }
}