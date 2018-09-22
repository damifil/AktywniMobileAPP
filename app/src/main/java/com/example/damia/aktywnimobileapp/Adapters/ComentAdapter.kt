package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.MODEL.Comment
import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.event_rating_item.view.*
import java.util.ArrayList
import android.text.TextWatcher
import android.widget.RatingBar
import android.text.Editable

class ComentAdapter(val items: ArrayList<Comment>, val context: Context): RecyclerView.Adapter<ViewHolderComment>()
{
    override fun onBindViewHolder(holder: ViewHolderComment, position: Int) {

        holder.rating.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser -> items[position].Rate=(rating*2).toInt() }
        holder.coment.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                items[position].describe=s.toString()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComment {
        return ViewHolderComment(LayoutInflater.from(context).inflate(R.layout.event_rating_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolderComment (view: View) : RecyclerView.ViewHolder(view) {
    val rating = view.ratingBar2
    val name= view.userName
    val coment=view.userComment
}


