package com.decathlon.obarhdad.myappdecathlon;

import java.util.HashMap;
import java.util.Map;

public class Person {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getFavoriteStore() {
        return favoriteStore;
    }

    public void setFavoriteStore(String favoriteStore) {
        this.favoriteStore = favoriteStore;
    }

    private String favoriteStore;


    public Person() {
        /*Blank default constructor essential for Firebase*/
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("password", password);
        result.put("favoriteStore", favoriteStore);
        return result;
    }
}
