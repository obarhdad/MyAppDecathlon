package com.decathlon.obarhdad.myappdecathlon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by obarhdad on 08/08/2016.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!AppUtils.getLogout(this)){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this, CompteDecathlonActivity.class);
            intent.putExtra("nameStore", AppUtils.getNameStore(this));
            intent.putExtra("name", AppUtils.getName(this));
            startActivity(intent);
            finish();
        }
    }
}
