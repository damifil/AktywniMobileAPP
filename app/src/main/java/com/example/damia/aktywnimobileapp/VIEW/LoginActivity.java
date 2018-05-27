package com.example.damia.aktywnimobileapp.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.damia.aktywnimobileapp.PRESENTER.LoginPresenter;
import com.example.damia.aktywnimobileapp.R;

public class LoginActivity extends Activity {

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Pacifico-Regular.ttf");
        TextView tv = (TextView) findViewById(R.id.TVLog);
        tv.setTypeface(tf);
        presenter=new LoginPresenter(this);


        Button loginButton =  findViewById(R.id.BTLogin);
        loginButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               presenter.login();
            }
        });


        Button passwordButton =  findViewById(R.id.BTPassword);
        passwordButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", value);
                LoginActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.SaveModel();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
