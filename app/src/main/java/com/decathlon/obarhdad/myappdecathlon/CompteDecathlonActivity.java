package com.decathlon.obarhdad.myappdecathlon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by obarhdad on 08/08/2016.
 */
public class CompteDecathlonActivity extends Activity {
    TextView pId, pDirector, pCountry, pName, pAdress, tNameHello, tPreferedStore;
    Stores store;
    String nameStore;
    String name;
    ArrayList<Stores> dataSets;
    ArrayList<String> data;
    private DatabaseReference mDatabase;
    int pos;
    Button bLogout, bChangeStore;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_decathlon_activity);
        dataSets = new ArrayList<Stores>();
        Stores.initArrayMPA(DecathlonStoresActivity.loadJSONFromAsset(this));

        dataSets = Stores.getStoresListAdapterHistory();

        tNameHello = (TextView) findViewById(R.id.tNameHello);
        tPreferedStore = (TextView) findViewById(R.id.tPreferedStore);
        pId = (TextView) findViewById(R.id.tId);
        pDirector = (TextView) findViewById(R.id.tDirector);
        pCountry = (TextView) findViewById(R.id.tCountry);
        pName = (TextView) findViewById(R.id.tName);
        pAdress = (TextView) findViewById(R.id.tAdress);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("nameStore"))
            nameStore = getIntent().getExtras().getString("nameStore");
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("name"))
            name = getIntent().getExtras().getString("name");

        AppUtils.saveName(this, name);
        AppUtils.saveNameStore(this, nameStore);
        AppUtils.saveLogout(this, true);

        store = Stores.getStoresAdapterHistory(nameStore);

        pId.setText(store.getId());
        pDirector.setText(store.getDirectorLastName());
        pCountry.setText(store.getCountry());
        pName.setText(store.getName());
        tPreferedStore.setText(store.getName());
        pAdress.setText(store.getAddress());
        tNameHello.setText(name);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.saveLogout(CompteDecathlonActivity.this, false);
                Intent intent = new Intent(CompteDecathlonActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bChangeStore = (Button) findViewById(R.id.bChangeStore);
        bChangeStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogView();
            }
        });
    }

    private void AlertDialogView() {
        data = Stores.getNameStoresListAdapterHistory();

        final CharSequence[] items = data.toArray(new CharSequence[data.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(CompteDecathlonActivity.this);
        builder.setTitle(this.getString(R.string.store_decathlon));
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), items[item],
                                Toast.LENGTH_SHORT).show();
                        pos = item;
                    }
                });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AppUtils.saveNameStore(CompteDecathlonActivity.this, items[pos].toString());
                pName.setText(items[pos].toString());
                tPreferedStore.setText(items[pos].toString());

                mDatabase = FirebaseDatabase.getInstance().getReference();
                Query pendingTasks = mDatabase.child("email").equalTo(store.getAddress());
                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {

                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                            snapshot.getRef().child("favoriteStore").setValue(items[pos]);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());

                    }

                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
