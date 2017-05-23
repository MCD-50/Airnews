package com.air.aircovg.helpers;

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

import com.air.aircovg.R;
import com.air.aircovg.model.Base;
import com.air.aircovg.model.News;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ayush AS on 27/12/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private ProgressDialog mProgressDialog;
    private Context mContext;
    public DatabaseHelper(Context context){
        super(context, AppConstants.DATABASE_NAME, null, AppConstants.DATABASE_VERSION);
        mContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAV_TRACK__TABLE = "CREATE TABLE " + AppConstants.TABLE_STARRED_NEWS + "("
                + AppConstants.KEY_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT," + AppConstants.KEY_TITLE + " TEXT,"
                + AppConstants.KEY_DESCRIPTION + " TEXT," + AppConstants.KEY_URL + " TEXT," + AppConstants.KEY_PUBLISHED_AT + " TEXT,"
                + AppConstants.KEY_IMAGE_URL + " TEXT," + AppConstants.KEY_IMAGE + " BLOB" + ")";

        db.execSQL(CREATE_FAV_TRACK__TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_STARRED_NEWS);
        onCreate(db);
    }



    public void addNews(News news) {
        if(getNewsCount() > 50){
            CollectionHelper.showAlert(mContext, "Couldn't add news", "You can add only 50 news to your starred list. Try removing few of them and then add.");
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            //insert only once.
            if(!isNewsExists(news.getmUrl())){
                ContentValues values = new ContentValues();
                values.put(AppConstants.KEY_TITLE, news.getmTitle());
                values.put(AppConstants.KEY_DESCRIPTION, news.getmDescription());
                values.put(AppConstants.KEY_URL, news.getmUrl());
                values.put(AppConstants.KEY_PUBLISHED_AT, news.getmPublishedAt());
                values.put(AppConstants.KEY_IMAGE_URL, news.getmImageUrl());
                db.insert(AppConstants.TABLE_STARRED_NEWS, null, values);
                new DownloadImage().execute(new Base(news.getmUrl(), news.getmImageUrl()));
            }
            db.close();
        }
    }


    private News getNews(String url){
        News news = null;
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<News> x = getAllNews();
            cursor = db.query(AppConstants.TABLE_STARRED_NEWS,
                    new String[] {AppConstants.KEY_ID, AppConstants.KEY_TITLE, AppConstants.KEY_DESCRIPTION, AppConstants.KEY_URL,
                            AppConstants.KEY_PUBLISHED_AT, AppConstants.KEY_IMAGE_URL, AppConstants.KEY_IMAGE}, AppConstants.KEY_URL + "=?",
                    new String[] { String.valueOf(url.trim()) }, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                news = new News(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                news.setmImageUrl(cursor.getString(5));
                try{
                    byte[] blob = cursor.getBlob(6);
                    news.setmLocalImage(BitmapFactory.decodeByteArray(blob, 0,blob.length));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(cursor != null)
                cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return news;
    }

    public ArrayList<News> getAllNews() {
        ArrayList<News> newsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + AppConstants.TABLE_STARRED_NEWS;

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    News news = new News(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                    news.setmImageUrl(cursor.getString(5));
                    try{
                        byte[] blob = cursor.getBlob(6);
                        news.setmLocalImage(BitmapFactory.decodeByteArray(blob, 0,blob.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    newsList.add(news);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return newsList;
    }

    // Getting single contact
    public boolean isNewsExists(String url) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AppConstants.TABLE_STARRED_NEWS, new String[] { AppConstants.KEY_URL  }, AppConstants.KEY_URL + "=?",
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
            values.put(AppConstants.KEY_IMAGE, bArray);
            db.update(AppConstants.TABLE_STARRED_NEWS, values, AppConstants.KEY_URL + " = ?", new String[] { String.valueOf(url) });
            return true;
        }catch(Exception ex){
            return false;
        }

    }

    public void deleteNews(String url, boolean showResult) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            News news = getNews(url);
            db.delete(AppConstants.TABLE_STARRED_NEWS, AppConstants.KEY_URL + " = ?", new String[] { String.valueOf(url) });
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
            long cnt = android.database.DatabaseUtils.queryNumEntries(db, AppConstants.TABLE_STARRED_NEWS);
            db.close();
            return (int) cnt;
        }catch (Exception e){
            try{
                String countQuery = "SELECT * FROM " + AppConstants.TABLE_STARRED_NEWS;
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
        String article;
        @Override
        protected Bitmap doInBackground(Base... params) {
            Base base = params[0];
            article = base.getmUrl();

            Bitmap bitmap = null;
            try {
                InputStream iStream = new URL(base.getmImageUrl()).openStream();
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
            if(saveBitmap(bitmap,  article)){
                Toast.makeText(mContext, "News added.", Toast.LENGTH_SHORT).show();
                EventHelper.Invoke(getNews(article), true);
            }
        }
    }


    private boolean saveBitmap(Bitmap bitmap, String url) {
        if(bitmap != null){
            return updateNews(bitmap, url);
        }else {
            CollectionHelper.showAlert(mContext, "Couldn't Complete, Try again", "Something went wrong at our end. Please try in a little bit.");
            deleteNews(url, false);
            return false;
        }
    }

}