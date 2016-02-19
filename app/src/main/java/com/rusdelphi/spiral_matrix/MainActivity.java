package com.rusdelphi.spiral_matrix;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    calk();
                }
                return false;
            }
        });
    }

    public void calk() {
        int n = Integer.valueOf(editText.getText().toString());
        if (n < 1 || n > 100)
            Toast.makeText(this, "Число превышает диапазон", Toast.LENGTH_LONG).show();
        else {
            int[][] matrix = new int[n][n];
            int row = 0;
            int col = 0;
            int dx = 1;
            int dy = 0;
            int dirChanges = 0;
            int visits = n;
            for (int i = -1; i < n * n - 1; i++) {
                matrix[row][col] = i + 1;
                if (--visits == 0) {
                    visits = n * (dirChanges % 2) + n * ((dirChanges + 1) % 2) - (dirChanges / 2 - 1) - 2;
                    int temp = dx;
                    dx = -dy;
                    dy = temp;
                    dirChanges++;
                }
                col += dx;
                row += dy;
            }
            TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
            tl.removeAllViews();
            for (int i = 0; i < n; i++) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < n; j++) {
                    TextView textView = new TextView(this);
                    textView.setText(String.valueOf(matrix[i][j]));
                    if ((i + j) % 2 == 0) {
                        textView.setBackgroundColor(Color.BLACK);
                        textView.setTextColor(Color.WHITE);
                    } else {
                        textView.setBackgroundColor(Color.GRAY);
                    }
                    tableRow.addView(textView, j);
                }

                tl.addView(tableRow, i);
            }

        }

    }

    public void onSendClick(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        calk();

    }
}







