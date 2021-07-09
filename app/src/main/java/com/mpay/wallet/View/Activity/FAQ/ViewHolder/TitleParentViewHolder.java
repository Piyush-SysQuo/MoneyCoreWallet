package com.mpay.wallet.View.Activity.FAQ.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.mpay.wallet.R;

public class TitleParentViewHolder extends ParentViewHolder {
    public TextView TV_Adap_FAQ_Parent_Title;
    public ImageView IV_Adap_FAQ_Parent_Title;
    public TitleParentViewHolder(View itemView) {
        super(itemView);
        TV_Adap_FAQ_Parent_Title = itemView.findViewById(R.id.TV_Adap_FAQ_Parent_Title);
        IV_Adap_FAQ_Parent_Title = itemView.findViewById(R.id.IV_Adap_FAQ_Parent_Title);
    }
}
