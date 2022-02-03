package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuJuegos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView menuList = (ListView) findViewById(R.id.listView);

        String[] items = {
                getResources().getString(R.string.menu_juego_2048),
                getResources().getString(R.string.menu_juego_peg),
                getResources().getString(R.string.menu_juego_tictactoe)};

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
                            MainActivity.class));

                } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_juego_peg))) {
// Launch the Help Activity
                startActivity(new Intent(MenuJuegos.this,
                        MainActivity.class));
            } /*else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_item_settings))) {
// Launch the Settings Activity
                startActivity(new Intent(MainActivity.this,
                        SettingsActivity.class));
            } else if (strText.equalsIgnoreCase(getResources().getString(
                    R.string.menu_item_scores))) {
// Launch the Scores Activity
                startActivity(new Intent(MainActivity.this,
                        ScoresActivity.class));
            }

                 */
            }
        });
    }
}