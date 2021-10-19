package com.radhikakhewalkarr.pingme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;

    // Firebase instance variables
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmailView = (EditText) findViewById(R.id.registerEmail);
        mPasswordView = (EditText) findViewById(R.id.registerpassword);
        mConfirmPasswordView = (EditText) findViewById(R.id.registerconfirmpassword);
        mUsernameView = (EditText) findViewById(R.id.registername);

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 200 || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }

    public void registrationButton(View view) {

        attemptRegistration();
    }


        private void attemptRegistration () {

            // Reset errors displayed in the form.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                mPasswordView.setError("Password too short or does not match");
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError("This field is required");
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError("This email is invalid");
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // TODO: Call create FirebaseUser() here
                createFirebaseUser();

            }
        }


    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassword = mConfirmPasswordView.getText().toString();
        return confirmPassword.equals(password) && password.length() > 7;
    }

    // TODO: Create a Firebase user
    private void createFirebaseUser() {
        String email = mEmailView.getText().toString();
        String password = mConfirmPasswordView.getText().toString();
//        String emailOne = new String();
//        String passwordOne = new String();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {


                if (!task.isSuccessful()) {
                    showAlertDialog("Registration attempt failed");

                } else {
                    saveUsername();
                    Intent nextScreen = new Intent(SignUpActivity.this, LoginScreen.class);
                    finish();
                    startActivity(nextScreen);
                }
            }





        });
    }
//         TODO: Save the display name to Shared Preferences
        private void saveUsername()
        {
            String Username = mUsernameView.getText().toString();
            SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
            prefs.edit().putString(DISPLAY_NAME_KEY, Username).apply();

        }


        // TODO: Create an alert dialog to show in case registration failed
       private void showAlertDialog(String message ){
            new AlertDialog.Builder(this)
                    .setTitle("Oops")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

    }

