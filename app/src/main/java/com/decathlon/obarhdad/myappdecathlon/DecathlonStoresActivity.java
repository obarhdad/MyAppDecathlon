package com.decathlon.obarhdad.myappdecathlon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by obarhdad on 07/08/2016.
 */
public class DecathlonStoresActivity extends Activity {
    ArrayList<Stores> dataSets;
    MyAdapter adapter;
    Button bCreateMyAccount;
    int pos;
    private DatabaseReference mDatabase;
    String firstName, lastName, password, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_decathlon_account);
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("firstName"))
            firstName = getIntent().getExtras().getString("firstName");
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("lastName"))
            lastName = getIntent().getExtras().getString("lastName");
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("password"))
            password = getIntent().getExtras().getString("password");
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("email"))
            email = getIntent().getExtras().getString("email");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        dataSets = new ArrayList<Stores>();
        bCreateMyAccount = (Button) findViewById(R.id.bCreateMyAccount);

        Stores.initArrayMPA(loadJSONFromAsset(this));

        final ListView lvDetail = (ListView) findViewById(R.id.listview);

        dataSets = Stores.getStoresListAdapterHistory();

        adapter = new MyAdapter(this, dataSets);
        lvDetail.setAdapter(adapter);

        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < dataSets.size(); i++) {
                    if (i == position)
                        dataSets.get(i).setFavorite(true);
                    else
                        dataSets.get(i).setFavorite(false);

                }
                pos = position;
                adapter.notifyDataSetChanged();
            }
        });
        bCreateMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != 0) {
                    Person p = new Person();
                    p.setFirstName(firstName);
                    p.setEmail(email);
                    p.setLastName(lastName);
                    p.setPassword(password);
                    p.setFavoriteStore(dataSets.get(pos).getName());
                    addPerson(p);

                    Intent intent = new Intent(DecathlonStoresActivity.this, CompteDecathlonActivity.class);
                    intent.putExtra("nameStore", dataSets.get(pos).getName());
                    intent.putExtra("name", firstName + " " + lastName);
                    startActivity(intent);
                }
            }
        });
    }

    public void addPerson(Person model) {
        Person person = new Person();
        person.setFirstName(model.getFirstName());
        person.setEmail(model.getEmail());
        person.setLastName(model.getLastName());
        person.setPassword(model.getPassword());
        person.setFavoriteStore(model.getFavoriteStore());

        String key = mDatabase.child("Persons").push().getKey();
        Map<String, Object> postValues = person.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("stores.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
