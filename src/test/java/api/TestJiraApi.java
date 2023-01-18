package api;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Paths.get;

public class TestJiraApi extends JiraApiTestCase {

    private final InputStream updatedSchema;
    {
        try {
            updatedSchema = newInputStream(get(new File("").getAbsolutePath() + "/src/main/java/json/updated-ticket.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    @Test()
    public void modifyDashboard() {
        given().spec(requestSpecification)
                .get("RPT1-3")
                .then()
                .spec(responseSpecification)
                .statusCode(200);

        given().spec(requestSpecification)
                .body(IOUtils.toString(updatedSchema, StandardCharsets.UTF_8))
                .put("RPT1-3")
                .then()
                .spec(responseSpecification)
                .statusCode(204);
    }
}
