package com.calculator.asif.calculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Asif on 21-Jun-16.
 */
public class HistoryActivity extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static int increment = 0;
    private TextView history_display=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_history);
        increment = getIntent().getIntExtra("increment", 0);
        this.history_display=(TextView) this.findViewById(R.id.history_display);
    }


    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("calculationHistory", Context.MODE_PRIVATE);
        for(int i=0; i<increment; i++)
        {
            String memory = sharedPreferences.getString("history" + i, "0");
            if(memory.equals("0"))
            {
                Toast.makeText(HistoryActivity.this, "No more History Available", Toast.LENGTH_SHORT).show();
            }
            else
            {
                this.history_display.append(memory);
                this.history_display.append("\n");
            }
        }
    }
    public void delete(View v)
    {
        sharedPreferences = getSharedPreferences("calculationHistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        this.history_display.setText("");
        super.onPause();
        increment=0;
        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("increment",Integer.toString(increment));
        editor.commit();
    }
    public void back(View v)
    {
        finish();

    }
    protected void onStart() {
        super.onStart();
    }
}
