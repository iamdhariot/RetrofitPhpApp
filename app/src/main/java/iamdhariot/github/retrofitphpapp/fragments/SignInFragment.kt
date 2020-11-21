package iamdhariot.github.retrofitphpapp.fragments

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import iamdhariot.github.retrofitphpapp.essentials.isEmailValid
import iamdhariot.github.retrofitphpapp.essentials.isPasswordValid
import iamdhariot.github.retrofitphpapp.activities.NavDrawerActivity
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.apis.APIService
import iamdhariot.github.retrofitphpapp.essentials.BASE_URL
import iamdhariot.github.retrofitphpapp.helper.SharedPrefManager
import iamdhariot.github.retrofitphpapp.models.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInFragment : Fragment() {
    private lateinit var buttonSignIn:Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewStatus: TextView

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in,parent,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextEmail  = view.findViewById(R.id.edit_text_email)
        editTextPassword = view.findViewById(R.id.edit_text_password)
        buttonSignIn = view.findViewById(R.id.button_sign_in)
        // for status
        linearLayout = view.findViewById(R.id.linear_layout)
        progressBar = view.findViewById(R.id.progress_bar)
        textViewStatus = view.findViewById(R.id.text_view_status)

        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password  = editTextPassword.text.toString().trim()
            // sign in
            signIn(email, password)
            // perform sign in if validations are true
            /*if(!validation(email, password)) {
                //signIn()
                Toast.makeText(activity,"Perform sign in",Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, NavDrawerActivity::class.java))
                activity?.finish()
            }*/
        }
    }

    // here we perform sign in using retrofit login api call
    private fun signIn(email: String, password: String) {
        // show progress dialog
        showProgressBar("Signing in...",true)

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiService: APIService = retrofit.create(APIService::class.java)

        val call: Call<Result> = apiService.userLogin(email, password)

        call.enqueue(object : Callback<Result>{
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                // hide the progressbar
                showProgressBar("",false)
                if(response!=null){
                    // if there is no error
                    if(!response.body().error){
                        // storing the user data in to shared preference
                        // staring the profile activity (i.e NavDrawerActivity)
                        SharedPrefManager.getInstance(activity?.applicationContext)
                                .userLogin(response.body().user)
                        startActivity(Intent(activity,NavDrawerActivity::class.java))
                        activity?.finish()
                    }else{
                        // hide the progressbar
                        Toast.makeText(activity,"Invalid email or password",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                showProgressBar("",false)
                Toast.makeText(activity,t?.message,Toast.LENGTH_SHORT).show()

            }
        })

    }
    private fun validation(email: String, password: String): Boolean {
        var cancel = false
        if(email.isEmpty()){
            editTextEmail.error = resources.getString(R.string.error_field_required)
            cancel = true
        }else if(!email.isEmailValid()){
            editTextEmail.error = resources.getString(R.string.error_invalid_email)
            cancel = true
        }
        if(password.isEmpty()){
            editTextPassword.error = resources.getString(R.string.error_field_required)
            cancel = true
        }else if(!password.isPasswordValid()){
            editTextPassword.error = resources.getString(R.string.error_invalid_password)
            cancel = true
        }
        return cancel
    }

    private fun showProgressBar(message: String, show: Boolean) {
        // true show progress bar
        if(show) {
            TransitionManager.beginDelayedTransition(linearLayout)
            textViewStatus.text = message
            linearLayout.visibility = View.VISIBLE
        }else{
            TransitionManager.beginDelayedTransition(linearLayout)
            textViewStatus.text = message
            linearLayout.visibility = View.GONE
        }

    }
}
