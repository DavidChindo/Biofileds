package com.hics.biofields.Library;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hics.biofields.R;

import org.aviran.cookiebar2.CookieBar;


/**
 * Created by david.barrera on 8/31/17.
 */

public class DesignUtils {

    public static void errorMessage(Activity activity, String title, String message){
        CookieBar.Build(activity)
                .setTitle(title)
                .setMessage(message)
                .setBackgroundColor(R.color.red)
                .setTitleColor(R.color.colorWhite)
                .show();
    }

    public static void errorMessage(Activity activity, String title, int message){
        CookieBar.Build(activity)
                .setTitle(title)
                .setMessage(message)
                .setBackgroundColor(R.color.red)
                .setTitleColor(R.color.colorWhite)
                .show();
    }

    public static void warningMessage(Activity activity, String title, String message){
        CookieBar.Build(activity)
                .setTitle(title)
                .setMessage(message)
                .setBackgroundColor(R.color.orange)
                .setTitleColor(R.color.colorWhite)
                .show();
    }

    public static void setListViewHeightBasedOnChildrenAdapter(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
