package iamdhariot.github.retrofitphpapp.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.models.User

class UserAdapter(private val users: ArrayList<User>, val context: Context?): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
                .from(context)
                .inflate(R.layout.user_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val user: User = users[position]
        holder.textViewName.text = user.name
    }

    override fun getItemCount(): Int {
       return users.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
         val imageButtonMessage: ImageButton = itemView.findViewById(R.id.image_button_message)
    }
}