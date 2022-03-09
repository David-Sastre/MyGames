package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {

    private EditText actUser;
    private EditText modPass;
    private EditText modPass2;
    private Button modify;
    private Boolean passVisible1 = false;
    private Boolean passVisible2 = false;
    private DBHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mDB = new DBHelper(this);
        actUser = (EditText) findViewById(R.id.act_username);
        modPass = (EditText) findViewById(R.id.mod_password);
        modPass2 = (EditText) findViewById(R.id.mod_password2);
        modify = (Button) findViewById(R.id.modify);
        modPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passVisible1) {
                    modPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passVisible1 = false;
                    modPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                } else {
                    modPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passVisible1 = true;
                    modPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                }
            }
        });
        modPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passVisible2) {
                    modPass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passVisible2 = false;
                    modPass2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                } else {
                    modPass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passVisible2 = true;
                    modPass2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                }
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actUser.getText().toString().isEmpty() ||
                        modPass.getText().toString().isEmpty() ||
                        modPass2.getText().toString().isEmpty()) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChangePassword.this);
                    builder.setMessage("No change has been made.")
                            .setTitle("NO CHANGE");
                    builder.create().show();

                } else if (!modPass.getText().toString().equals(modPass2.getText().toString())) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChangePassword.this);
                    builder.setMessage("Passwords must be equals.")
                            .setTitle("PASSWORD ERROR");
                    builder.create().show();
                } else {
                    if (!searchSettDB()) {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage("User does not exists")
                                .setTitle("ERROR USER");
                        builder.create().show();
                    } else {
                        mDB.updateUser(actUser.getText().toString(), modPass.getText().toString());
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage("Password change successfully")
                                .setTitle("PASSWORD");
                        builder.create().show();
                    }
                }
            }
        });
    }

    private boolean searchSettDB() {
        String user = actUser.getText().toString();
        Cursor cursor = mDB.search(user);
        if (cursor != null & cursor.getCount() > 0) {
            cursor.moveToFirst();
            int index;
            String result;
            do {
                index = cursor.getColumnIndex(DBHelper.KEY_USER);
                result = cursor.getString(index);
                if (result.equals(user)) {
                    return true;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return false;
    }
}