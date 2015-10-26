
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by banbara23 on 15/10/25.
 */
public class Loading {
    Context mContext;
    ProgressDialog mProgressDialog;

    public Loading(Context context) {
        mContext = context;
        mProgressDialog = new ProgressDialog(context);
    }

    public void show() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.view_loading);
        mProgressDialog.setCancelable(false);
    }

    public void close() {
        mProgressDialog.dismiss();
    }
}
