package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class CharSet {
    public final static String CHARSET = I18NBundle.createBundle(
        new FileHandle("strings/charset")
    ).get("CHARSET");
}
