package iamdhariot.github.retrofitphpapp.fragments

import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.apis.APIService
import iamdhariot.github.retrofitphpapp.essentials.BASE_URL
import iamdhariot.github.retrofitphpapp.helper.SharedPrefManager
import iamdhariot.github.retrofitphpapp.models.Result
import iamdhariot.github.retrofitphpapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var buttonUpdate: Button
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewStatus: TextView
    private var gender: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextFullName = view.findViewById(R.id.edit_text_full_name)
        editTextEmail = view.findViewById(R.id.edit_text_email)
        editTextPassword = view.findViewById(R.id.edit_text_password)
        radioGroupGender = view.findViewById(R.id.radio_group_gender)
        buttonUpdate= view.findViewById(R.id.button_update)

        // for status
        linearLayout = view.findViewById(R.id.linear_layout)
        progressBar = view.findViewById(R.id.progress_bar)
        textViewStatus = view.findViewById(R.id.text_view_status)

        // setting the current  user data to fields
        val user: User = SharedPrefManager.getInstance(context).getUser()
        editTextFullName.setText(user.name)
        editTextEmail.setText(user.email)
        editTextPassword.setText("0000")
        if(user.gender.equals("male",true)){
            radioGroupGender.check(R.id.radio_button_male)
        }else{
            radioGroupGender.check(R.id.radio_button_female)
        }

        // getting the checked radio button
        radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton: RadioButton = group.findViewById(checkedId)
            gender = checkedRadioButton.text.toString()

        }
        buttonUpdate.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password  = editTextPassword.text.toString().trim()
            val name = editTextFullName.text.toString().trim()
            val gender: String = gender
            updateUser(name,email,password,gender)
        }
    }

    private fun updateUser(name: String, email: String, password: String, gender: String) {
        showProgressBar("Updating...",true)

        val retrofit: Retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiService: APIService = retrofit.create(APIService::class.java)

        val user: User = User(SharedPrefManager.getInstance(context).getUser().id, name, email, password, gender)
        val call: Call<Result> = apiService.updateUser(
                user.id,
                user.name,
                user.email,
                user.password,
                user.gender
        )

        call.enqueue(object: Callback<Result>{
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                showProgressBar("",false)
                if(response!=null){
                    Toast.makeText(context, response.body().message,Toast.LENGTH_LONG).show()
                    if(!response.body().error){
                        // if no error then set new updated user data to shared preference
                            SharedPrefManager.getInstance(context).userLogin(response.body().user)
                    }
                }

            }
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                showProgressBar("",false)
                Toast.makeText(context, t?.message, Toast.LENGTH_LONG).show()
            }
        })

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