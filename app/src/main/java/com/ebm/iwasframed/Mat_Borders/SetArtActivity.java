package com.ebm.iwasframed.Mat_Borders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebm.iwasframed.R;
import com.ebm.iwasframed.Rational;
import com.ebm.iwasframed.SpinnerHeight;


public class SetArtActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView imgFrame;
    ImageView imgView;
    FrameLayout frameLayout;
    public Button btnsetSize,btnNext;
    public EditText edtWidth,edtHeight;
    Spinner edtOverlap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    double overLap=0;
    float frameWidth=0;
    float frameHeight=0;
    boolean check=false;
    String spWidth, spHeight;
    Spinner spinnerHeight, spinnerWidth;
    boolean inches=false, centiMetrics=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_art);

        viewIntilizers();
        fillSpinner();
        setSpinnerSelectionListener();

    }


    private void viewIntilizers() {
        edtWidth=(EditText)findViewById(R.id.edtWidth);
        edtHeight=(EditText)findViewById(R.id.edtHeight);
        edtOverlap=(Spinner) findViewById(R.id.edtOverlap);

        btnNext=(Button)findViewById(R.id.btnNext);
        btnsetSize=(Button)findViewById(R.id.btnsetSize);

        imgFrame = (ImageView) findViewById(R.id.imgFrame);
        imgView = (ImageView) findViewById(R.id.imgView);//inside
       frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        spinnerWidth = (Spinner) findViewById(R.id.widthSpinner);
        spinnerHeight = (Spinner) findViewById(R.id.heightSpinner);


        sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        edt= sharedPreferences.edit();
        inches =sharedPreferences.getBoolean("UNIT",false);
        centiMetrics=sharedPreferences.getBoolean("METRICS",false);

        frameWidth = sharedPreferences.getFloat("Framewidth",0.0f);
        frameHeight = sharedPreferences.getFloat("Frameheight",0.0f);

        if(centiMetrics){
            edtWidth.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            edtHeight.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // edtOverlap.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        else if(inches){
            edtWidth.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
            //  edtOverlap.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else{

        }

        btnNext.setOnClickListener(this);
        btnsetSize.setOnClickListener(this);

        String[] cm_list = getResources().getStringArray(R.array.centimeters_frames);
        String[] list = getResources().getStringArray(R.array.frames);

        ArrayAdapter<String> adapter=null;
        if(inches) {
            adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        }
        else {
            adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,cm_list);
        }

        spinnerWidth.setAdapter(adapter);
        spinnerHeight.setAdapter(adapter);

        SpinnerHeight.setHeight(spinnerHeight);
        SpinnerHeight.setHeight(spinnerWidth);

    }

    private void setSpinnerSelectionListener() {
        edtOverlap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                overLap =Rational.fractionToDecimal(edtOverlap.getItemAtPosition(edtOverlap.getSelectedItemPosition()).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinnerWidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    spWidth = String.valueOf(Rational.fractionToDecimal(spinnerWidth.getItemAtPosition(spinnerWidth.getSelectedItemPosition()).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spHeight = String.valueOf(Rational.fractionToDecimal(spinnerHeight.getItemAtPosition(spinnerHeight.getSelectedItemPosition()).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillSpinner(){
        try{
           ArrayAdapter<String> adapter;

            String[] list = getResources().getStringArray(R.array.frames);
            String[] centiMeters=getResources().getStringArray(R.array.centimeters_frames);



            if(inches){
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                edtOverlap.setAdapter(adapter);
            }
            else if(centiMetrics){
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, centiMeters);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                edtOverlap.setAdapter(adapter);
            }
            else{

            }

            SpinnerHeight.setHeight(edtOverlap);

        }
        catch (Exception e){
        }

    }



    @Override
    public void onClick(View view) {
        Intent intent;
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (view.getId()){
            case R.id.btnsetSize:
                try{

                    double h=Double.parseDouble(edtHeight.getText().toString());
                    double w=Double.parseDouble(edtWidth.getText().toString());


                    if(edtHeight.getText().toString().isEmpty()==true || edtWidth.getText().toString().isEmpty()==true){
                        Toast.makeText(this, "Please fill the width and height fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(frameHeight<=h || frameWidth<=w){
                        Toast.makeText(this, "Image size should be smaller than frame size", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    check =true;



                    editor.putFloat("Imageheight",(float) h+Float.parseFloat(spHeight));
                    editor.putFloat("Imagewidth",(float) w+Float.parseFloat(spWidth));
                    edt.putBoolean("DoubleMat",false);
                    edt.putBoolean("TripleMat",false);
                    editor.putFloat("OverLap",(float)overLap);
                    editor.putBoolean("start",false);
                    editor.putBoolean("SetImage",true);
                    editor.commit();
                    imgFrame.invalidate();
                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
             break;

            case R.id.btnNext:
                if(edtHeight.getText().toString().isEmpty()==true || edtWidth.getText().toString().isEmpty()==true || !check){
                    Toast.makeText(this, "Please set image size first", Toast.LENGTH_SHORT).show();
                    return;
                }

                editor.putBoolean("RESULTS",true);
                editor.commit();
                intent=new Intent(SetArtActivity.this,MatSelectionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
//            case R.id.btnuploadimage:
//                showPictureDialog();
//                setImageSize();
//                break;


        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        clearDoubleAndTripleMat();
    }

    private void clearDoubleAndTripleMat() {
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DoubleMat",false);
        editor.putBoolean("TripleMat",false);
        editor.putBoolean("RESULTS",false);
        edt.putBoolean("DecimalFraction", true);
        editor.commit();
        imgFrame.invalidate();
    }
}
