package com.alexua.messages.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alexua.messages.R;
import com.alexua.messages.core.preferences.SharedPrefHelper;
import com.alexua.messages.core.server.dto.LoginResponseDto;
import com.alexua.messages.core.server.requestapi.ServerRequestAdapter;
import com.alexua.messages.ui.MainActivity;
import com.alexua.messages.ui.base.BaseTextWatcher;
import com.android.volley.Response;
import com.android.volley.VolleyError;


public class LoginActivity extends Activity {

    TextView email = null;
    TextView pass = null;
    Button login = null;
    View loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!SharedPrefHelper.getToken("").isEmpty() &&
                !SharedPrefHelper.getEmail("").isEmpty() &&
                !SharedPrefHelper.getUserId("").isEmpty() &&
                !SharedPrefHelper.getUserName("").isEmpty()) {
            startMainActivity();
            return;
        }

        setContentView(R.layout.login_activity);
        email = (TextView) findViewById(R.id.login_activity_email);
        pass = (TextView) findViewById(R.id.login_activity_password);
        login = (Button) findViewById(R.id.login_activity_button);
        loading = findViewById(R.id.login_activity_loading_animation);

        login.setEnabled(false);
        email.setText(SharedPrefHelper.getEmail(""));

        email.addTextChangedListener(loginTextWatcher);
        pass.addTextChangedListener(loginTextWatcher);


        findViewById(R.id.login_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((TextView) findViewById(R.id.login_activity_email)).getText().toString();
                String password = ((TextView) findViewById(R.id.login_activity_password)).getText().toString();
                SharedPrefHelper.setEmail(email);
                loading.setVisibility(View.VISIBLE);
                setEnableAll(false);
                ServerRequestAdapter.singinRequest(email, password, loginListener, errorLoginListener);
            }
        });

        setTitle("Please login to BLABLABLA");
    }

    BaseTextWatcher loginTextWatcher = new BaseTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            login.setEnabled(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty());
        }
    };

    Response.Listener<LoginResponseDto> loginListener = new Response.Listener<LoginResponseDto>() {
        @Override
        public void onResponse(LoginResponseDto response) {
            String token = response.getData().getToken();
            if (token != null && !token.isEmpty()) {
                SharedPrefHelper.setToken(token);
                SharedPrefHelper.setEmail(email.getText().toString());
                SharedPrefHelper.setUserName(response.getData().getName());
                SharedPrefHelper.setUserId(response.getData().getUser_id());
                startMainActivity();
            } else {
                setEnableAll(true);
                requestError(response.getErrors());
            }
        }
    };

    Response.ErrorListener errorLoginListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            requestError(new String(error.networkResponse.data));
        }
    };

    private void requestError(String msg) {
        pass.setText("");

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        setEnableAll(true);
        loading.setVisibility(View.GONE);
    }

    private void setEnableAll(boolean enable) {
        login.setEnabled(enable && !email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty());
        email.setEnabled(enable);
        pass.setEnabled(enable);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
