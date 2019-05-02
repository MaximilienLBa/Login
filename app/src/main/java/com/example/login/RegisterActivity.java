package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextMail;
    EditText mTextAge;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextCnfPassword = (EditText) findViewById(R.id.edittext_cnf_password);
        mTextAge =(EditText) findViewById(R.id.edittext_age);
        mTextMail =(EditText) findViewById(R.id.edittext_email);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String age = mTextAge.getText().toString().trim();
                String mail = mTextMail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                if (user.equals("") || pwd.equals("")|| age.equals("") || mail.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Registeration Error - field empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (pwd.equals(cnf_pwd)) {
                        Boolean checkusername = db.checkUsername(user);
                        Boolean checkMail = db.checkEmail(mail);
                        if (checkusername == true && checkMail == true){
                            Toast.makeText(RegisterActivity.this, "Username and mail already exist", Toast.LENGTH_SHORT).show();
                        }
                        else if (checkusername == true) {
                            Toast.makeText(RegisterActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkMail == true){
                            Toast.makeText(RegisterActivity.this, "mail already exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(mail.contains("@") == false && mail.contains(".") == false){
                            Toast.makeText(RegisterActivity.this, "Wrong email", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            long val = db.addUser(user, pwd,age,mail);
                            if (val > 0) {
                                Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                                Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(moveToLogin);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}