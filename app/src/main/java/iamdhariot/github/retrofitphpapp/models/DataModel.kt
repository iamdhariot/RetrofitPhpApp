package iamdhariot.github.retrofitphpapp.models

import com.google.gson.annotations.SerializedName
// primary constructor
data class Result(
        @SerializedName("error") var error: Boolean,
        @SerializedName("message") var message: String,
        @SerializedName("user") var user: User
)
// primary constructor
data class User constructor(
        var id: Int,
        var name: String,
        var email: String,
        var password: String,
        var gender: String
){

    // secondary constructor
    // secondary constructor must call Primary constructor
    // otherwise you get exception Primary constructor call expected
    constructor(name: String, email: String, password: String, gender: String):this(0, name, email, password, gender){
        this.name = name
        this.email = email
        this.password = password
        this.gender = gender
    }
    // secondary constructor
    constructor(id: Int, name: String, email: String, gender: String):this(id, name, email, "", gender){
        this.id = id
        this.name = name
        this.email = email
        this.gender = gender
    }


}

