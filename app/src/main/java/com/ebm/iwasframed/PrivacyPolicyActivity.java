package com.ebm.iwasframed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

//        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#594f3b")));
    //    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home) {
            Intent myIntent = new Intent(PrivacyPolicyActivity.this, MainActivity.class);
            startActivity(myIntent);
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        }
        return true;
    }
}
