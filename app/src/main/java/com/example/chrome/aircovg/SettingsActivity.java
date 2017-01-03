package com.example.chrome.aircovg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chrome.aircovg.helpers.SharedPreferenceHelper;

import org.w3c.dom.Text;

/**
 * Created by ayush AS on 30/12/16.
 */

public class SettingsActivity extends AppCompatActivity {


    TextView contact, github, paypal;
    SharedPreferenceHelper sharedPreferenceHelper;
    Spinner spinner;

    String[] array = new String[]{"top", "latest", "popular"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());



        contact = (TextView) findViewById(R.id.mailLink);
        github = (TextView) findViewById(R.id.githubLink);
        paypal = (TextView) findViewById(R.id.coffeeLink);
        spinner = (Spinner) findViewById(R.id.spinnerViews);

        spinner.setSelection(getIndex(sharedPreferenceHelper.getData()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferenceHelper.saveData(array[position]);
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "ayush.as.shukla@gmail.com", null));
                sIntent.putExtra(Intent.EXTRA_SUBJECT, "Airvege user");

                try{
                    startActivity(Intent.createChooser(sIntent, "Contact developer.."));
                }catch (Exception e){
                    Snackbar.make(v, "No mail client installed.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(Intent.ACTION_VIEW);
                bIntent.setData(Uri.parse("https://www.github.com/MCD-50/Airverge"));
                startActivity(bIntent);
            }
        });

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(Intent.ACTION_VIEW);
                bIntent.setData(Uri.parse("https://www.paypal.me/AyushAS"));
                startActivity(bIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private int getIndex(String sort){
        for(int i = 0; i < 3 ; i++ ){
            if(array[i].equalsIgnoreCase(sort))
                return i;
        }

        return 0;
    }
}
