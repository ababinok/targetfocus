package com.ofnicon.targetfocus.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.ofnicon.targetfocus.R;
import com.ofnicon.targetfocus.objects.Notice;

import java.util.ArrayList;

public class NoticesAdapter extends ArrayAdapter<Notice> {

    public NoticesAdapter(Context context, ArrayList<Notice> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.notice_item, parent, false);
        }

        Notice currentNotice = getItem(position);
        TextView noticeTV = listItemView.findViewById(R.id.notice_tv);
        noticeTV.setText(currentNotice != null ? currentNotice.getText() : "");

        return listItemView;

    }
}
