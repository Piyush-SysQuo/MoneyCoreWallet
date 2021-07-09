package com.mpay.wallet.View.Activity.FAQ.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.FAQ.Model.ChildTitle;
import com.mpay.wallet.View.Activity.FAQ.Model.ParentTitle;
import com.mpay.wallet.View.Activity.FAQ.ViewHolder.TitleChildViewHolder;
import com.mpay.wallet.View.Activity.FAQ.ViewHolder.TitleParentViewHolder;

import java.util.List;

public class FAQAdapter extends ExpandableRecyclerAdapter<TitleParentViewHolder, TitleChildViewHolder> {

    LayoutInflater inflater;

    public FAQAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TitleParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.adapter_layout_faq_parent, viewGroup, false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.adapter_layout_faq_child, viewGroup, false);
        return new TitleChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TitleParentViewHolder titleParentViewHolder, int i, Object o) {
        ParentTitle title = (ParentTitle)o;
        titleParentViewHolder.TV_Adap_FAQ_Parent_Title.setText(title.getTitle());
    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder titleChildViewHolder, int i, Object o) {
        ChildTitle title = (ChildTitle)o;
        titleChildViewHolder.TV_Adap_FAQ_Child_MSG.setText(title.getFaq_ans());
    }
}
