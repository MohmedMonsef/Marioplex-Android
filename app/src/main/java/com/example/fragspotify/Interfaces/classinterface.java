package com.example.fragspotify.Interfaces;

import com.example.fragspotify.SpotifyClasses.NewRelease;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface classinterface
{
@GET("v1/browse/new-releases?country=SE&limit=10&offset=0")
@Headers("Authorization: Bearer BQDyRz6H-POb1vzoKKsJ0FqxUkECR8CMrSgDQ9Mc0JdrFQXizQISwYOMZj2lH5aU1kMU34u4tAwjNVu25Bxiv-7yILjqmxDk0B4CCAdoofX4wi938KvzdvdQzQDZhSzosqMKSrorcsZi-En9bUfKxlVORTnLsRuEdFF4LpIA6i86_4cdRQnivqOJUzHEkWd4-NoSCyGAa0djEUxrkr_jz4mnjnWJc5N11nv7BUprywy5kjTBoNbd9hfJ3Py31Voc4ieXJF13KT2Ds3rL6lxUu1VZ7F-yJCqQMA")
public Call<NewRelease> getNewRelease();

}
