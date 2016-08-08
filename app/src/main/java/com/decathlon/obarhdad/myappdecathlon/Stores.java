package com.decathlon.obarhdad.myappdecathlon;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by obarhdad on 07/08/2016.
 */
public class Stores {
    private String name;
    private String id;
    private String directorLastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectorLastName() {
        return directorLastName;
    }

    public void setDirectorLastName(String directorLastName) {
        this.directorLastName = directorLastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String country;
    private String address;
    private boolean favorite;

    private static Stores StoresListAdapterInstance;
    private static ArrayList<Stores> dataSets = new ArrayList<Stores>();

    public Stores() {

    }

    public Stores(JSONObject item) {
        try {
            this.name = item.getString("name");
            this.id = item.getString("id");
            this.directorLastName = item.getString("directorLastName");
            this.country = item.getString("country");
            this.address = item.getString("address");

            Log.e("tag", "name:" + this.name + "|id:" + this.id + "|directorLastName:" + directorLastName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initArrayMPA(String res) {
        JSONObject o;
        try {
            o = new JSONObject(res);
            JSONArray data = o.getJSONObject("data").getJSONArray("stores");
            getStoresListAdapter().setJSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setJSONArray(JSONArray table) {
        try {
            dataSets = new ArrayList<Stores>();
            for (int i = 0; i < table.length(); i++) {
                Stores model = new Stores(table.getJSONObject(i));
                dataSets.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Stores getStoresListAdapter() {
        if (StoresListAdapterInstance == null) {
            StoresListAdapterInstance = new Stores();
        }
        return StoresListAdapterInstance;
    }

    public static ArrayList<Stores> getStoresListAdapterHistory() {
        return dataSets;
    }

    public static ArrayList<String> getNameStoresListAdapterHistory() {
        ArrayList<String> nameStores = new ArrayList<>();
        for (int i = 0; i < dataSets.size(); i++)
            nameStores.add(dataSets.get(i).name);
        return nameStores;
    }

    public static Stores getStoresAdapterHistory(String name) {
        Stores store = new Stores();
        for (int i = 0; i < dataSets.size(); i++)
            if(name.equals(dataSets.get(i).name))
               store=dataSets.get(i);
        return store;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
