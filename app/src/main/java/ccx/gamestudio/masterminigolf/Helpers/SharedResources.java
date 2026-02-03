package ccx.gamestudio.masterminigolf.Helpers;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.time.LocalDate;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.R;

public class SharedResources {
    // ====================================================
    // ACCESS TO SHARED RESOURCES
    // ====================================================
    public static final String SHARED_WINDOWS_WIDTH_INCHES="windowswidthinches";
    public static final String SHARED_PREFS_ACTIVITY_START_COUNT = "count";
    public static final String SHARED_PREFS_ACTIVITY_START_USER = "countloadactivyty";
    public static final String SHARED_PREFS_MAIN = "MasterGolfSettings";
    public static final String SHARED_PREFS_LEVEL_STARS = "level.stars";
    public static final String SHARED_PREFS_LEVEL_HIGHSCORE = "level.highscore";
    public static final String SHARED_PREFS_HIGHSCORE = "highscore";
    public static final String SHARED_PREFS_ENEMIES_TOTALS_DEFEATED = "level.enemiesdefeated";
    public static final String SHARED_PREFS_LEVEL_MAX_REACHED = "levels.reached";
    public static final String SHARED_PREFS_WORLD_MAX_REACHED = "world.reached";
    public static final String SHARED_PREFS_WORLD_SELECTED = "world.selected";
    public static final String SHARED_PREFS_MUSIC_MUTED = "mute.music";
    public static final String SHARED_PREFS_SOUNDS_MUTED = "mute.sounds";
    public static final String SHARED_PREFS_MUSIC_VOLUMEN = "volumenmusic";
    public static final String SHARED_PREFS_MUSIC_GAME_VOLUMEN = "volumenmusic";
    public static final String SHARED_PREFS_SOUNDS_ENGINE_TANK = "soundenginetank";
    public static final String SHARED_PREFS_WEAPONSELECT = "weapondselect";
    public static final String SHARED_PREFS_GEMS = "gems";
    public static final String SHARED_PREFS_COINS = "coins";
    public static final String SHARED_DATE = "date";
    public static final String SHARED_COUNT_DATE = "countdate";
    public static final String SHARED_DAY_OFF = "dayoff";
    public static final String SHARED_PREFS_ACTIVITY_START_COUNT_RATING = "countrating";
    public static final String SHARED_PREFS_RATING_SUCCESS = "rating";
    public static final String SHARED_PREFS_VERSION_CODE = "version";
    public static final String SHARED_PREFS_DESAFIO_CHECK = "desafiocheck";
    public static final String SHARED_PREFS_CONTRARELOJ_CHECK = "contrarelojcheck";
    public static final String SHARED_PREFS_PRACTICA_CHECK = "practicacheck";
    public static final float SCALE_BACKGROUND_MENU = 1.07f;

    private static int selectedPlayer = 0;

    public static int writeIntToSharedPreferences(final String pStr, final int pValue) {
        ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).edit().putInt(pStr, pValue).apply();
        return pValue;
    }

    public static int getIntFromSharedPreferences(final String pStr) {
        return ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).getInt(pStr, 0);
    }

    public static Float getFloatFromSharedPreferences(final String pStr) {
        return ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).getFloat(pStr, 0.0f);
    }

    public static Float writeFloatToSharedPreferences(final String pStr, final Float pValue) {
        ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).edit().putFloat(pStr, pValue).apply();
        return pValue;
    }

    public static void writeBooleanToSharedPreferences(final String pStr, final boolean pValue) {
        ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).edit().putBoolean(pStr, pValue).apply();
    }

    public static boolean getBooleanFromSharedPreferences(final String pStr) {
        return ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).getBoolean(pStr, false);
    }

    public static int getLevelStars(final int pWorld, final int pLevelNumber) {
        return getIntFromSharedPreferences(SHARED_PREFS_LEVEL_STARS + pWorld + pLevelNumber );
    }

    public static int getTotalsCoins() {
        return getIntFromSharedPreferences(SHARED_PREFS_COINS);
    }
    public static int getTotalsGems() {
        return getIntFromSharedPreferences(SHARED_PREFS_GEMS);
    }

    public static void writeStringToSharedPreferences(final String pStr, final String pValue) {
        ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).edit().putString(pStr, pValue).apply();
    }

    public static String getStringFromSharedPreferences(final String pStr) {
        return ResourceManager.getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0).getString(pStr, "0");
    }

    public static void ResetCountGif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!LocalDate.now().toString().equals(SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_DATE))) {
                SharedResources.writeStringToSharedPreferences(SharedResources.SHARED_DATE, LocalDate.now().toString());
                ResetCount();
            }
        }
    }

    private static void ResetCount() {
        if (SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_COUNT_DATE) >= 12) {
            SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_COUNT_DATE, 1);
            SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_DAY_OFF, 0);
        } else {
            SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_COUNT_DATE, SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_COUNT_DATE) + 1);
            SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_DAY_OFF, 0);
        }
    }


    public static Spanned getSpanned() {
        String htmlMessage =  ResourceManager.getContext().getText(R.string.app_detailsApp) +
                "<br><br><b>" + ResourceManager.getContext().getText(R.string.app_productions) + "</b><br>Facundo Javier Immerso." +
                "<br><br><b><u>" + ResourceManager.getContext().getText(R.string.app_contactinformation) + "</u></b>" +
                "<br><a href=\"mailto:facuimmerso@gmail.com\">Email CCX Game Studio</a>" +
                "<br><br><b><u>" + ResourceManager.getContext().getText(R.string.app_sound_title) + "</u></b><br>" + ResourceManager.getContext().getText(R.string.app_sound) +"<br>"+
                "Crowd clapping.mp3 by CosminPa <a href=\"https://freesound.org/s/445284/\">clapping</a> -- License: Creative Commons 0";

        return Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY);
    }



    public static void setSelectedPlayer(int index) {
        selectedPlayer = index;
    }

    public static int getSelectedPlayer() {
        return selectedPlayer;
    }

}
