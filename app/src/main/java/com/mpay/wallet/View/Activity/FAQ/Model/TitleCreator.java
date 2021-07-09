package com.mpay.wallet.View.Activity.FAQ.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class TitleCreator {
    static TitleCreator _titleCreator;
    List<ParentTitle> _parentTitles;

    public TitleCreator(Context context) {
        _parentTitles =  new ArrayList<>();
        for(int i=1; i <=7;i++){
            if( i == 1) {
                ParentTitle title = new ParentTitle("How can I transfer Money?");
                _parentTitles.add(title);
            }
            else if( i == 2) {
                ParentTitle title = new ParentTitle("How can I check my wallet balance?");
                _parentTitles.add(title);
            }
            else if( i == 3) {
                ParentTitle title = new ParentTitle("Are digital wallets safe?");
                _parentTitles.add(title);
            }
            else if( i == 4) {
                ParentTitle title = new ParentTitle("Where can I use my digital wallet?");
                _parentTitles.add(title);
            }
            else if( i == 5) {
                ParentTitle title = new ParentTitle("What should I do if I need help? ");
                _parentTitles.add(title);
            }
            else if( i == 6) {
                ParentTitle title = new ParentTitle("How do I top up my wallet?");
                _parentTitles.add(title);
            }
        }
    }

    public static TitleCreator get(Context context)
    {
        if(_titleCreator == null){
            _titleCreator = new TitleCreator(context);
        }
        return _titleCreator;
    }

    public List<ParentTitle> getAll() {
        return _parentTitles;
    }
}
