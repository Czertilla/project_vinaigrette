package com.czertilla.project_vinaigrette.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.R;

import java.util.Arrays;
import java.util.Locale;

public class Bundle extends I18NBundle{
    static private I18NBundle instance;
    static private final String[] langCodes = C.LANG_CODES;
    static private String[] langList;
    static final FileHandle baseFileHandle = Gdx.files.internal(R.path.STRINGS_HANDLE);

    public static I18NBundle getInstance() {
        if (instance == null)
            new Bundle();
        return instance;
    }

    public static String[] getLangList(){
        if (langList == null){
            langList = new String[langCodes.length];
            for (int i=0; i < langCodes.length; i++){
                langList[i] = I18NBundle.createBundle(
                        baseFileHandle,
                        new Locale(langCodes[i])
                ).get("/");
            }
        }
        return langList;
    }

    public static Integer getSelectedLang(){
//        return langList[Arrays.asList(langCodes).indexOf(instance.getLocale().getLanguage())];
        return Arrays.asList(langCodes).indexOf(instance.getLocale().getLanguage());
    }

    public static void setLanguage(int langIndex){
        Locale locale = new Locale(langCodes[langIndex]);
        instance = I18NBundle.createBundle(baseFileHandle, locale);
    }

    private Bundle(){
        instance = I18NBundle.createBundle(baseFileHandle, new Locale(langCodes[0]));
    }
}
