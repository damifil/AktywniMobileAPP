package com.example.damia.aktywnimobileapp.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.damia.aktywnimobileapp.PRESENTER.LoginPresenter;
import com.example.damia.aktywnimobileapp.R;
import com.example.damia.aktywnimobileapp.databinding.ActivityLoginBinding;
import com.github.florent37.viewtooltip.ViewTooltip;
import static com.github.florent37.viewtooltip.ViewTooltip.ALIGN.CENTER;
import static com.github.florent37.viewtooltip.ViewTooltip.Position.TOP;


public class LoginActivity extends Activity {

    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter=new LoginPresenter(this);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setModel(presenter.model);

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Pacifico-Regular.ttf");
        TextView tv =  findViewById(R.id.TVLog);
        tv.setTypeface(tf);
        tf = Typeface.createFromAsset(getAssets(),
                "fonts/fa-solid-900.ttf");
        tv =  findViewById(R.id.TVLoginIco);
        tv.setTypeface(tf);
        tv =  findViewById(R.id.TVPasswordIco);
        tv.setTypeface(tf);
        tv =  findViewById(R.id.TVPasswordIcoSee);
        tv.setTypeface(tf);

        final TextView seeIco=findViewById(R.id.TVPasswordIcoSee);
        seeIco.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                seeIco.setText( seeIco.getText()=="\uf070"? "\uf06e":"\uf070");
                if(seeIco.getText()=="\uf070")
                {
                     TextView t =findViewById(R.id.ETPassword);
                             t.setTransformationMethod(null);
                }
                else
                {
                    TextView t =findViewById(R.id.ETPassword);
                    t.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        tf = Typeface.createFromAsset(getAssets(),
                "fonts/Segoe UI.ttf");
        Button loginButton =  findViewById(R.id.BTLogin);
        loginButton.setTypeface(tf);
        Button passwordButton =  findViewById(R.id.BTPassword);
        passwordButton.setTypeface(tf);

        loginButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               presenter.login();
            }
        });

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

    public void goToHomeView()
    {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        //myIntent.putExtra("key", value);
        LoginActivity.this.startActivity(myIntent);
    }

    public  void showTooltip()
    {
        ViewTooltip
                .on(this, findViewById((R.id.ETLogin)))
                .autoHide(true , 4000)
                .clickToHide(false)
                .align(CENTER)
                .position(TOP)
                .text("Niepoprawny login lub hasło")
                .textColor(Color.parseColor("#FFFF00"))
                .color(Color.parseColor("#1B3544"))
                .corner(40)
                .arrowWidth(30)
                .arrowHeight(15)
                .distanceWithView(0)
                .onDisplay(new ViewTooltip.ListenerDisplay() {
                    @Override
                    public void onDisplay(View view) {

                    }
                })
                .onHide(new ViewTooltip.ListenerHide() {
                    @Override
                    public void onHide(View view) {

                    }
                })
                .show();
    }


}
