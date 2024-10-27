package com.czertilla.project_vinaigrette.utils;

public class Ammo {
    private int
        pistolAmmo,
        rifleAmmo,
        shotgunAmmo;

    public Ammo() {
        pistolAmmo = 0;
        rifleAmmo = 0;
        shotgunAmmo = 0;
    }

    public Ammo(int pistolAmmo, int rifleAmmo, int shotgunAmmo) {
        this.pistolAmmo = pistolAmmo;
        this.rifleAmmo = rifleAmmo;
        this.shotgunAmmo = shotgunAmmo;
    }

    public void setPistolAmmo(int pistolAmmo) {
        this.pistolAmmo = pistolAmmo;
    }

    public void setRifleAmmo(int rifleAmmo) {
        this.rifleAmmo = rifleAmmo;
    }

    public void setShotgunAmmo(int shotgunAmmo) {
        this.shotgunAmmo = shotgunAmmo;
    }

    public void addPistolAmmo(int number) {
        this.pistolAmmo += number;
    }

    public void addRifleAmmo(int number) {
        this.rifleAmmo += number;
    }

    public void addShotgunAmmo(int number) {
        this.shotgunAmmo += number;
    }

    public int getPistolAmmo() {
        return pistolAmmo;
    }

    public int getRifleAmmo() {
        return rifleAmmo;
    }

    public int getShotgunAmmo() {
        return shotgunAmmo;
    }

    public int getPistolAmmo(int number) {
        pistolAmmo -= number;
        if (pistolAmmo < 0){
            number += pistolAmmo;
            pistolAmmo = 0;
        }
        return number;
    }

    public int getRifleAmmo(int number) {
        rifleAmmo -= number;
        if (rifleAmmo < 0){
            number += rifleAmmo;
            rifleAmmo = 0;
        }
        return number;
    }

    public int getShotgunAmmo(int number) {
        shotgunAmmo -= number;
        if (shotgunAmmo < 0){
            number += shotgunAmmo;
            shotgunAmmo = 0;
        }
        return number;
    }
}
