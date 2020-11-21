package iamdhariot.github.retrofitphpapp.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iamdhariot.github.retrofitphpapp.R
import iamdhariot.github.retrofitphpapp.models.Message

class MessageAdapter(private val messages: ArrayList<Message>, val context: Context) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
                .from(context)
                .inflate(R.layout.message_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: Message = messages[position]
        holder.textViewName.text = message.from
        holder.textViewTitle.text = message.title
        holder.textViewMessage.text = message.message
        holder.textViewSentTime.text = message.sent
    }
    override fun getItemCount(): Int {
       return messages.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewMessage: TextView = itemView.findViewById(R.id.text_view_message)
        val textViewSentTime: TextView = itemView.findViewById(R.id.text_view_sent_time)


    }
}