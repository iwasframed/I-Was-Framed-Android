package com.ebm.iwasframed.Mat_Borders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebm.iwasframed.R;
import com.ebm.iwasframed.Rational;
import com.ebm.iwasframed.SpinnerHeight;

public class MatActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnGold,btnSilver,btnBlack,btnWood,btnNext,btnCreate;
    EditText edtWidth,edtHeight;
    double edtheight,edtwidth;// for get text from textbox
    ImageView imageView;
    int screenHeight,screenWidth;  //for screensize
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    int headerLayoutHeight, headerLayoutWidth;
    String spWidth, spHeight;
    Spinner spinnerHeight, spinnerWidth;
    boolean inches,centiMetrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_mat);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);

        checkMetrics();
        clearSharedPreference();
         getScreenWidth();
        sharedPreferenceValues();
        inializeViews(this);
        setSpinnerSelectionListener();

        ViewTreeObserver observer = imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                // TODO Auto-generated method stub
                headerLayoutHeight= imageView.getHeight()-120;
                headerLayoutWidth = imageView.getWidth()-100;
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putFloat("MaxWidth", (float) headerLayoutWidth/75);
                editor.putFloat("MaxHeight", (float) headerLayoutHeight/75);
                editor.commit();

            }
        });





    }

    private void checkMetrics() {
        inches=sharedPreferences.getBoolean("UNIT",false);
        centiMetrics=sharedPreferences.getBoolean("METRICS",false);

    }

    private void getScreenWidth() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

    }

    private void sharedPreferenceValues() {
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        double h = sharedPreferences.getFloat("Imageheight",0);
        double w = sharedPreferences.getFloat("Imagewidth",0);
    }


    void clearSharedPreference(){
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("DoubleMat",false);
        editor.putBoolean("TripleMat",false);
        editor.putFloat("width",0);
        editor.putFloat("height",0);


        editor.commit();
    }

    void inializeViews(Context context){
        btnGold = (Button)findViewById(R.id.btnGold);
        btnBlack = (Button)findViewById(R.id.btnBlack);
        btnSilver = (Button)findViewById(R.id.btnSilver);
        btnWood = (Button)findViewById(R.id.btnWood);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnCreate = (Button)findViewById(R.id.btnCreate);
        edtHeight = (EditText)findViewById(R.id.edtHeight);
        edtWidth = (EditText)findViewById(R.id.edtWidth);
        imageView = (ImageView)findViewById(R.id.customFrame);

        spinnerWidth = (Spinner) findViewById(R.id.widthSpinner);
        spinnerHeight = (Spinner) findViewById(R.id.heightSpinner);

       getFrameSize();


        edt= sharedPreferences.edit();

        if(centiMetrics){
            edtWidth.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            edtHeight.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        }
        else if(inches){
            edtWidth.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtHeight.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        else{

        }

      //  imageView.invalidate();

        btnGold.setOnClickListener(this);
        btnBlack.setOnClickListener(this);
        btnSilver.setOnClickListener(this);
        btnWood.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnCreate.setOnClickListener(this);


        edt= sharedPreferences.edit();


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
        final Dialog dialog = new Dialog(this);
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

    private void getFrameSize() {

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screenHeight = imageView.getMeasuredHeight();

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnGold:
                btnGold.setBackgroundColor(getResources().getColor(R.color.ButtonColor));
                btnBlack.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnSilver.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnWood.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                changeColor("gold");
                break;

            case R.id.btnBlack:
                btnBlack.setBackgroundColor(getResources().getColor(R.color.ButtonColor));
                btnGold.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnSilver.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnWood.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                changeColor("black");
                break;

            case R.id.btnSilver:
                btnSilver.setBackgroundColor(getResources().getColor(R.color.ButtonColor));
                btnBlack.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnGold.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnWood.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                changeColor("silver");
                break;

            case R.id.btnWood:
                btnWood.setBackgroundColor(getResources().getColor(R.color.ButtonColor));
                btnBlack.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnSilver.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                btnGold.setBackgroundColor(getResources().getColor(R.color.ButtonNewColor));
                changeColor("wood");
                break;

            case R.id.btnNext:
                if(edtheight ==0 || edtwidth==0){
                    Toast.makeText(this, "Please create a frame first", Toast.LENGTH_SHORT).show();
                    return;
                }
                //double and triple mat false on start
                edt.putBoolean("DoubleMat",false);
                edt.putBoolean("TripleMat",false);
                edt.commit();


                Intent intent = new Intent(MatActivity.this,SetArtActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;

            case R.id.btnCreate:
                try {
                    if(edtHeight.getText().toString().isEmpty()==true || edtWidth.getText().toString().isEmpty() == true ){
                        Toast.makeText(this, "Please fill width and height fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        edt.putBoolean("DoubleMat",false);
                        edt.putBoolean("TripleMat",false);
                        edtheight = Double.parseDouble(edtHeight.getText().toString());
                        edtwidth = Double.parseDouble(edtWidth.getText().toString());
                       /* if(edtwidth*75<=width*75 || edtheight*75<=height*75){
                            Toast.makeText(this, "Frame Must Be Greater than Image Size", Toast.LENGTH_SHORT).show();
                        }
                        else if(edtwidth*75>=screenWidth || edtheight*75>=screenHeight){
                            Toast.makeText(this, "Frame Must Be Lesser than Device Screen", Toast.LENGTH_SHORT).show();
                        }
                        else {*/

                        Log.wtf("Frame Width", String.valueOf(edtwidth*75));
                        Log.wtf("Frame Height", String.valueOf((edtheight*Double.parseDouble(spHeight))*75));

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putFloat("Frameheight",(float)edtheight+Float.parseFloat(spHeight));
                            editor.putFloat("Framewidth",(float)edtwidth+Float.parseFloat(spWidth));
                            editor.putBoolean("start",true);
                            editor.putBoolean("FRAME",true);
                            editor.commit();
                            imageView.invalidate();
                        }

                } catch (Exception e){

                }



                break;

            default:
        }

    }


    void changeColor(String name){
        int color=0;
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (name.equals("black")){
            color = getResources().getColor(R.color.black); //The color u want
        } else if (name.equals("silver")) {
            color = getResources().getColor(R.color.silver);

        }
        else if(name.equals("wood")){
            color = getResources().getColor(R.color.wood);
        }
        else if(name.equals("gold")){
            color = getResources().getColor(R.color.gold);
        }
        editor.putInt("color",color);
        editor.commit();
        imageView.invalidate();
    }


    @Override
    public void onBackPressed() {
      super.onBackPressed();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearDoubleAndTripleMat();
    }

    private void clearDoubleAndTripleMat() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DoubleMat",false);
        editor.putBoolean("TripleMat",false);
        editor.putFloat("Imagewidth",0.0f);
        editor.putFloat("Imageheight", 0.0f);
        editor.commit();
        imageView.invalidate();
    }
}
