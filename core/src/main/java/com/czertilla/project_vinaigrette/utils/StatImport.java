package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class StatImport {
    private final static FileHandle
        firearmHandler = new FileHandle(R.path.FIREARM_STATS_HANDLE),
        whitearmHandler = new FileHandle(R.path.WHITEARM_STATS_HANDLE);
    public static I18NBundle getFireArm(String id){
        return I18NBundle.createBundle(firearmHandler, new Locale(id));
    }
    public static I18NBundle getWhiteArm(String id){
        return I18NBundle.createBundle(whitearmHandler, new Locale(id));
    }
}
