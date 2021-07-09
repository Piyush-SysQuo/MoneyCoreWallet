package com.mpay.wallet.View.Activity.FAQ.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.mpay.wallet.R;

public class TitleChildViewHolder extends ChildViewHolder {
    public TextView TV_Adap_FAQ_Child_MSG;
    public TitleChildViewHolder(View itemView) {
        super(itemView);
        TV_Adap_FAQ_Child_MSG = itemView.findViewById(R.id.TV_Adap_FAQ_Child_MSG);
    }
}
