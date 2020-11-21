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
import iamdhariot.github.retrofitphpapp.helper.MessageAdapter
import iamdhariot.github.retrofitphpapp.helper.SharedPrefManager
import iamdhariot.github.retrofitphpapp.models.Messages
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessagesFragment : Fragment() {

    private lateinit var recyclerViewMessages:RecyclerView
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewMessages = view.findViewById(R.id.recycler_view_messages)
        recyclerViewMessages.setHasFixedSize(true)
        recyclerViewMessages.layoutManager = LinearLayoutManager(context)

        getMessages();
    }

    // getting all messages using retrofit api call
    private fun getMessages() {
        val retrofit: Retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiService: APIService = retrofit.create(APIService::class.java)
        val call: Call<Messages> = apiService.getMessages(SharedPrefManager.getInstance(context).getUser().id)
        call.enqueue(object: Callback<Messages>{
            override fun onResponse(call: Call<Messages>?, response: Response<Messages>?) {
               if(response!=null){
                   if(activity!=null) {
                       messageAdapter = MessageAdapter(response.body().messages, activity!!)
                       recyclerViewMessages.adapter = messageAdapter
                   }

               }
            }
            override fun onFailure(call: Call<Messages>?, t: Throwable?) {
                Toast.makeText(activity, t?.message, Toast.LENGTH_LONG).show();
            }
        })

    }

}