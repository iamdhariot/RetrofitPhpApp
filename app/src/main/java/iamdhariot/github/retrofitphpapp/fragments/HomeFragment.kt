package iamdhariot.github.retrofitphpapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.apis.APIService
import iamdhariot.github.retrofitphpapp.essentials.BASE_URL
import iamdhariot.github.retrofitphpapp.helper.UserAdapter
import iamdhariot.github.retrofitphpapp.models.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewUsers = view.findViewById(R.id.recycler_view_users)
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.layoutManager = LinearLayoutManager(activity)
        Toast.makeText(activity,"This is home fragment",Toast.LENGTH_LONG).show()
        getAllUsers()
    }

    private fun getAllUsers(){
        // Getting all users using retrofit
        val retrofit: Retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiService: APIService = retrofit.create(APIService::class.java)
        val call: Call<Users> = apiService.getUsers()
        call.enqueue(object: Callback<Users>{
            override fun onResponse(call: Call<Users>?, response: Response<Users>?) {
                if(response!=null){
                    // setting the users to the adapter
                   // Toast.makeText(activity,response.body().users.toString(),Toast.LENGTH_LONG).show()
                    userAdapter = UserAdapter(response.body().users, activity)

                    recyclerViewUsers.adapter = userAdapter
                }
            }
            override fun onFailure(call: Call<Users>?, t: Throwable?) {
                Toast.makeText(activity,t?.message,Toast.LENGTH_LONG).show()
            }

        })
    }

}