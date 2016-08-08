package com.decathlon.obarhdad.myappdecathlon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by obarhdad on 07/08/2016.
 */
public class LoginActivity extends Activity {


    static final String TAG = "Main Activity";
    private DatabaseReference mDatabase;
    private static ArrayList<Person> arrayListPerson = new ArrayList<>();
    EditText pEmail, pPassword;
    Button bLogin, bCreateAccount;
    ProgressDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pEmail = (EditText) findViewById(R.id.pEmail);
        pPassword = (EditText) findViewById(R.id.pPassword);


        bLogin = (Button) findViewById(R.id.bLogin);
        bCreateAccount = (Button) findViewById(R.id.bCreateAccount);

        bCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pPassword.getText().toString().trim().equals("") || pEmail.getText().toString().trim().equals("") || !CreateAccountActivity.isEmailValid(pEmail.getText().toString())) {
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.addChildEventListener(childEventListener);
                    new Wait().execute();
                } else
                    displayAlertDialog();
            }
        });
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Person person = dataSnapshot.getValue(Person.class);
            arrayListPerson.add(person);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), "Could not update.", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        arrayListPerson.clear();
    }

    private class Wait extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getDialogVisibility(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                Log.d(TAG, ie.toString());
            }
            return (arrayListPerson.size() == 0);
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            boolean find = false;
            for (int i = 0; i < arrayListPerson.size(); i++) {
                Log.e("test test", "test test:" + arrayListPerson.get(i).getEmail());
                if (arrayListPerson.get(i).getPassword().equals(pPassword.getText().toString()) && arrayListPerson.get(i).getEmail().equals(pEmail.getText().toString())) {
                    getDialogVisibility(false);
                    find = true;
                    Intent intent = new Intent(LoginActivity.this, CompteDecathlonActivity.class);
                    intent.putExtra("nameStore", arrayListPerson.get(i).getFavoriteStore());
                    intent.putExtra("name", arrayListPerson.get(i).getFirstName() + " " + arrayListPerson.get(i).getLastName());
                    startActivity(intent);
                    finish();
                }
            }
            getDialogVisibility(false);
            if (!find) {
                displayAlertDialog();
            }
        }
    }

    public void displayAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
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

    public void getDialogVisibility(boolean visibility) {
        if (visibility) {
            dlg = new ProgressDialog(LoginActivity.this);

            CharSequence message = "Please Wait";

            dlg.setMessage(message);
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setCanceledOnTouchOutside(false);
            dlg.setCancelable(false);
            dlg.show();
        } else {
            dlg.dismiss();
        }
    }
}

