package com.example.vahana.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vahana.MainActivity
import com.example.vahana.R
import kotlinx.coroutines.NonDisposableHandle.parent

class RecyclerAdapter(
    private var titles: List<String>,
    private var country: List<String>,
    private var links: List<String>
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_cname)
        val itemCountry: TextView = itemView.findViewById(R.id.tv_ccountry)
        val itemlinks: TextView = itemView.findViewById(R.id.tv_clinks)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(itemView.context, Uri.parse(links[position]))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemCountry.text = country[position]
        holder.itemlinks.text = links[position]

    }
}