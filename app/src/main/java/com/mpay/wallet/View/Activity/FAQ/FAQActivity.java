package com.mpay.wallet.View.Activity.FAQ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.FAQ.Adapter.FAQAdapter;
import com.mpay.wallet.View.Activity.FAQ.Model.ChildTitle;
import com.mpay.wallet.View.Activity.FAQ.Model.ParentTitle;
import com.mpay.wallet.View.Activity.FAQ.Model.TitleCreator;
import com.mpay.wallet.View.Activity.Setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    RecyclerView Recycler_FAQ;
    FAQAdapter faqAdapter;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ((FAQAdapter)Recycler_FAQ.getAdapter()).onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Recycler_FAQ = findViewById(R.id.Recycler_FAQ);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        Recycler_FAQ.addItemDecoration(itemDecorator);

        Recycler_FAQ.setLayoutManager(new LinearLayoutManager(this));

        faqAdapter = new FAQAdapter(this, initData());
        faqAdapter.setParentClickableViewAnimationDefaultDuration();
        faqAdapter.setParentAndIconExpandOnClick(true);

        Recycler_FAQ.setAdapter(faqAdapter);
    }

    private List<ParentObject> initData() {
        TitleCreator titleCreator = TitleCreator.get(this);
        List<ParentTitle> titles = titleCreator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        for(ParentTitle title:titles)
        {
            List<Object> childList = new ArrayList<>();
            childList.add(new ChildTitle("Lorem Ipsum is simply dummy text of the printing and typesetting industry. took a galley of type and scrambled it to make a type specimen book. desktop including versions of Lorem Ipsum."));
            title.setChildObjectList(childList);
            parentObjects.add(title);
        }
        return parentObjects;
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, SettingActivity.class);
        startActivity(in);
        finish();
    }
}