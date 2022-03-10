package com.example.my2048game.Games;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my2048game.R;
import com.example.my2048game.User.Settings;
import com.example.my2048game.Utils.DBHelper;

public class MenuJuegos extends AppCompatActivity {
    public static String user;
    public DBHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDB = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        AlertDialog.Builder builder = new AlertDialog.Builder (MenuJuegos.this);
        ListView menuList = (ListView) findViewById(R.id.listView);
        String[] items = {
                getResources().getString(R.string.menu_juego_2048),
                getResources().getString(R.string.menu_juego_peg),
                getResources().getString(R.string.menu_item_settings)};

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                R.layout.style_item, items);

        menuList.setAdapter(adapt);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(
                        R.string.menu_juego_2048))) {
                    startActivity(new Intent(MenuJuegos.this,
                            Main2048.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_juego_peg))) {
                // Launch the Help Activity
                startActivity(new Intent(MenuJuegos.this,
                        MainPegSolitaire.class));
            } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_item_settings))) {
                // Launch the Settings Activity
                startActivity(new Intent(MenuJuegos.this,
                        Settings.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MenuJuegos.this);
        builder.setMessage("Are you sure you want to close the app?")
                .setTitle("CLOSE APP")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}