package com.example.chrome.aircovg.helpers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.chrome.aircovg.R;
import com.example.chrome.aircovg.model.Base;
import com.example.chrome.aircovg.model.News;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ayush AS on 27/12/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AirVergeDatabaseInternal";

    private static final String TABLE_STARRED_NEWS= "tableStarredNews";


    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_PUBLISHED_AT = "publishedAt";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_IMAGE_URL = "imageUrl";

    private ProgressDialog mProgressDialog;
    private Context mContext;
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAV_TRACK__TABLE = "CREATE TABLE " + TABLE_STARRED_NEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT," + KEY_AUTHOR + " TEXT,"  + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_URL + " TEXT," + KEY_PUBLISHED_AT + " TEXT,"
                + KEY_SOURCE + " TEXT," + KEY_IMAGE_URL + " TEXT," + KEY_IMAGE + " BLOB" + ")";

        db.execSQL(CREATE_FAV_TRACK__TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STARRED_NEWS);
        onCreate(db);
    }



    public void addNews(News news) {
        if(getNewsCount() > 50){
            showAlert("Couldn't add news", "You can add only 50 news to your starred list. Try removing few of them and then add.");
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            //insert only once.
            if(!isNewsExists(news.getmUrl())){
                ContentValues values = new ContentValues();
                values.put(KEY_AUTHOR, news.getmAuthor());
                values.put(KEY_TITLE, news.getmTitle());
                values.put(KEY_DESCRIPTION, news.getmDescription());
                values.put(KEY_URL, news.getmUrl());
                values.put(KEY_PUBLISHED_AT, news.getmPublishedAt());
                values.put(KEY_SOURCE, news.getmSource());
                values.put(KEY_IMAGE_URL, news.getmImageUrl());
                db.insert(TABLE_STARRED_NEWS, null, values);
                new DownloadImage().execute(new Base(news.getmId(), news.getmImageUrl(), news.getmUrl()));
            }
            db.close();
        }
    }


    private News getNews(String url){
        News news = null;
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ArrayList<News> newssss = getAllNews();
            cursor = db.query(TABLE_STARRED_NEWS, new String[] {KEY_ID, KEY_AUTHOR, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_PUBLISHED_AT
             , KEY_SOURCE, KEY_IMAGE_URL, KEY_IMAGE}, KEY_URL + "=?",
                    new String[] { String.valueOf(url.trim()) }, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                news = new News(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        ,cursor.getString(4), cursor.getString(5), cursor.getString(6));

                news.setmId(cursor.getInt(0));
                news.setmImageUrl(cursor.getString(7));
                try{
                    byte[] blob = cursor.getBlob(8);
                    news.setmLocalImage(BitmapFactory.decodeByteArray(blob, 0,blob.length));
                }catch (Exception e){

                }
            }
            if(cursor != null)
                cursor.close();
        }catch (Exception e){
              int  x= 1;
        }

        return news;
    }

    public ArrayList<News> getAllNews() {
        ArrayList<News> newsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STARRED_NEWS;

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    News news = new News(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                                          ,cursor.getString(4), cursor.getString(5), cursor.getString(6));

                    news.setmId(cursor.getInt(0));
                    news.setmImageUrl(cursor.getString(7));
                    try{
                        byte[] blob = cursor.getBlob(8);
                        news.setmLocalImage(BitmapFactory.decodeByteArray(blob, 0,blob.length));
                    }catch (Exception e){

                    }
                    newsList.add(news);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }catch (Exception e){

        }


        // return contact list
        return newsList;
    }

    // Getting single contact
    public boolean isNewsExists(String url) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STARRED_NEWS, new String[] { KEY_URL  }, KEY_URL + "=?",
                new String[] { String.valueOf(url) }, null, null, null, null);
        return cursor != null && cursor.getCount() > 0;
    }



    private boolean updateNews(Bitmap bitmap, String url) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Bitmap p = bitmap;
            ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
            p.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
            byte[] bArray = bAOS.toByteArray();
            values.put(KEY_IMAGE, bArray);
            // updating row
            db.update(TABLE_STARRED_NEWS, values, KEY_URL + " = ?",
                    new String[] { String.valueOf(url) });

            return true;
        }catch(Exception ex){
            return false;
        }

    }

    public void deleteNews(String url, boolean showResult) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            News news = getNews(url);
            db.delete(TABLE_STARRED_NEWS, KEY_URL + " = ?", new String[] { String.valueOf(url) });
            db.close();
            if(showResult)
                Toast.makeText(mContext, "News removed", Toast.LENGTH_SHORT).show();
            EventHelper.Invoke(news, false);
        }catch (Exception e){
            Toast.makeText(mContext, "Cannot remove news.", Toast.LENGTH_SHORT).show();
        }

    }

    private int getNewsCount() {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            long cnt = android.database.DatabaseUtils.queryNumEntries(db, TABLE_STARRED_NEWS);
            db.close();
            return (int) cnt;

        }catch (Exception e){
            try{
                String countQuery = "SELECT * FROM " + TABLE_STARRED_NEWS;
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(countQuery, null);
                int count = cursor.getCount();
                cursor.close();
                return count;
            }catch (Exception ex){
                return 0;
            }
        }
    }

    private class DownloadImage extends AsyncTask<Base, Void, Bitmap> {
        String art;
        @Override
        protected Bitmap doInBackground(Base... params) {
            Base base = params[0];
            String url = base.getmImageUrl();
            art = base.getmUrl();

            Bitmap bitmap = null;
            try {
                InputStream iStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(iStream);
            }catch(Exception ex){
                try{
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.drawer_back);
                }catch (Exception e){

                }
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Downloading image...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressDialog.dismiss();
            if(saveBitmap(bitmap,  art)){
                Toast.makeText(mContext, "News added.", Toast.LENGTH_SHORT).show();
                EventHelper.Invoke(getNews(art), true);
            }
        }
    }


    private boolean saveBitmap(Bitmap bitmap, String url) {
        if(bitmap != null){
            return updateNews(bitmap, url);
        }else {
            showAlert("Couldn't Complete, Try again", "Something went wrong at our end. Please try in a little bit.");
            deleteNews(url, false);
            return false;
        }
    }

    private void showAlert(String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.setNegativeButton("CANCEL", null);
        alert.show();
    }
}