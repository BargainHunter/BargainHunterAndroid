package com.bargainhunter.bargainhunterandroid.controllers;

/**
 * Created by veruz on 24/12/2014.
 */


import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDBController extends SQLiteOpenHelper {


        // Instances
        private static HashMap<Context, LocalDBController> mInstances;

        // Member object
        private Context mContext;

        // Database metadata
        public static final String DATABASE_NAME = "BgHunter.db";
        public static final int DATABASE_VERSION = 1;

        // Table names
        public static final String TABLE_ONE = "FavOffers";
        public static final String TABLE_TWO = "FavStores";

        // Create table querys
        private static final String QUERY_CREATE_TABLE_ONE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "`%s` INTEGER primary key autoincrement " +
                        ");",
                TABLE_ONE,
                "id");

        private static final String QUERY_CREATE_TABLE_TWO = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "`%s` INTEGER primary key autoincrement " +
                        ");",
                TABLE_TWO,
                "id");

        private LocalDBController(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

            mContext = context;
        }

        public static LocalDBController getInstance(Context context) {
            if(mInstances == null)
                mInstances = new HashMap<Context, LocalDBController>();

            if(mInstances.get(context) == null)
                mInstances.put(context, new LocalDBController(context));

            return mInstances.get(context);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(QUERY_CREATE_TABLE_ONE);
            db.execSQL(QUERY_CREATE_TABLE_TWO);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO Upgrade your database here

        }
        @Override
        public synchronized SQLiteDatabase getReadableDatabase() {
            return super.getReadableDatabase();
        }

}
