package entities;

public enum StatusCodes {
    SUCCESS("200"),
    CREATED("201"),
    NOT_FOUND("404");

    private final String name;

    StatusCodes(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
