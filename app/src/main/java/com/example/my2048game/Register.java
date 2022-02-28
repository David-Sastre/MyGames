package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private static final String TAG = Register.class.getSimpleName();
    private DBHelper mDB;
    private static final int NO_ID = -99;
    private static final String NO_WORD = "";

    private EditText mEditUserView;
    private EditText mEditPassword;

    // Unique tag for the intent reply.
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY1 = "com.example.android.wordlistsql.REPLY1";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditUserView = (EditText) findViewById(R.id.UserName);
        mEditPassword = (EditText) findViewById(R.id.TextPassword);
        mDB = new DBHelper(this);

        // Get data sent from calling activity.
        Bundle extras = getIntent().getExtras();
    }

    public void returnReply(View view) {
        String user = mEditUserView.getText().toString();
        String password = mEditPassword.getText().toString();
        searchDB();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, user);
        replyIntent.putExtra(EXTRA_REPLY1, password);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private void searchDB(){
        String user = mEditUserView.getText().toString();
        Cursor cursor = mDB.search(user);
        if (cursor != null & cursor.getCount() > 0) {
            cursor.moveToFirst();
            int index;
            String result;
            do {
                index = cursor.getColumnIndex(DBHelper.KEY_USER);
                result = cursor.getString(index);
                if (result == user){
                    Toast.makeText(getApplicationContext(),
                            R.string.user_used,
                            Toast.LENGTH_LONG).show();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}