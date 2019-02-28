package com.ofnicon.targetfocus.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ofnicon.targetfocus.R;
import com.ofnicon.targetfocus.adapters.NoticesAdapter;
import com.ofnicon.targetfocus.core.MySharedPreferences;
import com.ofnicon.targetfocus.objects.Notice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NOTICE_ACTIVITY = 0;
    static final int RESULT_OK = 10;
    static final int RESULT_DELETE = 11;
    static final int RESULT_CANCEL = 12;
    static final String INDEX_FIELD = "index";
    static final String TEXT_FIELD = "text";
    private static final String PREFERENCES_NOTICES_LIST = "my notices list";

    List<Notice> notices;
    NoticesAdapter noticesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        @SuppressLint("InflateParams") final View header = LayoutInflater.from(this).inflate(R.layout.notices_header, null);

        final ListView listView = findViewById(R.id.packages_list);
        listView.addHeaderView(header);

        Set<String> savedNotices = MySharedPreferences.getStringSet(this, PREFERENCES_NOTICES_LIST);
        if (savedNotices != null) {
            notices = Notice.getNoticeListFromStringList(new ArrayList<>(savedNotices));
        } else {
            notices = new ArrayList<>();
        }

        noticesAdapter = new NoticesAdapter(this, notices);

        listView.setAdapter(noticesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return; // Header
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                intent.putExtra(INDEX_FIELD, position - 1);
                intent.putExtra(TEXT_FIELD, notices.get(position - 1).getText());
                startActivityForResult(intent, REQUEST_CODE_NOTICE_ACTIVITY);
            }
        });

        findViewById(R.id.add_notice_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(MainActivity.this, NoticeActivity.class)
                                .putExtra(INDEX_FIELD, -1)
                                .putExtra(TEXT_FIELD, ""),
                        REQUEST_CODE_NOTICE_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NOTICE_ACTIVITY && data != null) {

            if (resultCode == RESULT_OK) {
                int indexToChange = data.getIntExtra(INDEX_FIELD, -1);
                String newText = data.getStringExtra(TEXT_FIELD);
                if (indexToChange >= 0) {
                    notices.get(indexToChange).setText(newText);
                    noticesAdapter.notifyDataSetChanged();
                } else {
                    notices.add(new Notice(newText));
                }
                saveNotices();
            } else if (resultCode == RESULT_DELETE) {
                int indexToRemove = data.getIntExtra(INDEX_FIELD, -1);
                if (indexToRemove >= 0) {
                    notices.remove(indexToRemove);
                    noticesAdapter.notifyDataSetChanged();
                }
                saveNotices();
            }
        }
    }

    private void saveNotices() {
        MySharedPreferences.saveStringSet(this, PREFERENCES_NOTICES_LIST,
                new HashSet<>(Notice.getStringListFromNoticeList(notices)));
    }

}
