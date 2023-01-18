package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;

public abstract class JiraApiTestCase {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeTest
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
}
