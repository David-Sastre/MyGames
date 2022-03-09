package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainPegSolitaire extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_peg_solitaire);
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
                    startActivity(new Intent(MainPegSolitaire.this,
                            GamePeg.class));

                } else if (strText.equalsIgnoreCase(getResources().getString(
                        R.string.menu_item_help))) {
                    // Launch the Help Activity
//                    Intent intent = new Intent(MainPegSolitaire.this,
//                            HelperPeg.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
                } else if (strText.equalsIgnoreCase(getResources().getString(
                        R.string.menu_item_settings))) {
                // Launch the Settings Activity
                    Intent intent = new Intent(MainPegSolitaire.this,
                            Settings.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                } else if (strText.equalsIgnoreCase(getResources().getString(
                        R.string.menu_item_scores))) {
                // Launch the Scores Activity
                    Intent intent = new Intent(MainPegSolitaire.this,
                            ScoreGames.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                }
            }
        });
    }
}