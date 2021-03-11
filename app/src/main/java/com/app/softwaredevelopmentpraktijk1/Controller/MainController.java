package com.app.softwaredevelopmentpraktijk1.Controller;


import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.app.softwaredevelopmentpraktijk1.R;

public class MainController extends AppCompatActivity {

    TextView currentMonthField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Date date = new Date();

    }

    public void setCurrentMonthField() {
        currentMonthField = (TextView)findViewById(R.id.current_month_field);
    }
}
