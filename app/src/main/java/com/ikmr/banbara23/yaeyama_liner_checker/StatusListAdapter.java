
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusListView;

import java.util.ArrayList;

/**
 * Created by banbara23 on 15/09/20.
 */
public class StatusListAdapter extends ArrayAdapter<String> {

    public StatusListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatusListView statusListView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            statusListView = (StatusListView) inflater.inflate(
                    R.layout.fragment_status_list_view, parent, false);
        }
        else {
            statusListView = (StatusListView) convertView;
        }
        statusListView.bind(getItem(position));
        return statusListView;
    }
}
