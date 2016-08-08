package com.decathlon.obarhdad.myappdecathlon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by obarhdad on 07/08/2016.
 */
public class CreateAccountActivity extends Activity {


    Button bNext;
    EditText pLastName, pEmail, pFirstName, pConfirmPassword, pPassword;
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        pLastName = (EditText) findViewById(R.id.pLastName);
        pEmail = (EditText) findViewById(R.id.pEmail);
        pFirstName = (EditText) findViewById(R.id.pFirstName);
        pPassword = (EditText) findViewById(R.id.pPassword);
        pConfirmPassword = (EditText) findViewById(R.id.pConfirmPassword);

        bNext = (Button) findViewById(R.id.bNext);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pFirstName.getText().toString().trim().equals("") || pEmail.getText().toString().trim().equals("") || !isEmailValid(pEmail.getText().toString())
                 || !pPassword.getText().toString().trim().equals(pConfirmPassword.getText().toString())|| pLastName.getText().toString().trim().equals("") || pPassword.getText().toString().trim().equals("") || pConfirmPassword.getText().toString().trim().equals("")) {
                    pPassword.setText("");
                    pConfirmPassword.setText("");
                    displayAlertDialog();

                } else {
                    Intent intent = new Intent(CreateAccountActivity.this, DecathlonStoresActivity.class);
                    intent.putExtra("firstName", pFirstName.getText().toString());
                    intent.putExtra("lastName", pEmail.getText().toString());
                    intent.putExtra("email", pLastName.getText().toString());
                    intent.putExtra("password", pPassword.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    public static boolean isEmailValid(final String email) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public void displayAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Erreur");
        alert.setMessage("Veuillez vérifier vos informations");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
