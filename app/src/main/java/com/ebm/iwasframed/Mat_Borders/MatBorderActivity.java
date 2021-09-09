package com.ebm.iwasframed.Mat_Borders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.ebm.iwasframed.MainActivity;
import com.ebm.iwasframed.R;

public class MatBorderActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnNext, btnDone, btnSave;

    TextView decimalFraction, txtThird;
    ScrollView scrollView;
    TextView txtBottom;

    ImageView imgFrame;
    double matHeight, matWidth;
    int screenHeight, screenWidth;
    boolean doubleMat = false;
    boolean tripleMat = false;
    double overlap;
    String divide;
    boolean inches, centiMetrics;
    Switch btnDecimalfraction;

    FrameLayout secondFrame, thirdFrame;
    SharedPreferences sharedPreferences;
    ImageView doubleMatFrame, tripleMatFrame;
    int headerLayoutHeight, headerLayoutWidth;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_border);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getScreenWidth();
        initializeViews();
        getMaxFrame();
//        setImageSize();




        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        final boolean doubleMat = sharedPreferences.getBoolean("DoubleMat",false);
        final boolean tripleMat = sharedPreferences.getBoolean("TripleMat", false);

        if(!doubleMat){
            secondFrame.setVisibility(View.GONE);
            txtBottom.setVisibility(View.GONE);
        }

        if(!tripleMat){
            thirdFrame.setVisibility(View.GONE);
            txtThird.setVisibility(View.GONE);
        }

        if(tripleMat){
            thirdFrame.setVisibility(View.VISIBLE);
            txtThird.setVisibility(View.VISIBLE);
        }


        btnDecimalfraction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = sharedPreferences.edit();

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(inches) {
                    if (btnDecimalfraction.isChecked()) {

                        edt.putBoolean("DecimalFraction", true);
                        edt.commit();
                        decimalFraction.setText("Fraction");
                        imgFrame.invalidate();
                        doubleMatFrame.invalidate();
                        tripleMatFrame.invalidate();

                    } else if (!btnDecimalfraction.isChecked()) {
                        edt.putBoolean("DecimalFraction", false);
                        edt.commit();
                        decimalFraction.setText("Decimal");
                        imgFrame.invalidate();
                        doubleMatFrame.invalidate();
                        tripleMatFrame.invalidate();

                    }
                }
                else {
                    if (btnDecimalfraction.isChecked()) {

                        edt.putBoolean("DecimalFraction", true);
                        edt.commit();
                        decimalFraction.setText("Centimeters");
                        imgFrame.invalidate();
                        doubleMatFrame.invalidate();
                        tripleMatFrame.invalidate();

                    } else if (!btnDecimalfraction.isChecked()) {
                        edt.putBoolean("DecimalFraction", false);
                        edt.commit();
                        decimalFraction.setText("Meters");
                        imgFrame.invalidate();
                        doubleMatFrame.invalidate();
                        tripleMatFrame.invalidate();

                    }
                }
            }
        });


    }

    void getMaxFrame(){
        ViewTreeObserver observer = imgFrame.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
                // TODO Auto-generated method stub
                headerLayoutHeight= imgFrame.getHeight()-120;
                headerLayoutWidth = imgFrame.getWidth()-100;
                imgFrame.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putFloat("MaxWidth", (float) headerLayoutWidth/75);
                editor.putFloat("MaxHeight", (float) headerLayoutHeight/75);
                editor.commit();

                Log.wtf("Width", String.valueOf(headerLayoutWidth));
                Log.wtf("Height", String.valueOf(headerLayoutHeight));
            }
        });
    }

//    private void setImageSize() {
//        float frameWidth = sharedPreferences.getFloat("Framewidth", 0.0f);
//        float frameHeight = sharedPreferences.getFloat("Frameheight", 0.0f);
//        float imageWidth = sharedPreferences.getFloat("Imagewidth", 0.0f);
//        float ImageHeight = sharedPreferences.getFloat("Imageheight", 0.0f);
//        boolean setImage = sharedPreferences.getBoolean("SetImage", false);
//        boolean inches = sharedPreferences.getBoolean("UNIT", false);
//        boolean centiMeters = sharedPreferences.getBoolean("METRICS", false);
//
//        if (setImage) {
//            //set image into artwork
//            if (inches) {
//                int h = (int) ImageHeight;
//                int w = (int) imageWidth;
//                imgView.getLayoutParams().height = h * 75 - 10;
//                imgView.getLayoutParams().width = w * 75 - 10;
//            } else if (centiMeters) {
//                int w = (int) (imageWidth / 2.54f) * 75;
//                int h = (int) (ImageHeight / 2.54f) * 75;
//                imgView.getLayoutParams().height = h - 10;
//                imgView.getLayoutParams().width = w - 10;
//            }
//        }
//        //set image into framework
//        else {
//            if (inches) {
//                int h = (int) frameHeight;
//                int w = (int) frameWidth;
//                imgView.getLayoutParams().height = h * 75 - 10;
//                imgView.getLayoutParams().width = w * 75 - 10;
//            } else if (centiMeters) {
//                int w = (int) (frameWidth / 2.54f) * 75;
//                int h = (int) (frameHeight / 2.54f) * 75;
//                imgView.getLayoutParams().height = h - 10;
//                imgView.getLayoutParams().width = w - 10;
//            }
//        }
//    }

    void initializeViews() {
        btnNext = (Button) findViewById(R.id.btnNext);
        btnDone = (Button) findViewById(R.id.btnDone);

        decimalFraction=(TextView)findViewById(R.id.decimalFraction);

        imgFrame = (ImageView) findViewById(R.id.imgFrame);
        btnDecimalfraction = (Switch) findViewById(R.id.btnDecimalfraction);
        secondFrame = (FrameLayout) findViewById(R.id.secondFrame);

        doubleMatFrame = (ImageView) findViewById(R.id.doubleFrame);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        txtBottom = (TextView) findViewById(R.id.txtBottom);
        thirdFrame = (FrameLayout) findViewById(R.id.thirdFrame);
        tripleMatFrame = (ImageView) findViewById(R.id.tripleFrame);
        txtThird = (TextView) findViewById(R.id.txtThird);


        sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = sharedPreferences.edit();
        overlap = sharedPreferences.getFloat("OverLap", 0.0f);
        inches = sharedPreferences.getBoolean("UNIT", false);
        centiMetrics = sharedPreferences.getBoolean("METRICS", false);

        if(!inches){
            decimalFraction.setText("Meters");
        }

        sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        String uri=sharedPreferences.getString("ImageAddress",null);
        getFrameSize();
        btnNext.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private void getFrameSize() {

        ViewTreeObserver vto = imgFrame.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screenHeight = imgFrame.getMeasuredHeight();

            }
        });

    }

    private void getScreenWidth() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnDone:
                intent = new Intent(MatBorderActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            case R.id.btnNext:
                intent = new Intent(MatBorderActivity.this, MatResultActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearSharedPreferenceColors();
    }

    private void clearSharedPreferenceColors() {
        SharedPreferences preferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Mat1Color",0);
        editor.putInt("Mat2Color",0);
        editor.putInt("Mat3Color",0);
        editor.commit();

    }

}
