
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by banbara23 on 15/09/20.
 */
public class StatusListAdapter extends ArrayAdapter<String> {
    public StatusListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
        }
        else {

        }
        return convertView;
    }
}
