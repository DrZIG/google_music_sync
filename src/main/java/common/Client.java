package common;

import google.GoogleApiController;
import local.LocalLibraryController;

public class Client {
    private static Client client;

    private GoogleApiController googleController;
    private LocalLibraryController localController;

    private Client() {
        googleController = GoogleApiController.getController();
        localController = LocalLibraryController.getController();
    }

    public static void initializeClient() {
        if (client != null) {
            System.out.println("Client already initialized.");
            return;
        }

        client = new Client();
    }

    public static Client getClient() {
        return client;
    }

    public GoogleApiController getGoogleController() {
        return googleController;
    }

    public LocalLibraryController getLocalController() {
        return localController;
    }
}
