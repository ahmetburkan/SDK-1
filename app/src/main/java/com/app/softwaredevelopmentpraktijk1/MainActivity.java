package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.app.softwaredevelopmentpraktijk1.Model.DateModel;

public class MainActivity extends AppCompatActivity {

    TextView currentMonthField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCurrentMonthField();
    }

    public void setCurrentMonthField() {
        DateModel dateModel = new DateModel();
        currentMonthField = (TextView)findViewById(R.id.current_month_field);
        currentMonthField.setText(dateModel.getMonthString());
    }
}