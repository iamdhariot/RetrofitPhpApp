package iamdhariot.github.retrofitphpapp.helper

import android.content.Context
import android.content.SharedPreferences
import iamdhariot.github.retrofitphpapp.essentials.*

import iamdhariot.github.retrofitphpapp.models.User
/**
 * On the successful registration(user sign up), we will make the user automatically logged in.
 * For this we will use the SharedPreferences to store user data
 * */
class SharedPrefManager private constructor(context: Context){
    companion object{
        private var INSTANCE:SharedPrefManager? = null
        private lateinit var mContext: Context
        @Synchronized
        fun getInstance(context: Context?): SharedPrefManager{
            if(INSTANCE == null){
                INSTANCE = SharedPrefManager(context!!)
            }
            return INSTANCE!!
        }
    }

    init {
        mContext = context
    }

    fun userLogin(user: User):Boolean{
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_USER_NAME,user.name)
        editor.putString(KEY_USER_EMAIL, user.email)
        editor.putString(KEY_USER_GENDER, user.gender)
        editor.apply()
        return true
    }
    fun isLoggedIn(): Boolean{
        val sharedPreference: SharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        if(sharedPreference.getString(KEY_USER_EMAIL, null) != null)
            return true
        return false
    }

    fun getUser(): User{
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return User(sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null)!!,
                sharedPreferences.getString(KEY_USER_EMAIL, null)!!,
                sharedPreferences.getString(KEY_USER_GENDER,null)!!
        )
    }
    fun logout(): Boolean{
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true

    }
}



