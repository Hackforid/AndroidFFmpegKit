package com.smilehacker.exffmpeg;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kleist on 2017/5/16.
 */

public class Config {

    private SharedPreferences mSharedPreferences;
    private final static String CONFIG = "ffmpeg_config";
    private final static String PREF_VERSION = "pref_version";

    private final static int VERSION = 1;

    public Config(Context context) {
        mSharedPreferences = context.getSharedPreferences(CONFIG, 0);
    }

    public boolean isVersionExpired() {
        int version = mSharedPreferences.getInt(PREF_VERSION, 0);
        return version < VERSION;
    }

    public void refreshVersion() {
        mSharedPreferences.edit().putInt(PREF_VERSION, VERSION).apply();
    }
}
