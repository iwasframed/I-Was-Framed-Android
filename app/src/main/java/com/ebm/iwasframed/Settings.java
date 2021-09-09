package com.ebm.iwasframed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    Switch btnUnit,btnMetrics;
     Button btnBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        viewIntilizers();
        setDefaultSetting();

    }

    private void setDefaultSetting() {   //INCHES
        btnUnit.setChecked(true);
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
       boolean inches= sharedPreferences.getBoolean("UNIT",false);
       boolean centiMetrices= sharedPreferences.getBoolean("METRICS",false);

       if(inches){
           btnUnit.setChecked(true);

           btnMetrics.setChecked(false);
       }
       else if(centiMetrices){
           btnMetrics.setChecked(true);
           btnUnit.setChecked(false);
       }
       else{

       }

        btnUnit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btnUnit.isChecked()){
                    btnMetrics.setChecked(false);
                    edt.putBoolean("UNIT",true);
                    edt.putBoolean("METRICS",false);
                    edt.commit();
                }
                else if(!btnUnit.isChecked()){
                    btnMetrics.setChecked(true);
                    edt.putBoolean("UNIT",false);
                    edt.putBoolean("METRICS",true);
                    edt.commit();
                }
            }
        });
        btnMetrics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(btnMetrics.isChecked()){
                    btnUnit.setChecked(false);  //METRICS
                    edt.putBoolean("UNIT",false);
                    edt.putBoolean("METRICS",true);
                    edt.commit();
                }
                else if(!btnMetrics.isChecked()){
                    btnUnit.setChecked(true);
                    edt.putBoolean("UNIT",true);
                    edt.putBoolean("METRICS",false);
                    edt.commit();
                }
            }
        });
    }

    private void viewIntilizers() {

        btnUnit=(Switch)findViewById(R.id.btnUnit);
        btnMetrics=(Switch)findViewById(R.id.btnMetrics);
        btnBack=(Button)findViewById(R.id.btnBack);


        sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        edt= sharedPreferences.edit();

        btnBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnBack:
                intent = new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }
}
