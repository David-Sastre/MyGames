package com.example.my2048game.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.my2048game.Games.MenuJuegos;
import com.example.my2048game.R;
import com.example.my2048game.Utils.DBHelper;

public class LogIn extends AppCompatActivity {


    private EditText username;
    private EditText pass;
    private Boolean visible = false;
    private DBHelper mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mDB = new DBHelper(this);
        this.username= (EditText) findViewById(R.id.editTextTextPersonName);
        this.pass = (EditText) findViewById(R.id.editTextTextPassword);
        pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(visible) {
                        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        visible = false;
                        pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    else {
                        pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        visible = true;
                        pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    }
                }
            });
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDB.selectUser(username.getText().toString(), pass.getText().toString())){
                    Intent intent = new Intent(LogIn.this, MenuJuegos.class);
                    intent.putExtra(DBHelper.KEY_USER, username.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder (LogIn.this);
                    builder.setMessage("Username or password is not correct, please try again");
                            builder.setTitle("ERROR");
                            builder.create().show();
                }
            }
        });

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Register.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String user = data.getStringExtra(Register.EXTRA_REPLY);
                String password = data.getStringExtra(Register.EXTRA_REPLY1);
                if (!TextUtils.isEmpty(user) && (!TextUtils.isEmpty(password))) {
                    mDB.insertPlayer(user, password);
                }
            }
        }
    }
}