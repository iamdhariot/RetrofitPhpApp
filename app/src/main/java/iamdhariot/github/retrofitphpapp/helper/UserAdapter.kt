package iamdhariot.github.retrofitphpapp.helper


import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.apis.APIService
import iamdhariot.github.retrofitphpapp.essentials.BASE_URL
import iamdhariot.github.retrofitphpapp.models.MessageResponse
import iamdhariot.github.retrofitphpapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAdapter(private val users: ArrayList<User>, val context: Context): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
                .from(context)
                .inflate(R.layout.user_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val user: User = users[position]
        holder.textViewName.text = user.name
        holder.imageButtonMessage.setOnClickListener {
            // creating a dialog to send message
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_send_message,null)
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
            alertDialogBuilder.setView(dialogView)
            val editTextTitle: EditText =  dialogView.findViewById(R.id.edit_text_title);
            val editTextMessage: EditText  = dialogView.findViewById(R.id.edit_text_message)

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Send") {dialog,id->
                        val title: String = editTextTitle.text.toString().trim()
                        val message: String = editTextMessage.text.toString().trim()
                        sendMessage(user.id,title,message);
                            }
                    .setNegativeButton("Cancel") { dialog, id->
                        dialog.cancel()
                    }
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
       return users.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
         val imageButtonMessage: ImageButton = itemView.findViewById(R.id.image_button_message)
    }

    // method to send message to user
    // using retrofit sendmessage api call
    private fun sendMessage(id: Int, title: String, message: String) {
        // showing progress dialog
        val progressDialog: ProgressDialog = ProgressDialog(context)
        progressDialog.setMessage("Sending message...")
        progressDialog.show()
        val retrofit: Retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiService: APIService = retrofit.create(APIService::class.java)
        val call: Call<MessageResponse> = apiService.sendMessage(
                SharedPrefManager.getInstance(context).getUser().id,
                id,
                title,
                message
        )
        call.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>?, response: Response<MessageResponse>?) {
                progressDialog.dismiss()
                if(response!=null)
                    Toast.makeText(context,response.body().message,Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<MessageResponse>?, t: Throwable?) {
                Toast.makeText(context,t?.message,Toast.LENGTH_LONG).show()
            }
        })
    }
}
