package com.ibanez.appointment.presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ibanez.appointment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.login_toolbar);
        FrameLayout toolBarContent = toolbar.findViewById(R.id.login_toolbar_content);
        toolBarContent.addView(getLayoutInflater().inflate(R.layout.login_toolbar_items, null));

        ImageButton btn = toolBarContent.findViewById(R.id.login_toolbar_items_back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView login = toolBarContent.findViewById(R.id.login_toolbar_items_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideVirtualKeyboard(LoginActivity.this, view);
                authenticate();
            }
        });
    }


    private void authenticate() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, LocationsActivity.class);
        startActivity(intent);
    }

    private static void hideVirtualKeyboard(Context context, View focusedView) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }
}
