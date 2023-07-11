package com.example.demoapplication

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CardAdapter(private val context: Context, db: AppDatabase) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    var dataModelArrayList: List<User> = emptyList()
    private val database = db.userDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: User = dataModelArrayList[position]
        holder.name.text = model.name
        holder.country.text = "country-" + " " + model.country
        holder.city.text = "city-" + " " + model.city
        holder.email.text = model.email
        holder.mobileNo.text = "Cell-" + " " + model.cell
        Glide.with(holder.itemView)
            .load(model.picture)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.profileImage)
        performClick(holder,position)

    }

    private fun performClick(holder: CardAdapter.ViewHolder, position: Int) {
        var userToUpdate : User? = database.getUserById(dataModelArrayList[position].name).value
        holder.accept.setOnClickListener {
            holder.accept.visibility=View.GONE
            holder.rejet.visibility = View.GONE
            holder.message.visibility = View.VISIBLE
            holder.message.text = "Member Accepted"
            holder.cardLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
            userToUpdate?.status = "Member Accepted"
            if (userToUpdate != null) {
                database.updateUser(userToUpdate)
            }
        }
        holder.rejet.setOnClickListener {
            holder.accept.visibility=View.GONE
            holder.rejet.visibility = View.GONE
            holder.message.visibility = View.VISIBLE
            holder.cardLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.red))
            holder.message.text = "Member Declined"
            userToUpdate?.status = "Member Declined"
            if (userToUpdate != null) {
                database.updateUser(userToUpdate)
            }
        }

    }

    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView
        val name: TextView
        val country: TextView
        val city: TextView
        val mobileNo: TextView
        val email: TextView
        val accept: Button
        val rejet: Button
        val message: TextView
        val cardLayout : RelativeLayout
        init {
            profileImage = itemView.findViewById(R.id.idProfileImage)
            name = itemView.findViewById(R.id.idName)
            country = itemView.findViewById(R.id.idCountry)
            city = itemView.findViewById(R.id.idCity)
            mobileNo = itemView.findViewById(R.id.idMobileNo)
            email = itemView.findViewById(R.id.idEmail)
            accept = itemView.findViewById(R.id.btn_accept)
            rejet =  itemView.findViewById(R.id.reject)
            message = itemView.findViewById(R.id.message)
            cardLayout = itemView.findViewById(R.id.cardLayout)
        }

    }
}
