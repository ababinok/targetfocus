package com.ofnicon.targetfocus.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ofnicon.targetfocus.R;
import com.ofnicon.targetfocus.adapters.NoticesAdapter;
import com.ofnicon.targetfocus.objects.Notice;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NOTICE_ACTIVITY = 0;
    static final int RESULT_OK = 0;
    static final int RESULT_DELETE = 1;

    ArrayList<Notice> notices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        @SuppressLint("InflateParams") final View header = LayoutInflater.from(this).inflate(R.layout.notices_header, null);

        ListView listView = findViewById(R.id.packages_list);
        listView.addHeaderView(header);

        notices = new ArrayList<>();
        notices.add(new Notice("Идеология лидера"));
        notices.add(new Notice("Убеждения лидера"));

        NoticesAdapter noticesAdapter = new NoticesAdapter(this, notices);

        listView.setAdapter(noticesAdapter);

        findViewById(R.id.add_notice_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, NoticeActivity.class), REQUEST_CODE_NOTICE_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NOTICE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                notices.add(new Notice(data.getStringExtra("text")));
                Toast.makeText(this, "Added", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_DELETE) {
                Toast.makeText(this, "To delete", Toast.LENGTH_LONG).show();
            }
        }
    }
}
