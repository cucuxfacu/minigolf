package ccx.gamestudio.masterminigolf.Manager;

import ccx.gamestudio.masterminigolf.Helpers.SharedResources;

public class GrowToggleManager {
    private static final GrowToggleManager INSTANCE = new GrowToggleManager();

    // ====================================================
    // INSTANCE GETTER
    // ====================================================
    public static GrowToggleManager getInstance(){
        return INSTANCE;
    }

    // ====================================================
    // VARIABLES
    // ====================================================
    public boolean mBtnDesafio;
    public boolean mBtnContraReloj;
    public boolean mBtnPractica;

    public static boolean isBtnDesafioCheck() {
        return getInstance().mBtnDesafio;
    }
    public static boolean isBtnContrarelojCheck() {
        return getInstance().mBtnContraReloj;
    }
    public static boolean isBtnPracticaCheck() {
        return getInstance().mBtnPractica;
    }

    public static boolean toggleDesafioClick() {
        getInstance().mBtnDesafio = !getInstance().mBtnDesafio;
        SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_DESAFIO_CHECK, (getInstance().mBtnDesafio ? 1 : 0));
        return getInstance().mBtnDesafio;
    }
    public static boolean toggleContraRelojClick() {
        getInstance().mBtnContraReloj = !getInstance().mBtnContraReloj;
        SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_CONTRARELOJ_CHECK, (getInstance().mBtnContraReloj ? 1 : 0));
        return getInstance().mBtnContraReloj;
    }
    public static boolean togglePracticaClick() {
        getInstance().mBtnPractica = !getInstance().mBtnPractica;
        SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_PRACTICA_CHECK, (getInstance().mBtnPractica ? 1 : 0));
        return getInstance().mBtnPractica;
    }
}
