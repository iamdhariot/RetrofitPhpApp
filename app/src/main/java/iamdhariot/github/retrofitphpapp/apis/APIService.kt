package iamdhariot.github.retrofitphpapp.apis

import iamdhariot.github.retrofitphpapp.models.Result
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    // the register call
    @FormUrlEncoded
    @POST("register")
    fun createUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String
    ): Call<Result>

}

