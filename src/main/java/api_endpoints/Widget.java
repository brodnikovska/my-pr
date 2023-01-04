package api_endpoints;

public enum Widget {
    WIDGET("/v1/{projectName}/widget"),
    WIDGET_ID("/v1/{projectName}/widget/{widgetId}");

    private final String name;

    Widget(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
