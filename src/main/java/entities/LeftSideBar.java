package entities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LeftSideBar {

    DASHBOARDS("Dashboards"),
    LAUNCHES("Launches"),
    FILTERS("Filters"),
    DEBUG("Debug"),
    PROJECT_MEMBERS("Project members"),
    PROJECT_SETTINGS("Project settings");

    private final String name;

    LeftSideBar(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static List<String> icons;

    public static List<String> getValues() {
        icons = Stream.of(values())
                .map(LeftSideBar::toString)
                .collect(Collectors.toList());
        return icons;
    }
}
