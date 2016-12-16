package com.calculator.asif.calculator;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
/**
 * Created by Asif on 12-Jun-16.
 */

public class MainActivity extends Activity {
    /** Called when the activity is first created. */

    //private Button btn1=null;
    private TextView result_display=null;
    private TextView equation_display=null;
    private double result=0;
    private String sym=" ";
    private int flag=0;
    private double value=0;
    private int eq=0;
    private int symcount=0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static int increment=0;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        this.result_display=(TextView) this.findViewById(R.id.result_display);
        this.equation_display=(TextView) this.findViewById(R.id.equation_display);
        increment = getIntent().getIntExtra("increment", 0);


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        increment=Integer.parseInt(sharedPreferences.getString("increment", "0"));
        //this.btn1=(Button)this.findViewById(R.ID.btn1);

    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Log.d("test", "super on restart called.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putString("editTextValue", MainActivity.this.result_display.getText().toString());
        outState.putString("textViewValue", MainActivity.this.equation_display.getText().toString());

    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("increment",Integer.toString(increment));
        editor.commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        MainActivity.this.result_display.setText(savedInstanceState.get("editTextValue").toString());
        MainActivity.this.equation_display.setText(savedInstanceState.get("textViewValue").toString());
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.d("test", "super on stop called.");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("test", "super on destroy called.");
    }



    public void cngfntsize(String s)
    {
        int config =this.getResources().getConfiguration().orientation;
        if (s.length()<9 && config==1 )
        {
            result_display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }
        else if (s.length()<13 &&  config==1 )
        {
            result_display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        }
        else if (s.length()<11 &&  config==2)
            result_display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        else if (s.length()<15 && config==2)
        {
            result_display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        }
        else
            result_display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    }
    public void displaylimit(String x,View v)
    {
        int config =this.getResources().getConfiguration().orientation;
        Button btn = (Button) v;
        if (x.length()>22 && config==1)
        {
            Toast.makeText(this, "Limit Reached", Toast.LENGTH_SHORT).show();
            result_display.setText(x);
        }
        else if (x.length()>26 && config==2)
        {
            Toast.makeText(this, "Limit Reached", Toast.LENGTH_SHORT).show();
            result_display.setText(x);
        }
        else {

            this.result_display.setText(this.result_display.getText() + btn.getText().toString());
        }

    }
    public void number(View v){
        flag=1;
        Button btn = (Button) v;
        String x= this.result_display.getText().toString();
        String y= this.equation_display.getText().toString();
        cngfntsize(x);

        if (this.equation_display.getText().toString().isEmpty())
        {
            if (result_display.getText().toString().equals("0") ) {
                this.result_display.setText(btn.getText().toString());
            }
            else if(value==0 && eq==0)
            {
                this.result_display.setText(btn.getText().toString());
            }
            else
                displaylimit(x,v);
            /*else if (x.length()>22 && config==1)
            {
                result_display.setText(x.substring(0, x.length() - 1)+btn.getText());
            }
            else if (x.length()>26 && config==2)
            {
                result_display.setText(x.substring(0, x.length() - 1)+btn.getText());
            }
            else {

                this.result_display.setText(this.result_display.getText() + btn.getText().toString());
            }*/
        }

        else if (eq>0)
        {
            this.result_display.setText(btn.getText().toString());
            sym=" ";
            value=0;
            result=0;
            eq=0;
        }
        else if (result_display.getText().toString().equals(Double.toString(result)))
        {
            this.result_display.setText(btn.getText().toString());
        }
        else {
            if (flag==1 )
            {
                displaylimit(x,v);

            }
            else
                this.result_display.setText(btn.getText().toString());
        }
        symcount=0;
    }

