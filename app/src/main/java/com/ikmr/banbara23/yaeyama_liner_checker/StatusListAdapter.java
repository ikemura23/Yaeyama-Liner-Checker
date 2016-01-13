
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 一覧アダプター
 */
public class StatusListAdapter extends ArrayAdapter<Liner> {

    // ListItemClickListener mListItemClickListener;

    public StatusListAdapter(Context context, Activity activity) {
        super(context, R.layout.fragment_status_list_view);

        // colorNormal = context.getResources().getColor(R.color.status_normal);

        // mListItemClickListener = (ListItemClickListener) activity;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // StatusListView statusListView;
        ViewHolder viewHolder;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        }
        else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.fragment_status_list_view, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        Liner liner = getItem(position);
        // 港
        viewHolder.portText.setText(liner.getPort().getValue());
        // ステータス
        switch (liner.getStatus()) {
            case NORMAL:
                viewHolder.statusIconText.setText(viewHolder.normal);
                viewHolder.statusIconText.setTextColor(viewHolder.colorNormal);
                break;
            case CANCEL:
                viewHolder.statusIconText.setText(viewHolder.cancel);
                viewHolder.statusIconText.setTextColor(viewHolder.colorCancel);
                break;
            case CAUTION:
                viewHolder.statusIconText.setText(viewHolder.cation);
                viewHolder.statusIconText.setTextColor(viewHolder.colorCation);
                break;
            case SUSPEND:
                viewHolder.statusIconText.setText(viewHolder.suspend);
                viewHolder.statusIconText.setTextColor(viewHolder.colorSuspend);
        }
        viewHolder.descriptionText.setText(liner.getText());

        // if (convertView == null) {
        // LayoutInflater inflater = LayoutInflater.from(getContext());
        // statusListView = (StatusListView) inflater.inflate(
        // R.layout.fragment_status_list_view, parent, false);
        // } else {
        // statusListView = (StatusListView) convertView;
        // }
        // statusListView.bind(getItem(position));
        // statusListView.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // mListItemClickListener.onItemClick(getItem(position));
        // }
        // });
        return view;
    }

    // /**
    // * 通知用
    // */
    // public interface ListItemClickListener {
    // void onItemClick(Liner liner);
    // }

    static class ViewHolder {

        @Bind(R.id.view_status_list_status_icon_text)
        TextView statusIconText;
        @Bind(R.id.view_status_list_port_text)
        TextView portText;
        @Bind(R.id.view_status_list_description_text)
        TextView descriptionText;

        // Bind String ---------------------------------------
        @BindString(R.string.status_normal)
        String normal;

        @BindString(R.string.status_cation)
        String cation;

        @BindString(R.string.status_cancel)
        String cancel;

        @BindString(R.string.status_cancel)
        String suspend;

        // BindColor ---------------------------------------
        @BindColor(R.color.status_normal)
        int colorNormal;
        @BindColor(R.color.status_cation)
        int colorCation;
        @BindColor(R.color.status_cancel)
        int colorCancel;
        @BindColor(R.color.dark_grey)
        int colorSuspend;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
