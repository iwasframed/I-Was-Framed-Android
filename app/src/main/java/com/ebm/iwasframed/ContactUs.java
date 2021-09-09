package com.ebm.iwasframed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity implements View.OnClickListener{


    Button btnBack, btnSubmit;
    EditText edtFname, edtLname, edtPhone, edtMsg, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initializeViews();
    }


    void initializeViews(){
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        edtFname = (EditText) findViewById(R.id.edtFname);
        edtLname = (EditText) findViewById(R.id.edtLname);
        edtMsg = (EditText) findViewById(R.id.edtMessage);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnBack:
                intent = new Intent(ContactUs.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;

            case R.id.btnSubmit:
                String email = edtEmail.getText().toString();
                String fname = edtFname.getText().toString();
                String lname = edtLname.getText().toString();
                String phone = edtPhone.getText().toString();
                String msg = edtMsg.getText().toString();


                if(email.isEmpty()==true || fname.isEmpty()==true || lname.isEmpty()==true ||
                        phone.isEmpty()==true || msg.isEmpty()==true){
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendEmail(fname,lname,msg,phone,email);

                break;

            default:
        }
    }


    private void sendEmail(String fname, String lname, String msg, String phone, String email){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"app@iwasframed.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Customer @ I Was Framed");

        i.putExtra(Intent.EXTRA_TEXT   , "Name: " + fname+" "+lname + "\n" +
        "Phone: " + phone + "\n" +
        "Email: " + email + "\n" +
        "Message: " + msg);

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
            this.finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUs.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
