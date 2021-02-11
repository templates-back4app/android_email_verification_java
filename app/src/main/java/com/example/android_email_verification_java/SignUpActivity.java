package com.example.android_email_verification_java;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {
    private ImageView back;
    private Button signUp;
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText passwordagain;
    private TextInputEditText email;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(SignUpActivity.this);

        back = findViewById(R.id.back);
        signUp = findViewById(R.id.signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordagain = findViewById(R.id.passwordagain);
        email = findViewById(R.id.email);

        signUp.setOnClickListener(v -> {
            if (password.getText().toString().equals(passwordagain.getText().toString()) && !TextUtils.isEmpty(username.getText().toString()))
                signUp(username.getText().toString(), password.getText().toString(), email.getText().toString());
            else
                Toast.makeText(this, "Make sure that the values you entered are correct.", Toast.LENGTH_SHORT).show();
        });

        back.setOnClickListener(v -> finish());
    }


    private void signUp(String username, String password, String email) {
        progressDialog.show();
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
                ParseUser.logOut();
                showAlert("Account Created Successfully!", "Please verify your email before Login", false);
            } else {
                ParseUser.logOut();
                showAlert("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true);
            }
        });
    }


    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


}