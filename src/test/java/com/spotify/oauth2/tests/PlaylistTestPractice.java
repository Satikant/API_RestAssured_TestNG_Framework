package com.spotify.oauth2.tests;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.pojo.PlaylistPractices;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTestPractice {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "BQCxHWNfwQqfIKK_nj_sS_y5A-8DbSEBsXNgvUyCLC-wWa1zP50qY0kQvwRZjZgDsFuEApJDk5TaIpvyNYe3BX-5GSJ_GxbEq26i63zjDSovclaxCxZKQ3eED2KrWlOZOA49CTVdcPZabDdENHXH91504EHwE2rzCIFW9LMxuVt3F6TLlrUn3xPSDRR5cLDYgpuMYkUyYSLOb_I_v_iGF8C8sdFx3LDnu8TOUhmiJvzK9o6nb3TSUmm_KwhOPDvHTOMNAMZE2F0Fd4Du";
    @BeforeClass
    public void beforeclass(){
        RequestSpecBuilder requestspecbuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").setBasePath("/v1").addHeader("Authorization","Bearer "+ access_token).
                setContentType(ContentType.JSON).log(LogDetail.ALL);

        requestSpecification = requestspecbuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();

    }

    @Test(priority = 1, enabled = true)
    public void createPlayList(){
        //Playlist requestplaylist = new Playlist();
        PlaylistPractices requestplaylist = new PlaylistPractices().
                setName("New FAV Playlist2").
                setDescription("New playlist description2"); //Builder pattern in POJO
//        requestplaylist.setName("New FAV Playlist2");
//        requestplaylist.setDescription("New playlist description2");
//        requestplaylist.setPublic(false);

        PlaylistPractices responseplaylist = given(requestSpecification).
                body(requestplaylist).
        when().post("users/31suc5zk6s47f7fbfpbiaidnn33a/playlists").
        then().spec(responseSpecification).assertThat().statusCode(201).
                extract().response().as(PlaylistPractices.class);

                assertThat(responseplaylist.getName(),equalTo(requestplaylist.getName()));
                assertThat(responseplaylist.getDescription(),equalTo(requestplaylist.getDescription()));

}
    @Test(priority = 2, enabled = false)
    public void getAPlayList(){
        given(requestSpecification).
        when().get("playlists/7pFhoYBrDOl7GHTh93pgWX").
        then().spec(responseSpecification).assertThat().statusCode(200).
                body("name",equalTo("New Playlist 1"),
                        "description",equalTo("New playlist description123"));
    }
    @Test(priority = 3, enabled = false)
    public void updatePlayList(){
        String payload= "{\n" +
                "    \"name\": \"Updated Playlist Name!!\",\n" +
                "    \"description\": \"Updated playlist description!!\",\n" +
                "    \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(payload).
                when().put("playlists/7pFhoYBrDOl7GHTh93pgWX").
                then().spec(responseSpecification).assertThat().statusCode(200);

    }
    @Test(priority = 4, enabled = false)
    public void createPlayListWithoutName(){
        String payload= "{\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"New playlist description33\",\n" +
                "    \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(payload).
                when().post("users/31suc5zk6s47f7fbfpbiaidnn33a/playlists").
                then().spec(responseSpecification).assertThat().statusCode(400).
                body("error.status",equalTo(400),"error.message",equalTo("Missing required field: name"));

    }

}
