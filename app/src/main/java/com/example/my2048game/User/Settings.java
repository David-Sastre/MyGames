package com.example.my2048game.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2048game.Games.MenuJuegos;
import com.example.my2048game.R;
import com.example.my2048game.Games.Splash;
import com.example.my2048game.Utils.DBHelper;

public class Settings extends AppCompatActivity {
    private String user;
    private TextView username;
    private Button btn_delete;
    private Button btn_change;
    private Button btn_logout;
    private Button btn_exit;
    private DBHelper mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mDB = new DBHelper(this);
        user = MenuJuegos.user;
        this.username = (TextView) findViewById(R.id.hellouser);
        this.btn_delete = (Button) findViewById(R.id.btnDelete);
        this.btn_change = (Button) findViewById(R.id.change_password);
        this.btn_logout = (Button) findViewById(R.id.logout);
        this.btn_exit = (Button) findViewById(R.id.btnExit);

        username.setText(user);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setMessage("Are you sure you want to log out?")
                        .setTitle("EXIT")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.this, LogIn.class);
                                startActivity(intent);
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
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.equals("admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setMessage("User ADMIN can not be Deleted.")
                            .setTitle("ERROR")
                            .show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setMessage("Do you want to delete this USER?")
                            .setTitle("DELETE")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mDB.deleteUser(user);
                                    Toast.makeText(getApplicationContext(), "Deleting user....", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Settings.this, Splash.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getApplicationContext(), "Cancel....", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
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
        });
    }
}
