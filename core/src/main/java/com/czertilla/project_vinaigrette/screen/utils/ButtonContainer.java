package com.czertilla.project_vinaigrette.screen.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.List;

public class ButtonContainer {
    private final List<Button> buttons;
    private float
        buttonsWidth,
        buttonsHeight;

    public void setButtonsWidth(float buttonsWidth) {
        this.buttonsWidth = buttonsWidth;
    }

    public void setButtonsHeight(float buttonsHeight) {
        this.buttonsHeight = buttonsHeight;
    }

    public void setButtonsRows(int buttonsRows) {
        this.buttonsRows = buttonsRows;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public float getButtonsWidth() {
        return buttonsWidth;
    }

    public float getButtonsHeight() {
        return buttonsHeight;
    }

    public int getButtonsRows() {
        return buttonsRows;
    }

    private int
        buttonsRows = 1;

    public ButtonContainer(float buttonsWidth, float buttonsHeight) {
        this.buttonsWidth = buttonsWidth;
        this.buttonsHeight = buttonsHeight;
        this.buttons = new ArrayList<>();
    }

    public ButtonContainer(float buttonsWidth, float buttonsHeight, int buttonsRows){
        this(buttonsWidth, buttonsHeight);
        this.buttonsRows = buttonsRows;
    }

    public Button get(int index){
        if (index >= buttons.size() || index < 0) return null;
        return buttons.get(index);
    }

    public void add(Button button){
        buttons.add(button);
    }

    public Button pop(int index) {
        Button button = get(index);
        if (button != null){
            buttons.remove(index);
            return button;
        }
        else return null;
    }
}
