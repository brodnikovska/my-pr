package entities;

public enum PageMessages {
    LOGIN_SUCCESSFUL("Signed in successfully"),
    BAD_CREDENTIALS("An error occurred while connecting to server: You do not have enough permissions. Bad credentials"),
    LOGOUT_SUCCESSFUL("You have been logged out");

    private final String message;

    PageMessages(String s) {
        message = s;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
