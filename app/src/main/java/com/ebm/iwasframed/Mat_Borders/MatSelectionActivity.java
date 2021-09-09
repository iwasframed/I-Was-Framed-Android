package com.ebm.iwasframed.Mat_Borders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebm.iwasframed.R;
import com.ebm.iwasframed.SpinnerHeight;

public class MatSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroup;
    Button btnBack, btnNext;
    LinearLayout doubleLayout, tripleLayout;
    Spinner doubleMatSpinner, tripleMatSpinner1, tripleMatSpinner2;
    String checkedValue="";
    String doubleMatOverlap, tripleMatOverlap1, tripleMatOverlap2;
    EditText edtDoubleMat, edtTripleMat1, edtTripleMat2;
    boolean inches,cm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_selection);

        checkMetrics();
        intializeViews();
        fillSpinners();
        setCheckedChangeListener();
        setSpinnerSelectionListener();
    }

    private void checkMetrics() {
        SharedPreferences preferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        inches = preferences.getBoolean("UNIT",false);
        cm= preferences.getBoolean("METRICS",false);
    }

    private void setSpinnerSelectionListener() {

        doubleMatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = edtDoubleMat.getText().toString();

                doubleMatOverlap =doubleMatSpinner.getItemAtPosition(doubleMatSpinner.getSelectedItemPosition()).toString();

                if(!text.equals("0")){
                    if(text.isEmpty()==false) {
                        String[] split = doubleMatOverlap.split("/");
                        int a = Integer.parseInt(split[0]);
                        int b = Integer.parseInt(split[1]);
                        int result = b * Integer.parseInt(text) + a;
                        String final_result = result + "/" + split[1];
                        doubleMatOverlap = final_result;
                    }
                }

                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tripleMatSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = edtTripleMat1.getText().toString();

                    tripleMatOverlap1 = tripleMatSpinner1.getItemAtPosition(tripleMatSpinner1.getSelectedItemPosition()).toString();

                if(!text.equals("0")){
                    if(inches) {
                        if (text.isEmpty() == false) {
                            String[] split = tripleMatOverlap1.split("/");
                            try {
                                int a = Integer.parseInt(split[0]);
                                int b = Integer.parseInt(split[1]);
                                int result = b * Integer.parseInt(text) + a;
                                String final_result = result + "/" + split[1];
                                tripleMatOverlap1 = final_result;
                            } catch (Exception e) {
                            }
                        }
                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tripleMatSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = edtTripleMat2.getText().toString();
                    tripleMatOverlap2 =tripleMatSpinner2.getItemAtPosition(tripleMatSpinner2.getSelectedItemPosition()).toString();

                if(!text.equals("0")) {
                    if (inches) {
                        if (text.isEmpty() == false) {
                            String[] split = tripleMatOverlap1.split("/");
                            try {
                                int a = Integer.parseInt(split[0]);
                                int b = Integer.parseInt(split[1]);
                                int result = b * Integer.parseInt(text) + a;
                                String final_result = result + "/" + split[1];
                                tripleMatOverlap1 = final_result;
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillSpinners() {
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);

        ArrayAdapter<String> adapter;
        String[] list = getResources().getStringArray(R.array.frames);
        String[] centiMeters= getResources().getStringArray(R.array.centimeters_frames);




            if(inches){
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doubleMatSpinner.setAdapter(adapter);
                tripleMatSpinner1.setAdapter(adapter);
                tripleMatSpinner2.setAdapter(adapter);

            }
            else if(cm){
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, centiMeters);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                doubleMatSpinner.setAdapter(adapter);
                tripleMatSpinner1.setAdapter(adapter);
                tripleMatSpinner2.setAdapter(adapter);

            }
        SpinnerHeight.setHeight(doubleMatSpinner);
        SpinnerHeight.setHeight(tripleMatSpinner1);
        SpinnerHeight.setHeight(tripleMatSpinner2);




    }

    private void setCheckedChangeListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked)
                {
                    if(checkedRadioButton.getText().equals("Double Mat")){
                        doubleLayout.setVisibility(View.VISIBLE);
                        tripleLayout.setVisibility(View.GONE);
                        checkedValue="1";
                    }
                    else if(checkedRadioButton.getText().equals("Triple Mat")){
                        doubleLayout.setVisibility(View.GONE);
                        tripleLayout.setVisibility(View.VISIBLE);
                        checkedValue="2";
                    }
                    else {
                        doubleLayout.setVisibility(View.GONE);
                        tripleLayout.setVisibility(View.GONE);
                        checkedValue="0";
                    }

                }

            }
        });
    }

    private void intializeViews() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        doubleMatSpinner = (Spinner) findViewById(R.id.doubleMatSpinner);
        doubleLayout = (LinearLayout) findViewById(R.id.doubleLayout);
        tripleLayout = (LinearLayout) findViewById(R.id.tripleLayout);
        tripleMatSpinner1 = (Spinner) findViewById(R.id.tripleMatSpinner1);
        tripleMatSpinner2 = (Spinner) findViewById(R.id.tripleMatSpinner2);
        edtDoubleMat = (EditText) findViewById(R.id.edtDoubleMat);
        edtTripleMat1 = (EditText) findViewById(R.id.edtTripleMat1);
        edtTripleMat2 = (EditText) findViewById(R.id.edtTripleMat2);


        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnBack:
                MatSelectionActivity.super.onBackPressed();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

            case R.id.btnNext:
                SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(checkedValue.equals("")){
                    Toast.makeText(this, "Please select a mat", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(checkedValue.equals("0")){
                    editor.putBoolean("DoubleMat", false);
                    editor.putBoolean("TripleMat", false);
                }
                else if(checkedValue.equals("1")){

                    if(doubleMatOverlap.equals("0") && (edtDoubleMat.getText().toString().equals("0") ||
                    edtDoubleMat.getText().toString().isEmpty()==true)){
                        Toast.makeText(this, "Please fill the Bottom Mat overlap", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    editor.putBoolean("DoubleMat", true);

                    if(doubleMatOverlap.equals("0")){
                        doubleMatOverlap = edtDoubleMat.getText().toString();
                    }

                    editor.putString("Convert", doubleMatOverlap);
                }
                else if(checkedValue.equals("2")){

                    if(tripleMatOverlap1.equals("0") && (edtTripleMat1.getText().toString().equals("0") ||
                            edtTripleMat1.getText().toString().isEmpty()==true)){
                        Toast.makeText(this, "Please fill the Middle Mat overlap", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(tripleMatOverlap2.equals("0") && (edtTripleMat2.getText().toString().equals("0") ||
                            edtTripleMat2.getText().toString().isEmpty()==true)){
                        Toast.makeText(this, "Please fill the Bottom Mat overlap", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    editor.putBoolean("TripleMat", true);
                    editor.putBoolean("DoubleMat", true);

                    if(tripleMatOverlap1.equals("0")){
                        tripleMatOverlap1 = edtTripleMat1.getText().toString();
                    }

                    if(tripleMatOverlap2.equals("0")){
                        tripleMatOverlap2 = edtTripleMat2.getText().toString();
                    }

                    editor.putString("TripleConvert", tripleMatOverlap1);
                    editor.putString("Convert", tripleMatOverlap2);
                }
                editor.putBoolean("RESULTS",true);
                editor.commit();

                Intent intent = new Intent(MatSelectionActivity.this, MatBorderActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
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
        editor.commit();

    }
}
