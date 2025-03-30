package com.example.powerhouse;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.powerhouse.entities.Habitat;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        TextView b = findViewById(R.id.intent_signup);

        b.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, InscriptionsActivity.class)));
        buttonLogin = findViewById(R.id.buttonLogin);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        buttonLogin.setOnClickListener((v -> login()));
    }

    private void login() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        String URL = "http://192.168.1.84/powerhome/login.php?email=" + email + "&password=" + password;

        Ion.with(this)
                .load("GET", URL)
                .asString()
                .setCallback((e, result) -> {
                    if (e != null) {
                        Toast.makeText(LoginActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        JSONObject jsonResponse = new JSONObject(result);

                        if (jsonResponse.has("token")) {

                            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("firstname", jsonResponse.getString("firstname"));
                            editor.putString("lastname", jsonResponse.getString("lastname"));
                            editor.putString("user_token", jsonResponse.getString("token"));
                            editor.putString("token_expiry", jsonResponse.getString("expired_at"));
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, HabitatsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Erreur de traitement des données", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
