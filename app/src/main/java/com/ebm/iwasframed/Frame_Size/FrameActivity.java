package com.ebm.iwasframed.Frame_Size;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebm.iwasframed.Config;
import com.ebm.iwasframed.R;
import com.ebm.iwasframed.Rational;
import com.ebm.iwasframed.SpinnerHeight;

public class FrameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCreate, btnNext,btnGold,btnSilver,btnBlack,btnWood;
    EditText edtHeight, edtWidth;
    Spinner spinnerHeight, spinnerWidth;
    String height, width;
    ImageView imgFrame;
    SharedPreferences sharedPreferences;
    int headerLayoutHeight, headerLayoutWidth;
    String spWidth, spHeight;
    boolean inches, cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        checkMetrics();
        initializeViews();
        getMaxWidthAndHeight();
        setSpinnerSelectionListener();

    }

    private void checkMetrics() {
        SharedPreferences preferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        inches= preferences.getBoolean("UNIT",false);
        cm= preferences.getBoolean("METRICS",false);
    }

    private void setSpinnerSelectionListener() {
        spinnerWidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int a = adapterView.getSelectedItemPosition();


                    spWidth = spinnerWidth.getItemAtPosition(spinnerWidth.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spHeight = spinnerHeight.getItemAtPosition(spinnerHeight.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getMaxWidthAndHeight() {
        ViewTreeObserver observer = imgFrame.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                // TODO Auto-generated method stub
                headerLayoutHeight= imgFrame.getHeight()-100;
                headerLayoutWidth = imgFrame.getWidth()-80;
                imgFrame.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putFloat(Config.MAX_WIDTH, (float) headerLayoutWidth/75);
                editor.putFloat(Config.MAX_HEIGHT, (float) headerLayoutHeight/75);
                editor.commit();

            }
        });
    }


    private void initializeViews() {
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtWidth = (EditText) findViewById(R.id.edtWidth);

        spinnerWidth = (Spinner) findViewById(R.id.widthSpinner);
        spinnerHeight = (Spinner) findViewById(R.id.heightSpinner);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnGold = (Button)findViewById(R.id.btnGold);
        btnBlack = (Button)findViewById(R.id.btnBlack);
        btnSilver = (Button)findViewById(R.id.btnSilver);
        btnWood = (Button)findViewById(R.id.btnWood);

        imgFrame = (ImageView) findViewById(R.id.customFrame);

        btnGold.setOnClickListener(this);
        btnBlack.setOnClickListener(this);
        btnSilver.setOnClickListener(this);
        btnWood.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        btnNext.setOnClickListener(this);

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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCreate:

                width = edtWidth.getText().toString();
                height = edtHeight.getText().toString();

                if(!spWidth.equals("0")){
                float a = Float.parseFloat(width) + (float) Rational.fractionToDecimal(spWidth);
                    width = String.valueOf(a);
            }
                if(!spHeight.equals("0")){
                    float a = Float.parseFloat(height) + (float) Rational.fractionToDecimal(spHeight);
                    height = String.valueOf(a);
                }



                if(height.isEmpty()==true || width.isEmpty()==true){
                    Toast.makeText(this, "Please fill width and height fields", Toast.LENGTH_SHORT).show();
                    return;
                }




                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(Config.FRAME_WIDTH, Float.parseFloat(width));
                editor.putFloat(Config.FRAME_HEIGHT, Float.parseFloat(height));
                editor.putString(Config.FRAME_WIDTH_OVERLAP, spWidth);
                editor.putString(Config.FRAME_HEIGHT_OVERLAP, spHeight);
                editor.commit();
                imgFrame.invalidate();


                break;

            case R.id.btnNext:

                if(height==null || width==null){
                    Toast.makeText(this, "Please create frame first", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(FrameActivity.this, BorderSizeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                break;

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
        }
    }

    void changeColor(String name){
        int color=0;
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
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
        editor.putInt(Config.FRAME_COLOR,color);
        editor.commit();
        imgFrame.invalidate();
    }
}
