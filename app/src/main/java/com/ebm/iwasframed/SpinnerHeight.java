package com.ebm.iwasframed;

import android.widget.Spinner;

import java.lang.reflect.Field;

/**
 * Created by Usman on 2/12/2018.
 */

public class SpinnerHeight {


    public static void setHeight(Spinner spinner){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(650);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
    }
}
