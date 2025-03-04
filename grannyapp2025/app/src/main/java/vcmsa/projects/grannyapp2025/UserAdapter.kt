package vcmsa.projects.grannyapp2025
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.concurrent.Executors
import java.util.logging.Handler

// Here the UserAdapter.UserAdapter is essentially returning the reference of the
// instantiated ListAdapter.

class UserAdapter : ListAdapter<User,UserAdapter.UserAdapter>(UserViewHolder()) {

    // This UserAdapter sub class is boiler plate and must be added as such
    // Note that the class name here is  the name of the superclass
    class UserAdapter(view: View) : RecyclerView.ViewHolder(view){

    }

    // We override the onCreateViewHolder which is how we actually hook into
    // and inject our custom layout into the default listadapter view
    // essentially, we override the default appearance of the listadapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter {
        val inflater = LayoutInflater.from(parent.context)
        return UserAdapter(inflater.inflate(
            R.layout.userlayout, parent,false
        ))
    }

    // This is essentially the method where the listadapter binds or connects
    // to the datasource and then start printing out the items.
    override fun onBindViewHolder(holder: UserAdapter, position: Int) {

        // We retrieve the index / position of where we are in the list
        val user = getItem(position)

        // We then set the data for the current GUI entry by referencing the current
        // indexed object
        holder.itemView.findViewById<TextView>(R.id.txtNameUser).text = user.Name
        holder.itemView.findViewById<TextView>(R.id.txtPasswordUser).text = user.Password

        // executor and handler to do URL handling on different thread
        // See welcome.kt

        val executor = Executors.newSingleThreadExecutor()
        val handler = android.os.Handler(Looper.getMainLooper())

        var image : Bitmap? = null
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imgPP)

        executor.execute{
            val imageURL = user.imageURL
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                Log.d("monte","Image in text " + imageURL.toString())
                handler.post{
                    Log.d("monte","Image Added")
                    imageView.setImageBitmap(image)
                }
            }catch (e:java.lang.Exception){

                Log.d("monte","Error happened... " + e.toString())
            }

        }

    }
}


class UserViewHolder : DiffUtil.ItemCallback<User>(){

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.Name == newItem.Name

    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}