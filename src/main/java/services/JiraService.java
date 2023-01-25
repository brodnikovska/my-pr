package services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Paths.get;

public class JiraService {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    private final InputStream updatedSchema;
    {
        try {
            updatedSchema = newInputStream(get(new File("").getAbsolutePath() + "/src/main/java/json/updated-ticket.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialiseConfig() {
        RestAssured.baseURI = "https://ira123abc.atlassian.net/rest/api/2/issue";
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Basic " + "aXJ5bmFicmRAZ21haWwuY29tOm9xSjU2N1lrUGMyaVpPN01LbDhXOERFNA==")
                .addHeader("Accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }

    public void modifyDashboard(String id) {
        initialiseConfig();
        given().spec(requestSpecification)
                .get("RPT1-3")
                .then()
                .spec(responseSpecification)
                .statusCode(200);

        try {
            given().spec(requestSpecification)
                    .body(String.format(IOUtils.toString(updatedSchema, StandardCharsets.UTF_8), id))
                    .post("RPT1-3/transitions")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(204);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
