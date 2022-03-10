package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Main2048 extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2048);
        ListView menuList = (ListView) findViewById(R.id.listView);
        username = MenuJuegos.user;

        String[] items = {
                getResources().getString(R.string.menu_item_play),
                getResources().getString(R.string.menu_item_scores),
                getResources().getString(R.string.menu_item_settings),
                getResources().getString(R.string.menu_item_help) };

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                R.layout.style_item, items);

        menuList.setAdapter(adapt);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(
                        R.string.menu_item_play))) {
                    Intent intent = new Intent(Main2048.this,
                            Game2048.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                    finish();
                } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_item_help))) {
                // Launch the Help Activity
//                startActivity(new Intent(Main2048.this,
//                        Help2048.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_item_settings))) {
                // Launch the Settings Activity
                    Intent intent = new Intent(Main2048.this,
                            Settings.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                    finish();
                } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_item_scores))) {
                // Launch the Scores Activity
                    Intent intent = new Intent(Main2048.this,
                            ScoreGames.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
