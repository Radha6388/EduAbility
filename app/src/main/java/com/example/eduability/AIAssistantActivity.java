package com.example.eduability;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class AIAssistantActivity extends AppCompatActivity {

    ImageView btnMic, imgOwl;
    TextView txtBubble;

    private static final int SPEECH_REQUEST_CODE = 1;

    TextToSpeech tts;
    boolean isListening = false;

    String stage = "comfort";
    int step = 0;
    String childName = "";

    Random random = new Random();

    // 🎲 QUESTION BANKS
    String[] alphabetQ = {
            "Tell me a word starting with A",
            "Say a fruit starting with A",
            "Can you say a word starting with B",
            "Tell me a word starting with C"
    };

    String[] numberQ = {
            "What comes after 5",
            "Count from 1 to 5",
            "What comes after 3",
            "Which number is bigger 3 or 7"
    };

    String[] mathQ = {
            "What is 2 plus 3",
            "What is 5 minus 2",
            "You have 3 chocolates and get 2 more how many",
            "Which is bigger 10 or 6"
    };

    String[] logicQ = {
            "Which is different apple banana car",
            "What comes next 1 2 3",
            "Which shape is round",
            "If it rains what do we use"
    };

    private String getRandom(String[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiassistant);

        btnMic = findViewById(R.id.btnMic);
        imgOwl = findViewById(R.id.imgOwl);
        txtBubble = findViewById(R.id.txtBubble);

        // 🦉 Animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgOwl, "scaleX", 1f, 1.05f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgOwl, "scaleY", 1f, 1.05f);

        scaleX.setDuration(1000);
        scaleY.setDuration(1000);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);

        scaleX.start();
        scaleY.start();

        // 🔊 TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {

                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(0.75f);

                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override public void onStart(String id) {}
                    @Override public void onDone(String id) {
                        runOnUiThread(() -> {
                            if (!isListening) startVoiceInput();
                        });
                    }
                    @Override public void onError(String id) {}
                });

                speak("Hey there 😊 I’m your learning buddy! What’s your name?");
            }
        });

        btnMic.setOnClickListener(v -> startVoiceInput());
    }

    private void startVoiceInput() {

        if (isListening) return;

        isListening = true;

        txtBubble.setText("Listening... 👂");

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            isListening = false;
            txtBubble.setText("Voice not supported 😔");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        isListening = false;

        if (requestCode == SPEECH_REQUEST_CODE) {

            if (resultCode == RESULT_OK && data != null) {

                ArrayList<String> results =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (results != null && !results.isEmpty()) {
                    handleResponse(results.get(0).toLowerCase());
                } else {
                    retryListening();
                }

            } else {
                retryListening();
            }
        }
    }

    private void retryListening() {
        speak("I couldn’t hear clearly 😊 Please say it again");
    }

    private void handleResponse(String input) {

        if (stage.equals("comfort")) {
            comfortStage(input);
        } else if (stage.equals("engage")) {
            engageStage(input);
        } else {
            learnStage(input);
        }
    }

    private void comfortStage(String input) {

        if (step == 0) {
            childName = input;
            step++;
            speak("Nice to meet you " + childName + "! How are you?");
        }
        else if (step == 1) {
            step++;
            speak("Did you play today?");
        }
        else {
            stage = "engage";
            step = 0;
            speak("Do you like cartoons?");
        }
    }

    private void engageStage(String input) {

        if (step == 0) {
            step++;
            speak("Wow! " + input + " is nice 😄");
        }
        else {
            stage = "learn";
            step = 0;
            speak("Let’s play a fun learning game 🎉");
        }
    }

    private void learnStage(String input) {

        if (step == 0) {
            step++;
            speak(getRandom(alphabetQ));
        }

        else if (step == 1) {
            speak("Good try 😊");
            step++;
            speak(getRandom(numberQ));
        }

        else if (step == 2) {
            speak("Nice 😊");
            step++;
            speak(getRandom(mathQ));
        }

        else if (step == 3) {
            speak("Great 😊");
            step++;
            speak(getRandom(logicQ));
        }

        else {
            speak("You did amazing today 🌟 Let’s play again!");
            stage = "comfort";
            step = 0;
        }
    }

    private void speak(String text) {

        txtBubble.setText(text);

        String clean = text.replaceAll("[^\\p{L}\\p{Nd}\\p{Z}\\p{P}]", "");

        tts.speak(clean, TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_ID");
    }

    @Override
    protected void onDestroy() {
        if (tts != null) tts.shutdown();
        super.onDestroy();
    }
}