package com.ebm.iwasframed.Mat_Borders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebm.iwasframed.MainActivity;
import com.ebm.iwasframed.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
//import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MatResultActivity extends AppCompatActivity implements View.OnClickListener{

 //   RelativeLayout colorPicker,gallery;
    Button btnDone,btnSave;
    EditText matOne,matTwo,matThree;
    ImageView imgFrame;
    TextView txtMatone,txtmatTwo,txtMatThree;
    ImageView imageView;
    FrameLayout frameLayout;
    double height,width;
    SharedPreferences sharedPreferences;
    String uri=null;
    LinearLayout LL, linear1, linear2, linear3;
    ImageView imgMat1, imgMat2, imgMat3;

    private static final int RESULT_LOAD_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_result);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();


        if(uri!=null) {
//            setImageSize();
        }


      matOne.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(charSequence.length()>6) {
                  try {
                      String edt = matOne.getText().toString();
                      int whiteInt = Color.parseColor(edt);
                      changeColor(whiteInt, 1, false);
                  }
                  catch (Exception e){
                      Toast.makeText(MatResultActivity.this, "Wrong Colour", Toast.LENGTH_SHORT).show();
                  }
              }
          }
//FF400g
          @Override
          public void afterTextChanged(Editable editable) {

          }
      });
      matTwo.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(charSequence.length()>6) {
                  try {
                      String edt = matTwo.getText().toString();
                      int whiteInt = Color.parseColor(edt);
                      changeColor(whiteInt, 2, false);
                  }
                  catch (Exception e){
                      Toast.makeText(MatResultActivity.this, "Wrong Colour", Toast.LENGTH_SHORT).show();
                  }
              }
          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });


      matThree.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(charSequence.length()>6) {
                  try {
                      String edt = matThree.getText().toString();
                      int whiteInt = Color.parseColor(edt);
                      changeColor(whiteInt, 3, false);
                  }
                  catch (Exception e){
                      Toast.makeText(MatResultActivity.this, "Wrong Colour", Toast.LENGTH_SHORT).show();
                  }
              }
          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });

    }



    void initializeViews(){
      //  colorPicker = (RelativeLayout) findViewById(R.id.colorPicker);
        //gallery = (RelativeLayout) findViewById(R.id.gallery);
        matOne=(EditText)findViewById(R.id.matOne);
        matTwo=(EditText)findViewById(R.id.matTwo);
        matThree=(EditText)findViewById(R.id.matThree);

        LL = (LinearLayout)findViewById(R.id.LL);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);



        txtMatone=(TextView)findViewById(R.id.txtMatone);
        txtmatTwo=(TextView)findViewById(R.id.txtmatTwo);
        txtMatThree=(TextView)findViewById(R.id.txtMatThree);

        imgMat1 = (ImageView) findViewById(R.id.imgMat1);
        imgMat2 = (ImageView) findViewById(R.id.imgMat2);
        imgMat3 = (ImageView) findViewById(R.id.imgMat3);

        imgMat1.setOnClickListener(this);
        imgMat2.setOnClickListener(this);
        imgMat3.setOnClickListener(this);


        btnDone = (Button) findViewById(R.id.btnDone);
        btnSave = (Button) findViewById(R.id.btnSave);
        imgFrame = (ImageView) findViewById(R.id.imgFrame);
         imageView = (ImageView) findViewById(R.id.imgView);//inside
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        uri=sharedPreferences.getString("ImageAddress",null);
        boolean doubleMat = sharedPreferences.getBoolean("DoubleMat", false);
        boolean tripleMat = sharedPreferences.getBoolean("TripleMat", false);

        if(!doubleMat){
            linear2.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);
            txtmatTwo.setText("");
            txtMatThree.setText("");
        }
        else if(!tripleMat){
            linear2.setVisibility(View.GONE);
            txtMatThree.setText("");
            txtmatTwo.setText("BOTTOM MAT");
        }
        if(uri!=null) {

            Bitmap photo = StringToBitMap(uri);
            imageView.setImageBitmap(photo);
            // imageView.setImageBitmap(photo);
        }

        matOne.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                pickColor(1);
                return true;
            }
        });


        matOne.setOnClickListener(this);
        matTwo.setOnClickListener(this);
        matThree.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        imgFrame.invalidate();
    }


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //  cropImage(selectedImage);

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnDone:
                Intent intent = new Intent(MatResultActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;

            case R.id.btnSave:
                Intent intent1 = new Intent(MatResultActivity.this,ImageActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;

            case R.id.imgMat1:
                pickColor(1);
                break;

            case R.id.imgMat2:
                pickColor(2);
                break;

            case R.id.imgMat3:
                pickColor(3);
                break;
        }
    }

    public void saveFrame(Bitmap bitmap){
        File exportDir = new File(Environment.getExternalStorageDirectory().toString()
                + "/IWasFramed/" + "images", "");
        exportDir.mkdirs();

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, System.currentTimeMillis() + ".jpg");

        try {
            FileOutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    void pickColor(final int id){

        ColorPickerDialogBuilder
                .with(MatResultActivity.this)
                .setTitle("Choose color")
                .initialColor(Color.BLUE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                                Toast.makeText(MatResultActivity.this,"onColorSelected: 0x" + Integer.toHexString(selectedColor),Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                                changeBackgroundColor(selectedColor);
//                        ImageView lineColorCode = (ImageView) findViewById(R.id.imgFrame);
//                        int color = Color.parseColor("#AE6118"); //The color u want
//                        lineColorCode.setColorFilter(selectedColor)
                        int matid = id;
                        changeColor(selectedColor,matid,true);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    void changeColor(int color,int id,boolean b){
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        if(id==1){
            if(b) {
                matOne.setText(hexColor);
            }
        editor.putInt("Mat1Color",color);
        }
        else if(id==2){
            if(b) {
                matTwo.setText(hexColor);
            }
            editor.putInt("Mat2Color",color);
           }
        else if (id==3){
            if(b) {
                matThree.setText(hexColor);
            }
            editor.putInt("Mat3Color",color);
        }

       // editor.putInt("color",color);
        editor.commit();
        imgFrame.invalidate();
    }


    @Override
    public void onBackPressed() {
     super.onBackPressed();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }


    @Override
    protected void onResume() {
        saveSharedPreference();
        super.onResume();
    }

    private void saveSharedPreference() {
        SharedPreferences preferences = getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("RESULTS", true);
        editor.commit();
    }
}
