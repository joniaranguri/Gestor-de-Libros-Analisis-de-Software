package controllers;

public final class AuthenticationManager {

    private static AuthenticationManager INSTANCE;

    public static AuthenticationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationManager();
        }
        return INSTANCE;
    }

    public boolean performUserAuthentication(final String userText, final char[] passwordText) {

        return true;
    }
}
