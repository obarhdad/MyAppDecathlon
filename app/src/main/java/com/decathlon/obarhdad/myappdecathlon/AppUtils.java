package com.decathlon.obarhdad.myappdecathlon;

import android.content.Context;


public class AppUtils {
    public static void saveLogout(Context context, boolean logout) {
        PreferencesUtil.writeBoolean(context, "logout", logout);
    }

    public static boolean getLogout(Context context) {
        return PreferencesUtil.readBooleanFalse(context, "logout");
    }


    public static void saveNameStore(Context context, String nameStore) {
        PreferencesUtil.writeString(context, "nameStore", nameStore);
    }

    public static String getNameStore(Context context) {
        return PreferencesUtil.readString(context, "nameStore");
    }

    public static void saveName(Context context, String name) {
        PreferencesUtil.writeString(context, "name", name);
    }

    public static String getName(Context context) {
        return PreferencesUtil.readString(context, "name");
    }
}
