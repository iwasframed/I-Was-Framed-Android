package com.ebm.iwasframed.Mat_Borders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ebm.iwasframed.MainActivity;
import com.ebm.iwasframed.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgView, imgFrame;
    FrameLayout frameLayout;
    Button btnSave, btnUpload, btnDone;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initializeViews();
        imgFrame.invalidate();


    }


    private void checkVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean result = checkAndRequestPermissions();
            if(result){
            saveImage();
            }
            else {
            }

        }
        else {
            saveImage();
        }
    }

    private  boolean checkAndRequestPermissions() {
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }


    private void initializeViews() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        imgView = (ImageView) findViewById(R.id.imgView);
        imgFrame = (ImageView) findViewById(R.id.imgFrame);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnUpload = (Button) findViewById(R.id.btnuploadimage);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnDone.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    Toast.makeText(this," Please provide permission to use this functionality", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void selectImage() {

        CropImage.activity()
                .setMinCropResultSize(400, 400)
                .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                .setBackgroundColor(R.color.black)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ImageActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {


            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    setImageSize();
                    Uri resultUri = result.getUri();
                    imgView.setImageURI(resultUri);


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;

        }
    }


    private void setImageSize() {
        SharedPreferences sharedPreferences = getSharedPreferences("Params", Context.MODE_PRIVATE);

       float imageWidth = sharedPreferences.getFloat("FinalImageWidth",0.0f);
        float imageHeight = sharedPreferences.getFloat("FinalImageHeight", 0.0f);
        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMeters=sharedPreferences.getBoolean("METRICS",false);


            //set image into artwork
            if(inches) {
                int h = (int) imageHeight;
                int w = (int) imageWidth;
                imgView.getLayoutParams().height = h * 75;
                imgView.getLayoutParams().width = w * 75;
            }
            else if(centiMeters){
                int w=(int)(imageWidth)*75;
                int h=(int)(imageHeight)*75;
                imgView.getLayoutParams().height = h;
                imgView.getLayoutParams().width = w;
            }

        //set image into framework


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDone:
                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

            case R.id.btnuploadimage:
                selectImage();
                break;

            case R.id.btnSave:
                checkVersion();
                break;
        }
    }

    private void saveImage(){
        Bitmap bitmap = viewToBitmap(frameLayout);
        saveFrame(bitmap);
    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void saveFrame(Bitmap bitmap){
        File exportDir = new File(Environment.getExternalStorageDirectory().toString()
                + "/I Was Framed", "");
        exportDir.mkdirs();

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, System.currentTimeMillis() + ".jpg");

        try {
            FileOutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();

            MediaScannerConnection.scanFile(this, new String[] { file.getPath() }, new String[] { "image/jpeg" }, null);

            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Sorry! Image did not save. Please try again", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
