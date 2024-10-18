package com.czertilla.project_vinaigrette.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Bundle extends I18NBundle{
    static private I18NBundle instance;

    static final FileHandle baseFileHandle = Gdx.files.internal("strings/strings");

    public static I18NBundle getInstance() {
        if (instance == null)
            new Bundle();
        return instance;
    }

    private Bundle(){
        instance = I18NBundle.createBundle(baseFileHandle);
    }
}
