package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;

import com.spotify.oauth2.api.applicationApi.ArtistsApi;
import com.spotify.oauth2.pojo.Artists.Artists;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ArtistTests extends BaseTest{

    @Test
    public void FetchArtist(){
        Artists requestArtistslist = artistsBuilder("Pitbull");
        Response response = ArtistsApi.get(DataLoader.getInstance().getArtistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertArtistsEqual(response.as(Artists.class), requestArtistslist);
    }
    

    @Step
    public Artists artistsBuilder(String name){
        return Artists.builder().
                name(name).
//                description(description).
//                _public(_public).
                build();
    }
    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertArtistsEqual(Artists responseArtistslist, Artists requestArtistslist){
        assertThat(responseArtistslist.getName(), equalTo(requestArtistslist.getName()));
        assertThat(responseArtistslist.getType(),equalTo(requestArtistslist.getType()));
//        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
//        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }
}
