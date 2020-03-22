package com.example.fragspotify.Interfaces;

import com.example.fragspotify.SpotifyClasses.NewRelease;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface classinterface
{
@GET("v1/browse/new-releases?country=SE&limit=10&offset=0")
@Headers("Authorization: Bearer BQCuLAJp8vgez0xzrQwqBbbXSqSeZqdff9bJPSPOqEL7OK52W1lN_Ny3dSC38HxAnGr_X7_o5Fw435vJvXCTcNIBEkCanW7Yp-ylFrQWTsUUfw5n4Gz4VIbUFfoyXrmP836lmoXnv4mXB-8WRX5l-E11HXSjOAPP-QFjLXgvNbZ3sPvgiHu8ElQMU7TQfiqF")
public Call<NewRelease> getNewRelease();

}
