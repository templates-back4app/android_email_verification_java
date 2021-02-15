package com.example.android_email_verification_java;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private Button navigatesignup;
    private Button login;
    private TextInputEditText password;
    private TextInputEditText username;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);

        navigatesignup = findViewById(R.id.navigatesignup);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);

        login.setOnClickListener(v -> login(username.getText().toString(), password.getText().toString()));

        navigatesignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private void login(String username, String password) {
        progressDialog.show();
        ParseUser.logInInBackground(username, password, (parseUser, e) -> {
            progressDialog.dismiss();
            if (parseUser != null) {
                showAlert("Login Successful", "Welcome, " + username + "!", false);
            } else {
                ParseUser.logOut();
                showAlert("Login Fail", e.getMessage() + " Please try again", true);
            }
        });
    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        if (!error) {
                            Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}