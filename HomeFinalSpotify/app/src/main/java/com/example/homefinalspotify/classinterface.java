package com.example.homefinalspotify;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface classinterface
{
@GET("v1/browse/new-releases?country=SE&limit=10&offset=0")
@Headers("Authorization: Bearer BQALHqkbdTdN1ywcHxGTavpv4dupt5VKlQr3gMGn3O27Xw085FXWPlohLmRjsOkt_FJ-FC4UKQObSvv5bFdU3XeHf2iGyYViRN2jow3z9gnM3LwHXO7fcTLia25Wp0KXPd0q_tG7HrHMkZHyE90wN4yBI5uQdzq6rCdC2VtrfQ6wiPvRponBTU2CsZnuoHaNu-wwagsaYeF1el6R79EYZAapq2zDWH0i_BG453Qb21CWmnlRn6jnRdgjSVlD29EfohRLLEXeZeqwTbAou2CiXtoJ3D1iwy-VUA")
public Call<NewRelease> getNewRelease();

}
