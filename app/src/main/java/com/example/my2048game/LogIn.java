package com.example.my2048game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LogIn extends AppCompatActivity {


    private EditText username;
    private EditText pass;
    private DBHelper mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mDB = new DBHelper(this);
        this.username= (EditText) findViewById(R.id.editTextTextPersonName);
        this.pass = (EditText) findViewById(R.id.editTextTextPassword);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDB.select(username.getText().toString(), pass.getText().toString())){
                    Intent intent = new Intent(LogIn.this, MenuJuegos.class);
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
                // Start empty edit activity.
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
                    mDB.insert(user, password);
                } else {
                    if (TextUtils.isEmpty(user)) {
                        Toast.makeText(getApplicationContext(),
                                R.string.empty_not_saved,
                                Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(),
                                R.string.empty_not_saved,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}