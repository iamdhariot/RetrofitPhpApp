package iamdhariot.github.retrofitphpapp.apis

import iamdhariot.github.retrofitphpapp.models.MessageResponse
import iamdhariot.github.retrofitphpapp.models.Messages
import iamdhariot.github.retrofitphpapp.models.Result
import iamdhariot.github.retrofitphpapp.models.Users
import retrofit2.Call
import retrofit2.http.*

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

    // to send message
    @FormUrlEncoded
    @POST("sendmessage")
    fun sendMessage(
            @Field("from") from: Int,
            @Field("to") to: Int,
            @Field("title") title: String,
            @Field("message") message: String
    ):Call<MessageResponse>

    // to update user profile
    @FormUrlEncoded
    @POST("update/{id}")
    fun updateUser(
            @Path("id") id: Int,
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("gender") gender: String
    ) :Call<Result>

    //getting all messages for logged in user
    @GET("messages/{id}")
    fun getMessages(
            @Path("id") id: Int
    ):Call<Messages>

}

