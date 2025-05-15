package com.example.domaaudio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AudioHelper audioHelper;
    private Button speakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o AudioHelper
        audioHelper = new AudioHelper(this);

        // Referência ao botão
        speakButton = findViewById(R.id.speakButton);

        // Configura o clique do botão
        speakButton.setOnClickListener(v -> audioHelper.speak("Seja bem-vindo ao DomaAudio!"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar os recursos do TextToSpeech
        audioHelper.shutdown();
    }
}
