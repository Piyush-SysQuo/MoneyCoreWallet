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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.CashIn.Model.CardItemList;
import com.mpay.wallet.View.Fragment.Transaction_History.Model.HistoryItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CardItemList> mList;
    private Context mContext;
    int mCheckedPosition = -1;
    public AddCardAdapter(List<CardItemList> list, Context context){
        super();
        mList = list;

        mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CardItemList itemAdapter = mList.get(position);
        try {
            ((ViewHolder) viewHolder).Tv_Adpt_AddCard.setText(itemAdapter.getCardno());

            if(position % 2 == 0)
            {
                //holder.rootView.setBackgroundColor(Color.BLACK);
                Glide.with(mContext).load(mContext.getResources().getDrawable(R.drawable.visa)).into(((ViewHolder) viewHolder).Iv_Adpt_AddCard);
            }
            else
            {
                //holder.rootView.setBackgroundColor(Color.WHITE);
                Glide.with(mContext).load(mContext.getResources().getDrawable(R.drawable.master)).into(((ViewHolder) viewHolder).Iv_Adpt_AddCard);
            }

            ((ViewHolder) viewHolder).Rb_Adpt_AddCard.setOnCheckedChangeListener(null);
            ((ViewHolder) viewHolder).Rb_Adpt_AddCard.setChecked(position == mCheckedPosition);
            ((ViewHolder) viewHolder).Rb_Adpt_AddCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mCheckedPosition = position;
                    notifyDataSetChanged();
                    Toast.makeText(mContext, ((ViewHolder) viewHolder).Tv_Adpt_AddCard.getText().toString(), Toast.LENGTH_SHORT).show();
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
        private LinearLayout MainLayout_Adpt_AddCard;
        private ImageView Iv_Adpt_AddCard;
        private TextView Tv_Adpt_AddCard;
        private RadioButton Rb_Adpt_AddCard;
        public ViewHolder(View itemView) {
            super(itemView);
            MainLayout_Adpt_AddCard = itemView.findViewById(R.id.MainLayout_Adpt_AddCard);

            Iv_Adpt_AddCard = itemView.findViewById(R.id.Iv_Adpt_AddCard);
            Tv_Adpt_AddCard = itemView.findViewById(R.id.Tv_Adpt_AddCard);
            Rb_Adpt_AddCard = itemView.findViewById(R.id.Rb_Adpt_AddCard);

        }
    }
}