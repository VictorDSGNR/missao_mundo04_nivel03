package com.example.domaaudio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AudioHelper audioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        audioHelper = new AudioHelper(this);


        Button speakButton = findViewById(R.id.speakButton);

        speakButton.setOnClickListener(v -> {
            if (!audioHelper.isAudioDeviceConnected()) {
                Toast.makeText(this, "Nenhum dispositivo de áudio conectado. Conecte um fone ou caixa de som.", Toast.LENGTH_SHORT).show();
            } else if (!audioHelper.isReady()) {
                Toast.makeText(this, "Inicializando o sistema de voz. Por favor, aguarde...", Toast.LENGTH_SHORT).show();
            } else {
                audioHelper.speak("Seja bem-vindo ao DomaAudio!");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar os recursos do TextToSpeech
        if (audioHelper != null) {
            audioHelper.shutdown();
        }
    }
}
