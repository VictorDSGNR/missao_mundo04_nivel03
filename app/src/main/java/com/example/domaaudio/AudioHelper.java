package com.example.domaaudio;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class AudioHelper {

    public boolean isReady() {
        return isReady;
    }

    private static final String TAG = "AudioHelper";

    private final AudioManager audioManager;
    private final Context context;
    private TextToSpeech textToSpeech;
    private boolean isReady = false;

    public AudioHelper(Context context) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        this.textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale language = Locale.US;
                int result = textToSpeech.setLanguage(language);

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "Idioma não suportado: " + language);
                } else {
                    isReady = true;
                    Log.i(TAG, "TextToSpeech inicializado com sucesso");
                }
            } else {
                Log.e(TAG, "Falha ao inicializar o TextToSpeech");
            }
        });
    }


    public boolean isAudioDeviceConnected() {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false;
        }

        AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
        for (AudioDeviceInfo device : devices) {
            int type = device.getType();
            if (type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP ||
                    type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO ||
                    type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                    type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                    type == AudioDeviceInfo.TYPE_USB_HEADSET ||
                    type == AudioDeviceInfo.TYPE_USB_DEVICE ||
                    type == AudioDeviceInfo.TYPE_LINE_ANALOG ||
                    type == AudioDeviceInfo.TYPE_LINE_DIGITAL ||
                    type == AudioDeviceInfo.TYPE_HDMI) {
                Log.i(TAG, "Dispositivo de áudio conectado: " + device.getProductName());
                return true;
            }
        }
        Log.i(TAG, "Nenhum dispositivo de áudio conectado");
        return false;
    }


    public void speak(String text) {
        if (isReady) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_ID");
        } else {
            Log.w(TAG, "TextToSpeech não está pronto ainda");
        }
    }


    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
