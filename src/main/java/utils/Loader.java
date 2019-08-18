package utils;

import common.Client;
import common.Track;
import common.TrackList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static common.Constants.*;

public class Loader {
    public static void initialise() {
        loadProperties();
        Client.initializeClient();
    }

    private static void loadProperties() {
        properties = new Properties();
        try {
            var file = new FileInputStream("src/main/resources/config.properties");
            var inputStream = new InputStreamReader(file, "UTF-8");
            properties.load(inputStream);

            USERNAME = new Property("login");
            PASSWORD = new Property("password");
            ANDROID_ID = new Property("imei");
            ANDROID_ID2 = new Property("imei_secondary");
            MUSIC_FOLDER = new Property("local_music_folder");
            DEBUG = new Property("debug");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<List<Track>> getCompletableFutureFunction(TrackList function) {
        return CompletableFuture.supplyAsync(() -> function.getTrackList());
    }

    public static List<Track> getTrackList(CompletableFuture<List<Track>> completableFuture) {
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
