package com.ebm.iwasframed.Frame_Size;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebm.iwasframed.Config;
import com.ebm.iwasframed.ListPopupWindowAdapter;
import com.ebm.iwasframed.R;
import com.ebm.iwasframed.Rational;
import com.ebm.iwasframed.SpinnerHeight;

import java.util.List;

public class BorderSizeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCreate, btnBack;
    EditText edtWidthTop, edtWidthBottom, edtHeightLeft, edtHeightRight;
    ImageView imgCustom;
    Spinner spWidthTop, spWidthBottom, spHeightLeft, spHeightRight;
    String widthTop, widthBottom, heightRight, heightLeft;
    String spTop, spBottom, spRight, spLeft;
    boolean inches,cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_border_size);

        checkMetrics();
        initializeViews();
        setSpinnerSelectionListener();


    }

    private void checkMetrics() {
        SharedPreferences preferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        inches= preferences.getBoolean("UNIT",false);
        cm= preferences.getBoolean("METRICS",false);
    }

    private void initializeViews() {

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnBack = (Button) findViewById(R.id.btnBack);

        edtWidthTop = (EditText) findViewById(R.id.edtWidthTop);
        edtWidthBottom = (EditText) findViewById(R.id.edtWidthBottom);
        edtHeightLeft= (EditText) findViewById(R.id.edtHeightLeft);
        edtHeightRight = (EditText) findViewById(R.id.edtHeightRight);
        imgCustom = (ImageView) findViewById(R.id.customFrame);

        spWidthTop = (Spinner) findViewById(R.id.spWidthTop);
        spWidthBottom = (Spinner) findViewById(R.id.spWidthBottom);
        spHeightRight = (Spinner) findViewById(R.id.spHeightRight);
        spHeightLeft = (Spinner) findViewById(R.id.spHeightLeft);


        btnCreate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        String[] cm_list = getResources().getStringArray(R.array.centimeters_frames);

        String[] list = getResources().getStringArray(R.array.frames);

        ArrayAdapter<String> adapter=null;
        if(inches) {
            adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        }
        else {
            adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,cm_list);
        }

        spWidthTop.setAdapter(adapter);
        spWidthBottom.setAdapter(adapter);
        spHeightLeft.setAdapter(adapter);
        spHeightRight.setAdapter(adapter);

        SpinnerHeight.setHeight(spWidthTop);
        SpinnerHeight.setHeight(spWidthBottom);
        SpinnerHeight.setHeight(spHeightLeft);
        SpinnerHeight.setHeight(spHeightRight);



        imgCustom.invalidate();
    }

    private void setSpinnerSelectionListener() {

        spWidthTop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spTop = spWidthTop.getItemAtPosition(spWidthTop.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spWidthBottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spBottom = spWidthBottom.getItemAtPosition(spWidthBottom.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spHeightLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spLeft = spHeightLeft.getItemAtPosition(spHeightLeft.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spHeightRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spRight = spHeightRight.getItemAtPosition(spHeightRight.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                BorderSizeActivity.super.onBackPressed();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;

            case R.id.btnCreate:
                String widthT = edtWidthTop.getText().toString();
                String widthB= edtWidthBottom.getText().toString();
                String heightL = edtHeightLeft.getText().toString();
                String heightR = edtHeightRight.getText().toString();


                if(widthT.equals("") || widthB.equals("")|| heightL.equals("")
                        || heightR.equals("")){
                    Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(inches) {
                    if (!spTop.equals("0")) {
                        widthT = convertImproperFraction(widthT, spTop);
                    }
                    if (!spBottom.equals("0")) {
                        widthB = convertImproperFraction(widthB, spBottom);
                    }
                    if (!spLeft.equals("0")) {
                        heightL = convertImproperFraction(heightL, spLeft);
                    }
                    if (!spRight.equals("0")) {
                        heightR = convertImproperFraction(heightR, spRight);
                    }
                }
                else {
                    if (!spTop.equals("0")) {
                        widthT = convertIntoCentimeters(widthT,spTop);
                    }
                    if (!spBottom.equals("0")) {
                        widthB = convertIntoCentimeters(widthB, spBottom);
                    }
                    if (!spLeft.equals("0")) {
                        heightL = convertIntoCentimeters(heightL, spLeft);
                    }
                    if (!spRight.equals("0")) {
                        heightR = convertIntoCentimeters(heightR, spRight);
                    }
                }



                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat(Config.FRAME_WIDTH_TOP,Float.parseFloat(widthT));
                editor.putFloat(Config.FRAME_WIDTH_BOTTOM,Float.parseFloat(widthB));
                editor.putFloat(Config.FRAME_HEIGHT_LEFT,Float.parseFloat(heightL));
                editor.putFloat(Config.FRAME_HEIGHT_RIGHT,Float.parseFloat(heightR));
                editor.commit();



                Intent intent = new Intent(BorderSizeActivity.this, FrameResultActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                break;


        }
    }

    private String convertIntoCentimeters(String num, String value){
        float a = Float.parseFloat(num) + Float.parseFloat(value);
        return String.valueOf(a);
    }

    private void showListMenu(View anchor, List<String> array, final String type) {
        final ListPopupWindow popupWindow = new ListPopupWindow(this);

        final ListAdapter adapter = new ListPopupWindowAdapter(BorderSizeActivity.this, array);

        popupWindow.setAnchorView(anchor);
        popupWindow.setAdapter(adapter);
        popupWindow.setWidth(220);
      popupWindow.setHeight(600);

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = adapter.getItem(i).toString();

                if(type.equals("top")){
                    widthTop=a;
                }
                else if(type.equals("bottom")){
                    widthBottom=a;
                }
                else if(type.equals("left")){
                    heightLeft=a;
                }
                else if(type.equals("right")){
                    heightRight=a;
                }
                Toast.makeText(BorderSizeActivity.this, adapter.getItem(i).toString() + " Selected", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    private String convertImproperFraction(String num, String value){
        String[] split = value.split("/");
        int a = Integer.parseInt(split[0]);
        int b = Integer.parseInt(split[1]);
        int result = b * Integer.parseInt(num) + a;
        String final_result = result + "/" + split[1];
        String x = String.valueOf(Rational.fractionToDecimal(final_result));
        return x;
    }


    @Override
    protected void onResume() {
        super.onResume();
        clearSharedPreference();
    }

    private void clearSharedPreference() {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(Config.FRAME_WIDTH_TOP,0);
        editor.putFloat(Config.FRAME_WIDTH_BOTTOM,0);
        editor.putFloat(Config.FRAME_HEIGHT_LEFT,0);
        editor.putFloat(Config.FRAME_HEIGHT_RIGHT,0);
        editor.commit();

    }


}
