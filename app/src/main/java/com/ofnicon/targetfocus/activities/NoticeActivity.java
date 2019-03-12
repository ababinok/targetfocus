package com.ofnicon.targetfocus.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ofnicon.targetfocus.R;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener {

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Intent inputData = getIntent();
        index = inputData.getIntExtra(MainActivity.INDEX_FIELD, -1);
        ((EditText) findViewById(R.id.goal_et)).setText(inputData.getStringExtra(MainActivity.TEXT_FIELD));

        Button deleteBtn = findViewById(R.id.delete_button);

        findViewById(R.id.ok_button).setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        if (index < 0) {
            deleteBtn.setVisibility(View.GONE);
            findViewById(R.id.button_divider).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:

                EditText goalET = findViewById(R.id.goal_et);
                String text = goalET.getText().toString().trim();
                if (text.length() == 0) {
                    goalET.setError(getString(R.string.enter_goal));
                    goalET.requestFocus();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(MainActivity.INDEX_FIELD, index);
                intent.putExtra(MainActivity.TEXT_FIELD, text);
                setResult(MainActivity.RESULT_OK, intent);
                finish();
                break;

            case R.id.delete_button:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                setResult(MainActivity.RESULT_DELETE, new Intent().putExtra(MainActivity.INDEX_FIELD, index));
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //nothing
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Удалить цель?").setPositiveButton("ДА", dialogClickListener)
                        .setNegativeButton("НЕТ", dialogClickListener).show();
                break;

        }
    }
}
