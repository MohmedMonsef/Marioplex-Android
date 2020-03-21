package pojo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EndPointAPI {
    String token = "BQDmQmNO1R_GcvTF88Zn6yiJUWWVl8xImeiPUW_qQkzAbPFp4kSGwu0Z_Z-M1fggdxlwQ8k144XSNX1UMG3mSIf4_JR_Ju_Z3ZBLTZjQjV29YVbcR-n1kKkFxf6JWZGIp5cBcIx-SxbFEuDsrdln8FKLVVBUtgqXjeQFUl-RGvyzC3EnurwH6fGr97HxQaDz";

    @GET("v1/tracks/3n3Ppam7vgaVa1iaRUc9Lp")
    @Headers("Authorization: Bearer "+token)
    Call<Track> getATrack();

    @GET("v1/tracks")
    @Headers("Authorization: Bearer "+token)
    Call<Tracks> getTracks(@Query("ids") String tracksid);

    @PUT("me/like")
    @Headers("Authorization: Bearer "+token)
    Call<ResponseBody> LikeATrack(@Query("id") String id);

    @DELETE("me/unlike")
    @Headers("Authorization: Bearer "+token)
    Call<ResponseBody> unLikeATrack(@Query("id") String id);


}
