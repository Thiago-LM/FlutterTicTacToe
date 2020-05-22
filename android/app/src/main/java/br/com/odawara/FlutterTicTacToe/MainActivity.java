package br.com.odawara.FlutterTicTacToe;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.robotemi.sdk.*;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    FlutterEngine engine;
    BinaryMessenger binaryMessenger;
    Context maCtx;

    private static final String CHANNEL = "br.com.odawara.FlutterTicTacToe/TemiSDK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maCtx = getApplicationContext();
        binaryMessenger = engine.getDartExecutor().getBinaryMessenger();
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, CHANNEL);
        methodChannel.setMethodCallHandler(this::methodChannelCallHandler);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        this.engine = flutterEngine;
    }

    private void methodChannelCallHandler(MethodCall call, MethodChannel.Result result) {
        switch (call.method) {
            case "hideTopBar":
                Robot.getInstance().hideTopBar();
                break;
            case "speak":
                speak(call.argument("word"));
                break;
            default:
                break;
        }
    }

    /**
     * Have the robot speak while displaying what is being said.
     */
    public void speak(String word) {
        TtsRequest ttsRequest = TtsRequest.create(word, true);
        Robot.getInstance().speak(ttsRequest);
    }
}
