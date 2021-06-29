package com.mpay.wallet.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE                        = "MoneyCore.sqlite";

    static final String B_ID                            = "_id";
    //------------------------------------------------------------------------------------------//
    //------------------------------------------------------------------------------------------//
    static final String USER_MASTER         =   "TRANSACTION_MST";

    static final String PERSON              =   "PERSON";
//    static final String RECEIVER            =   "RECEIVER";
    static final String AMOUNT              =   "AMOUNT";
    static final String INOUT               =   "INOUT";
    static final String TRANSACTION_TYPE    =   "TRANSACTION_TYPE";
    static final String TRANSACTION_STATUS  =   "TRANSACTION_STATUS";
    static final String TRANSACTION_DATE    =   "TRANSACTION_DATE";
//------------------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------------------------//

    public DBHelper(Context context)
    {
        super( context,DATABASE,null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(" CREATE TABLE " + USER_MASTER + "(" + B_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON + "  VARCHAR(250), " + AMOUNT + "  NUMERIC(18, 2)," + TRANSACTION_STATUS + " VARCHAR(100)," + INOUT + " VARCHAR(20)," + TRANSACTION_TYPE + " VARCHAR(100), "+TRANSACTION_DATE+" DATETIME )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS "  + "TRANSACTION_MST" );
    }
}
