package com.ofnicon.targetfocus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ofnicon.targetfocus.R;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        findViewById(R.id.ok_button).setOnClickListener(this);
        findViewById(R.id.delete_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:
                Intent intent = new Intent();
                intent.putExtra("text", ((EditText) findViewById(R.id.goal_et)).getText().toString().trim());
                setResult(MainActivity.RESULT_OK, intent);
                finish();
                break;
            case R.id.delete_button:
                setResult(MainActivity.RESULT_DELETE);
                finish();
                break;
        }
    }
}
