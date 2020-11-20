package iamdhariot.github.retrofitphpapp.fragments

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.apis.APIService
import iamdhariot.github.retrofitphpapp.essential.BASE_URL
import iamdhariot.github.retrofitphpapp.essential.isEmailValid
import iamdhariot.github.retrofitphpapp.essential.isNameValid
import iamdhariot.github.retrofitphpapp.essential.isPasswordValid
import iamdhariot.github.retrofitphpapp.models.Result
import iamdhariot.github.retrofitphpapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignUpFragment: Fragment(){
    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var buttonSignUp: Button
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewStatus:TextView
    private var gender: String = ""

    // for inflate the sign up layout
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, parent, false)
    }

    // for layout components
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextFullName = view.findViewById(R.id.edit_text_full_name)
        editTextEmail = view.findViewById(R.id.edit_text_email)
        editTextPassword = view.findViewById(R.id.edit_text_password)
        radioGroupGender = view.findViewById(R.id.radio_group_gender)
        buttonSignUp = view.findViewById(R.id.button_sign_up)

        // for status
        linearLayout = view.findViewById(R.id.linear_layout)
        progressBar = view.findViewById(R.id.progress_bar)
        textViewStatus = view.findViewById(R.id.text_view_status)

        // getting the checked radio button
        radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton: RadioButton = group.findViewById(checkedId)
            gender = checkedRadioButton.text.toString()
        }

        buttonSignUp.setOnClickListener {
             // perform sign up if validations are true
            val email = editTextEmail.text.toString().trim()
            val password  = editTextPassword.text.toString().trim()
            val name = editTextFullName.text.toString().trim()
            val gender: String = gender
            signUp(email, password, name, gender)
            /*if(!validation(email, password, name, gender)) {
                // here  we are going to perform sign up
                signUp(email, password, name, gender)


            }*/
        }
    }

    // perform sign up using retrofit
    private fun signUp(email: String, password: String, name: String, gender: String) {
        // defining a progress dialog to show while signing up
        showProgressBar("Signing up...", true)

        // Building retrofit object
        val retrofit: Retrofit  = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // defining retrofit api service
        val apiService: APIService = retrofit.create(APIService::class.java)

        // Defining the user object as we need to pass it with the call
        val user = User(name, email, password, gender)

        // defining the call
        val call:Call<Result> = apiService.createUser(
            user.name,
            user.email,
            user.password,
            user.gender
        )

        // calling the api
        call.enqueue(object: Callback<Result> {
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                // hide progress bar
                showProgressBar("",false)
                // Displaying  the message from the response as toast
                Toast.makeText(activity,response?.body()?.message,Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                // hide progress bar
                showProgressBar("",false)
                // Displaying  the error
                Toast.makeText(activity,t?.message,Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun validation(email: String, password: String, name: String, gender: String): Boolean {
        var cancel = false
        if(name.isEmpty()){
            editTextFullName.error = resources.getString(R.string.error_field_required)
            cancel = true
        }else if(!name.isNameValid()){
            editTextFullName.error = resources.getString(R.string.error_invalid_full_name)
            cancel = true
        }
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

        if(gender.isEmpty()){
            cancel = true
        }
        return cancel
    }

    private fun showProgressBar(message: String, show: Boolean) {
        // true show progress bar
        if(show) {
            TransitionManager.beginDelayedTransition(linearLayout)
            textViewStatus.text = message
            linearLayout.visibility = VISIBLE
        }else{
            TransitionManager.beginDelayedTransition(linearLayout)
            textViewStatus.text = message
            linearLayout.visibility = GONE
        }

    }

}