package iamdhariot.github.retrofitphpapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import iamdhariot.github.retrofitphpapp.R

class MainFragment: Fragment(){
    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main,parent,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignIn = view.findViewById(R.id.button_sign_in)
        buttonSignUp = view.findViewById(R.id.button_sign_up)
        buttonOnClick()
    }
    private fun buttonOnClick() {
        buttonSignIn.setOnClickListener {
            // start the sign in fragment
            //startActivity(Intent(MainActivity@ this, SignInFragment::class.java))
            val signInFragment = SignInFragment()
                activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.frame_layout, signInFragment, signInFragment.tag)
                        ?.addToBackStack(MainFragment().tag)
                        ?.commit()

        }
        buttonSignUp.setOnClickListener {
            // start the sign up fragment
            val signUpFragment = SignUpFragment()
            activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.frame_layout, signUpFragment, signUpFragment.tag)
                    ?.addToBackStack(MainFragment().tag)
                    ?.commit()
        }
    }
}