    public void symbols(View v)
    {
        Button btn =(Button) v;
        String ps = sym;
        sym = btn.getText().toString();

        value=Double.parseDouble(this.result_display.getText().toString());
        String y= this.equation_display.getText().toString();
        if (symcount==0) {
            if (y.isEmpty() && flag == 0) {
                result = 0;
                this.equation_display.setText("0" + btn.getText().toString());

            }
            else {

                if (flag == 0 && eq == 0) {
                    equation_display.setText(y.substring(0, y.length() - 1) + sym);

                } else {
                    if (eq > 0) {
                        value = 0;
                        ps = "=";
                        flag = 0;
                        this.equation_display.setText(this.result_display.getText().toString() + btn.getText().toString());
                    } else if(ps.equals(" ")) {
                        this.equation_display.setText(this.result_display.getText().toString() + btn.getText().toString());
                    }
                    else {
                        this.equation_display.setText(this.equation_display.getText().toString() + this.result_display.getText().toString() + btn.getText().toString());
                    }

                }

            }
            if (ps.equals("+")) {
                result = result + value;
            } else if (ps.equals("-")) {
                result = result - value;
            } else if (ps.equals("*")) {
                result_display.setText(ps);
                result = result * value;
            } else if (ps.equals("/")) {
                result = result / value;
            } else if (ps.equals(" ")) {
                result = value;
            } else if (ps.equals("=")) {
                result = result;
            }
        }
        else {
            value = 0;
            equation_display.setText(y.substring(0, y.length() - 1) + sym);
        }
        String stringdouble = Double.toString(result);
        result_display.setText(stringdouble);
        flag=0;
        eq=0;
        symcount++;

    }

    public void equal(View v) {
        if (eq==0)
        {
            value=Double.parseDouble(this.result_display.getText().toString());
            this.equation_display.setText(this.equation_display.getText() + this.result_display.getText().toString() );
            eq++;
        }
        else{
            this.equation_display.setText(this.equation_display.getText() + sym + value );
        }
        String stringdouble;
        switch (sym) {
            case "+":
                result = result + value;
                stringdouble = Double.toString(result);
                result_display.setText(stringdouble);
                break;
            case "-":
                result = result - value;
                stringdouble = Double.toString(result);
                result_display.setText(stringdouble);
                break;
            case "*":
                result = result * value;
                stringdouble = Double.toString(result);
                result_display.setText(stringdouble);
                break;
            case "/":
                if (value==0)
                {
                    result_display.setText("Math Error");
                    break;
                }
                else
                result = result / value;
                stringdouble = Double.toString(result);
                result_display.setText(stringdouble);
                break;
        }
        sharedPreferences = getSharedPreferences("calculationHistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("history" + increment, equation_display.getText().toString() + " = " + result_display.getText().toString());
        editor.commit();
        increment++;
        symcount=0;

    }
    public void history(View v)
    {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        intent.putExtra("increment", increment);
        startActivity(intent);
        Log.d("test", "super on destroy called.");
    }

    public void cancel(View v)
    {
        sym=" ";
        value=0;
        result=0;
        flag=0;
        eq=0;
        equation_display.setText(null);
        result_display.setText("0");
    }

    public void memory(View v)
    {
        Button btn =(Button) v;
        String button = btn.getText().toString();
        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        double  memVal=0,cVal=0;
        switch (button) {
            case "M+":
                memVal=Double.parseDouble(sharedPreferences.getString("memory", "0"));
                cVal=Double.parseDouble(this.result_display.getText().toString());
                memVal=memVal+cVal;
                editor = sharedPreferences.edit();
                editor.putString("memory", Double.toString(memVal));
                editor.commit();
                Toast.makeText(this, "New Value is "+Double.toString(memVal)+" Saved", Toast.LENGTH_SHORT).show();
                break;
            case "MR":
                memVal=Double.parseDouble(sharedPreferences.getString("memory", "0"));
                //cVal=Double.parseDouble(this.result_display.getText().toString());
                //memVal=memVal-cVal;
                result_display.setText(Double.toString(memVal));
                editor = sharedPreferences.edit();
                editor.putString("memory", Double.toString(memVal));
                editor.commit();
                sym=" ";
                value=0;
                result=memVal;
                flag=1;
                //eq=0;
                //Toast.makeText(this, "New Value is "+Double.toString(memVal)+" Saved", Toast.LENGTH_SHORT).show();
                break;
            case "MC":
                sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("memory", Double.toString(memVal));
                editor.commit();
                Toast.makeText(this, "Memory Cleared", Toast.LENGTH_SHORT).show();
                break;

        }
    }


}
