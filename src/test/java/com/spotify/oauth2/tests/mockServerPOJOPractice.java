package com.spotify.oauth2.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.oauth2.pojo.SimplePOJOPractice;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class mockServerPOJOPractice {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token ="";
    @BeforeClass
    public void beforeclass(){
        RequestSpecBuilder requestspecbuilder = new RequestSpecBuilder().
                setBaseUri("https://8da8dff4-1739-4f02-928e-9fe11a2b6d54.mock.pstmn.io").
                setContentType(ContentType.JSON).log(LogDetail.ALL);

        requestSpecification = requestspecbuilder.build();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }
    @Test
    public void simplepojo() throws JsonProcessingException {
        SimplePOJOPractice simplepojoprcatice = new SimplePOJOPractice("value1","value2");
        SimplePOJOPractice deserializedpojo = given().body(simplepojoprcatice).
        when().post("/simplepostpojo").
        then().spec(responseSpecification).extract().response().as(SimplePOJOPractice.class);

        ObjectMapper objectmapper = new ObjectMapper();
        String deserilazedpojoString = objectmapper.writeValueAsString(deserializedpojo);//Actual JSON //Serialization = converting java object to JSON
        String SimplePOJOStr = objectmapper.writeValueAsString(simplepojoprcatice);//expected JSON
        assertThat(objectmapper.readTree(deserilazedpojoString), equalTo(objectmapper.readTree(SimplePOJOStr)));

    }
}
