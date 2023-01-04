package api;

import com.epam.reportportal.junit5.ReportPortalExtension;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.PropertyController;

@ExtendWith(ReportPortalExtension.class)
public abstract class BaseApiTestCase {

    RequestSpecification requestSpecification;

    @BeforeEach
    public void initialiseConfig() {
        RestAssured.baseURI = PropertyController.getPropertyByKey("base.url.api");
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + PropertyController.getPropertyByKey("rp.uuid"))
                .addHeader("Accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)
                .build();
    }
}
