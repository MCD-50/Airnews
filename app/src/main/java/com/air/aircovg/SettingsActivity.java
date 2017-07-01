package com.air.aircovg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.air.aircovg.adapters.SpinnerAdapter;
import com.air.aircovg.helpers.AppConstants;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.klinker.android.sliding.SlidingActivity;

import java.util.ArrayList;

/**
 * Created by ayush AS on 30/12/16.
 */

public class SettingsActivity extends SlidingActivity {

    TextView mContact, mGithub, mPaypal;

    @Override
    public void init(Bundle savedInstanceState) {
        setTitle("Settings");

        setPrimaryColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        setContent(R.layout.settings_layout);


        mContact = (TextView) findViewById(R.id.mailLink);
        mGithub = (TextView) findViewById(R.id.githubLink);
        mPaypal = (TextView) findViewById(R.id.coffeeLink);



        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "ayush.as.shukla@gmail.com", null));
                sIntent.putExtra(Intent.EXTRA_SUBJECT, "Airnews user");
                try{
                    startActivity(Intent.createChooser(sIntent, "Contact developer.."));
                }catch (Exception e){
                    Snackbar.make(v, "No mail client installed.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(Intent.ACTION_VIEW);
                bIntent.setData(Uri.parse("https://www.github.com/MCD-50/Airnews"));
                startActivity(bIntent);
            }
        });

        mPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(Intent.ACTION_VIEW);
                bIntent.setData(Uri.parse("https://www.paypal.me/AyushAS"));
                startActivity(bIntent);
            }
        });
    }
}
