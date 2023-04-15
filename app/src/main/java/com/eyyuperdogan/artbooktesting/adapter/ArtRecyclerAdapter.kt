package com.eyyuperdogan.artbooktesting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.eyyuperdogan.artbooktesting.R
import com.eyyuperdogan.artbooktesting.roomdb.Art
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide:RequestManager
):RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

        private val diffUtil=object :DiffUtil.ItemCallback<Art>(){
            override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
                return oldItem==newItem
            }

        }
        private val recylerListDiffer=AsyncListDiffer(this,diffUtil)
        var artsList:List<Art>
         get() = recylerListDiffer.currentList
         set(value) = recylerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView=holder.itemView.findViewById<ImageView>(R.id.imageArtRow)
        val artName=holder.itemView.findViewById<TextView>(R.id.txtNameArtRow)
        val artistName=holder.itemView.findViewById<TextView>(R.id.txtArtisNameArtRow)
        val year=holder.itemView.findViewById<TextView>(R.id.txtYearArtRow)


        val art=artsList[position]
        holder.itemView.apply {
            glide.load(art.imageUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CircleCrop())
                .into(imageView)

            artName.text = "Name: ${art.name}"
            artistName.text = "Artist Name: ${art.artistName}"
            year.text = "Year: ${art.year}"
        }

    }

    override fun getItemCount(): Int {
       return artsList.size
    }
}