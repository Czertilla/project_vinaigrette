package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class R {
    public static class path {
        private static final I18NBundle bundle = I18NBundle.createBundle(
            new FileHandle("strings/paths")
        );

        public static final String
            LAYOUT_HANDLE = bundle.get("LAYOUT_HANDLE"),
            VIDEO_HANDLE = bundle.get("VIDEO_HANDLE"),
            MAIN_MENU_BACKGROUND = bundle.get("MAIN_MENU_BACKGROUND"),
            BUTTON_DRAWABLE_REGION = bundle.get("BUTTON_DRAWABLE_REGION"),
            PIXEL_FONT = bundle.get("PIXEL_FONT"),
            STRINGS_HANDLE = bundle.get("STRINGS_HANDLE"),
            CHARSET_HANDLE = bundle.get("CHARSET_HANDLE"),
            PATHS_HANDLE = bundle.get("PATHS_HANDLE"),
            CLICK_SOUND = bundle.get("CLICK_SOUND");
    }

    public static final class id {
        public static final String
            CONTINUE = "BUTTONS/MENU/CONTINUE",
            NEW_GAME = "BUTTONS/MENU/NEW_GAME",
            SETTINGS = "BUTTONS/MENU/SETTINGS",
            BACK = "BUTTONS/MENU/BACK",
            QUIT = "BUTTONS/MENU/QUIT",
            VIDEO = "BUTTONS/MENU/VIDEO",
            DEBUG = "BUTTONS/MENU/DEBUG",
            FULLSCREEN = "BUTTONS/MENU/FULLSCREEN",
            SWITCH_ON = "BUTTONS/MENU/SWITCH/ON",
            SWITCH_OFF = "BUTTONS/MENU/SWITCH/OFF",
            SCALE = "BUTTONS/MENU/SCALE",
            CHARSET = "CHARSET",
            LANG_CODES = "languages",
            FRAMES_LOCK = "FRAMES_LOCK",
            SCALE_ARRAY = "SCALE_ARRAY",
            MENU_BUTTONS_WIDTH = "MENU_BUTTONS_WIDTH",
            MENU_BUTTONS_HEIGHT = "MENU_BUTTONS_HEIGHT",
            FONT_SIZE = "FONT_SIZE",
            DEFAULT_WIDTH = "DEFAULT_WIDTH",
            DEFAULT_HEIGHT = "DEFAULT_HEIGHT";
    }
}
