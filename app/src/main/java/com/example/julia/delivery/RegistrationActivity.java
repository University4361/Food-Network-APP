package com.example.julia.delivery;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.julia.delivery.api.models.AuthRequest;
import com.example.julia.delivery.api.models.AuthResponse;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity  extends Activity{

    @BindView(R.id.sign_in_button)Button signInButton;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.phone)
    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.sign_in_button)
    void sighIn(){

        AuthRequest authRequest = new AuthRequest();
        authRequest.setLogin(phoneEditText.getText().toString());
        authRequest.setPassword(passwordEditText.getText().toString());

        App.getApi().loginCourier(authRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, final Response<AuthResponse> response) {
                getCurrentContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getCurrentContext());
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.token), response.body().getToken());
                            editor.apply();

                            Intent intent = new Intent(getCurrentContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t)
            {
                getCurrentContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });


    }

    Activity getCurrentContext()
    {
        return  this;
    }
}
