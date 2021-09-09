package com.ebm.iwasframed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ebm.iwasframed.Frame_Size.FrameActivity;
import com.ebm.iwasframed.Mat_Borders.MatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnFrameSize, btnContactUs,btnPrivacyPolicy,btnSettings,btnMatBorders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        clearSharedPreferences();

    }

    private void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt  = preferences.edit();
        edt.clear();
        edt.commit();



        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);
        if(!inches&&!centiMetrics){
            editor.putBoolean("UNIT", true);
        }


        editor.putBoolean("DoubleMat",false);
        editor.putBoolean("TripleMat",false);
        editor.putFloat("MatHeight",0.0f);
        editor.putFloat("MatWidth",0.0f);
        editor.putFloat("OverLap",0.0f);
        editor.putFloat("MaxWidth", 0.0f);
        editor.putFloat("MaxHeight", 0.0f);
        editor.putBoolean("DecimalFraction", false);
        editor.putString("ImageAddress",null);
        editor.putFloat("Imagewidth",0.0f);
        editor.putFloat("Imageheight",0.0f);
        editor.putFloat("Doublethickness",0.0f);
        editor.putFloat("Framewidth",0.0f);
        editor.putFloat("Frameheight",0.0f);
        editor.putString("Convert", "0");
        editor.putBoolean("FRAME",false);
        editor.putBoolean("start",false);
        editor.putInt("MATONECOLOR",0);
        editor.putInt("MATTWOCOLOR",0);
        editor.putBoolean("SetImage",false);
        editor.putInt("MATTHREECOLOR",0);
        editor.putBoolean("SHOWDOUBLEMAT",false);
        editor.putBoolean("SHOWTRIPLEMAT",false);
        editor.putBoolean("RESULTS",false);
        editor.commit();
    }


    void initializeViews(){
        btnFrameSize = (Button) findViewById(R.id.btnFrameSize);
        btnContactUs = (Button) findViewById(R.id.btnContactUs);
        btnPrivacyPolicy = (Button) findViewById(R.id.btnPrivacyPolicy);
        btnSettings=(Button)findViewById(R.id.btnSettings);
        btnMatBorders=(Button)findViewById(R.id.btnMatBorders);
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("start",false);
        editor.commit();
        btnFrameSize.setOnClickListener(this);
        btnContactUs.setOnClickListener(this);
        btnPrivacyPolicy.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnMatBorders.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (view.getId()){
            case R.id.btnFrameSize:

                intent = new Intent(MainActivity.this,FrameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;

            case R.id.btnPrivacyPolicy:
                intent = new Intent(MainActivity.this,PrivacyPolicyActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
            case R.id.btnContactUs:
                intent = new Intent(MainActivity.this,ContactUs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
            case R.id.btnSettings:
                intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

                break;
            case R.id.btnMatBorders:
                intent = new Intent(MainActivity.this,MatActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

                break;




            default:
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("EXIT")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearSharedPreferences();
    }
}
