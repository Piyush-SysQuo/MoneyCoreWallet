package com.mpay.wallet.View.Fragment.Notification.adapter;

import android.content.Context;
import android.graphics.Color;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NotificationItemAdapter> mList;
    private Context mContext;
    public NotificationTransactionAdapter(List<NotificationItemAdapter> list, Context context){
        super();
        mList = list;
        mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        NotificationItemAdapter itemAdapter = mList.get(position);
        try {
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Heading.setText(itemAdapter.getHeading());
            ((ViewHolder) viewHolder).Tv_Adap_Noti_DT.setText(itemAdapter.getDate());
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Status.setText(itemAdapter.getTransaction_status());
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Amount.setText(itemAdapter.getAmount());

        /*if(itemAdapter.getTransaction_status().toString().equals("Withdraw"))
        {
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Amount.setText("-"+itemAdapter.getAmount());
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Amount.setTextColor(mContext.getResources().getColor(R.color.transaction_minus));
        }
        else if(itemAdapter.getTransaction_status().toString().equals("Cash in"))
        {
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Amount.setText("+"+itemAdapter.getAmount());
            ((ViewHolder) viewHolder).Tv_Adap_Noti_Amount.setTextColor(mContext.getResources().getColor(R.color.transaction_plus));
//            ((ViewHolder) viewHolder).Tv_Adap_Noti_Amount.setTextColor(Color.alpha(R.color.transaction_plus));
        }*/


            Glide.with(mContext).load(itemAdapter.getProfilePic()).into(((ViewHolder) viewHolder).userImg);
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
        private ImageView userImg;
        private TextView Tv_Adap_Noti_Heading, Tv_Adap_Noti_DT, Tv_Adap_Noti_Amount, Tv_Adap_Noti_Status;
        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            userImg = itemView.findViewById(R.id.imageView1);
            Tv_Adap_Noti_Heading = itemView.findViewById(R.id.Tv_Adap_Noti_Heading);
            Tv_Adap_Noti_DT = itemView.findViewById(R.id.Tv_Adap_Noti_DT);
            Tv_Adap_Noti_Amount = itemView.findViewById(R.id.Tv_Adap_Noti_Amount);
            Tv_Adap_Noti_Status = itemView.findViewById(R.id.Tv_Adap_Noti_Status);
        }
    }
}