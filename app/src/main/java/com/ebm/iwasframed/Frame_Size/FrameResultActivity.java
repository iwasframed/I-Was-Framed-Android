package com.ebm.iwasframed.Frame_Size;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ebm.iwasframed.MainActivity;
import com.ebm.iwasframed.R;

public class FrameResultActivity extends AppCompatActivity {

    Button btnDone;
    ImageView imgFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_result);

        btnDone = (Button) findViewById(R.id.btnDone);
        imgFrame = (ImageView) findViewById(R.id.customFrame);
        imgFrame.invalidate();


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrameResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
