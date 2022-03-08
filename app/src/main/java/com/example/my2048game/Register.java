package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends AppCompatActivity {

    private static final String TAG = Register.class.getSimpleName();
    private DBHelper mDB;

    private EditText mEditUserView;
    private EditText mEditPassword;
    private EditText mEditPassword2;
    private Button mButtonRegister;
    private Boolean passVisible1 = false;
    private Boolean passVisible2 = false;

    // Unique tag for the intent reply.
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY1 = "com.example.android.wordlistsql.REPLY1";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditUserView = (EditText) findViewById(R.id.UserName);
        mEditPassword = (EditText) findViewById(R.id.TextPassword);
        mEditPassword2 = (EditText) findViewById(R.id.TextPassword2);
        mButtonRegister = (Button) findViewById(R.id.signUp);
        mDB = new DBHelper(this);
        mEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passVisible1) {
                    mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passVisible1 = false;
                    mEditPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                }
                else {
                    mEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passVisible1 = true;
                    mEditPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                }
            }
        });
        mEditPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passVisible2) {
                    mEditPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passVisible2 = false;
                    mEditPassword2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                }
                else {
                    mEditPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passVisible2 = true;
                    mEditPassword2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                }
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditUserView.getText().toString().isEmpty() ||
                        mEditPassword.getText().toString().isEmpty() ||
                        mEditPassword2.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    builder.setMessage("There are empty fields, please fill them in.")
                            .setTitle("ERROR");
                    builder.create().show();
                } else if (!mEditPassword.getText().toString().equals(mEditPassword2.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    builder.setMessage("Password must be equals.")
                            .setTitle("PASSWORD ERROR");
                    builder.create().show();
                } else {
                    if (searchDB()) {
                        returnReply(view);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setMessage("User already registered")
                                .setTitle("ERROR USER");
                        builder.create().show();
                    }
                }
            }
        });
    }

    public void returnReply(View view) {
        String user = mEditUserView.getText().toString();
        String password = mEditPassword.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, user);
        replyIntent.putExtra(EXTRA_REPLY1, password);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private boolean searchDB(){
        String user = mEditUserView.getText().toString();
        Cursor cursor = mDB.search(user);
        if (cursor != null & cursor.getCount() > 0) {
            cursor.moveToFirst();
            int index;
            String result;
            do {
                index = cursor.getColumnIndex(DBHelper.KEY_USER);
                result = cursor.getString(index);
                if (result.equals(user)){
                    return false;
                }
            } while (cursor.moveToNext());
            cursor.close();
        } return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}