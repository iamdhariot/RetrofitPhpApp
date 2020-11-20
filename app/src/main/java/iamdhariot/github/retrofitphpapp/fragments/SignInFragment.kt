package iamdhariot.github.retrofitphpapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import iamdhariot.github.retrofitphpapp.essentials.isEmailValid
import iamdhariot.github.retrofitphpapp.essentials.isPasswordValid
import iamdhariot.github.retrofitphpapp.activities.NavDrawerActivity
import iamdhariot.github.retrofitphpapp.R

class SignInFragment : Fragment() {
    private lateinit var buttonSignIn:Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in,parent,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextEmail  = view.findViewById(R.id.edit_text_email)
        editTextPassword = view.findViewById(R.id.edit_text_password)
        buttonSignIn = view.findViewById(R.id.button_sign_in)

        buttonSignIn.setOnClickListener {
            // perform sign in if validations are true
            val email = editTextEmail.text.toString().trim()
            val password  = editTextPassword.text.toString().trim()
            if(!validation(email, password)) {
                //signIn()
                Toast.makeText(activity,"Perform sign in",Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, NavDrawerActivity::class.java))
                activity?.finish()
            }
        }
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

}
