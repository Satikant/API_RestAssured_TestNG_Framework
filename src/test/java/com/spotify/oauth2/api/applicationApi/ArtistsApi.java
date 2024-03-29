package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.ARTISTS;
import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class ArtistsApi {
    public static Response get(String artistsId){
        return RestResource.get(ARTISTS + "/" + artistsId, getToken());
    }
}
