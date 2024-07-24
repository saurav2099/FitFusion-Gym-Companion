package com.example.fitfusion.helpers;

import android.view.View;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;

public class toastHelper {

    public static void showLongMessageSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), android.R.color.white));
        snackbar.show();
    }
}
