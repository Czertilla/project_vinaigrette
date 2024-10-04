package com.czertilla.project_vinaigrette.screen.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.List;

public class ButtonContainer {
    private final List<Button> buttons;
    private float
        width,
        height,
        buttonsWidth,
        buttonsHeight,
        buttonsInterval,
        buttonsPadding,
        positionX,
        positionY;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getButtonsInterval() {
        return buttonsInterval;
    }

    public void setButtonsInterval(float buttonsInterval) {
        this.buttonsInterval = buttonsInterval;
    }

    public float getButtonsPadding() {
        return buttonsPadding;
    }

    public void setButtonsPadding(float buttonsPadding) {
        this.buttonsPadding = buttonsPadding;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

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

    public ButtonContainer(
            float positionX,
            float positionY,
            float width,
            float height,
            float buttonsWidth,
            float buttonsHeight
    ) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.buttonsWidth = buttonsWidth;
        this.buttonsHeight = buttonsHeight;
        this.buttonsInterval
            = this.buttonsPadding
            = buttonsHeight/4f;
        this.buttons = new ArrayList<>();
    }

    public ButtonContainer(
        float positionX,
        float positionY,
        float width,
        float height,
        float buttonsWidth,
        float buttonsHeight,
        int buttonsRows
    ) {
        this(positionX,
            positionY,
            width,
            height,
            buttonsWidth,
            buttonsHeight
        );
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
