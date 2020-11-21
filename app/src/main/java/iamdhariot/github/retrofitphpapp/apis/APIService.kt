package iamdhariot.github.retrofitphpapp.apis

import iamdhariot.github.retrofitphpapp.models.Result
import iamdhariot.github.retrofitphpapp.models.Users
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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

    // the sign in call
    @FormUrlEncoded
    @POST("login")
    fun userLogin(
            @Field("email") email: String,
            @Field("password") password: String

    ):Call<Result>

    // get all users
    @GET("users")
    fun getUsers():Call<Users>


}

