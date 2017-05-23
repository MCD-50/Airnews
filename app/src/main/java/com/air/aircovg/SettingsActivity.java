package com.air.aircovg;

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

import com.air.aircovg.adapters.SpinnerAdapter;
import com.air.aircovg.helpers.AppConstants;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.air.aircovg.helpers.StringHelpers;
import com.air.aircovg.model.Country;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ayush AS on 30/12/16.
 */

public class SettingsActivity extends AppCompatActivity {

    SharedPreferenceHelper sharedPreferenceHelper;
    SpinnerAdapter spinnerAdapter;

    TextView mContact, mGithub, mPaypal;
    Spinner mSpinner;

    ArrayList<String> countryName = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());
        spinnerAdapter = new SpinnerAdapter(this, AppConstants.COUNTRY_LIST);

        mContact = (TextView) findViewById(R.id.mailLink);
        mGithub = (TextView) findViewById(R.id.githubLink);
        mPaypal = (TextView) findViewById(R.id.coffeeLink);
        mSpinner = (Spinner) findViewById(R.id.spinnerViews);

        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setSelection(getIndex(sharedPreferenceHelper.getData("ned")));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country country = (Country) parent.getItemAtPosition(position);
                sharedPreferenceHelper.saveData("ned", country.getmCode());
                mSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                bIntent.setData(Uri.parse("https://www.github.com/MCD-50/Airverge"));
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


    private int getIndex(String code){
        ArrayList<Country> countries = AppConstants.COUNTRY_LIST;
        for(int i = 0; i < countries.size(); i++){
            Country country = countries.get(i);
            if(country.getmCode().equalsIgnoreCase(code))
                return i;
        }
        return 0;
    }
}
