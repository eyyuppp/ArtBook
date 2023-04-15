package com.eyyuperdogan.artbooktesting.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.eyyuperdogan.artbooktesting.R
import com.eyyuperdogan.artbooktesting.roomdb.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager
):RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {
    class ImageViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
        private var onItemClicListener:((String)->Unit)?=null
        private val diffUtil=object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem==newItem
            }

        }
        private val recylerListDiffer= AsyncListDiffer(this,diffUtil)
        var imageList:List<String>
            get() = recylerListDiffer.currentList
            set(value) = recylerListDiffer.submitList(value)

     fun setClick(listener:(String) -> Unit){
         onItemClicListener=listener
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
         val view=LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
         return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageView=holder.itemView.findViewById<ImageView>(R.id.singletionImageView)
        val url=imageList[position]

        holder.itemView.apply {

            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClicListener ?.let {
                    it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}