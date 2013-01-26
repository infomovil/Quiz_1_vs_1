package com.infomovil.quiz1vs1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Quiz1vs1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quiz1vs1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layout_quiz1vs1, menu);
        return true;
    }
    
}
