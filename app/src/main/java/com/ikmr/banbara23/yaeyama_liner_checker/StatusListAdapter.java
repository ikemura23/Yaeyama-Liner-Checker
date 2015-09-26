
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusListView;

/**
 * 一覧アダプター
 */
public class StatusListAdapter extends ArrayAdapter<Liner> {
    ListItemClickListener mListItemClickListener;

    public StatusListAdapter(Context context, Activity activity) {
        super(context, R.layout.fragment_status_list_view);
        mListItemClickListener = (ListItemClickListener) activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        StatusListView statusListView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            statusListView = (StatusListView) inflater.inflate(
                    R.layout.fragment_status_list_view, parent, false);
        } else {
            statusListView = (StatusListView) convertView;
        }
        statusListView.bind(getItem(position));
        statusListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListItemClickListener.onItemClick(getItem(position));
            }
        });
        return statusListView;
    }

    /**
     * 通知用
     */
    public interface ListItemClickListener {
        void onItemClick(Liner liner);
    }
}
