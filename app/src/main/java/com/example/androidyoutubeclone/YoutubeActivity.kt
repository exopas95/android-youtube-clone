package com.example.androidyoutubeclone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.androidyoutubeclone.databinding.ActivityYoutubeBinding
import com.example.androidyoutubeclone.databinding.ItemRecyclerYoutubeContentViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeActivity : AppCompatActivity() {
    lateinit var glide: RequestManager
    lateinit var binding: ActivityYoutubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        glide = Glide.with(this)

        (application as MasterApplication).service.getYoutubeList()
            .enqueue(object : Callback<ArrayList<Youtube>> {

                override fun onResponse(
                    call: Call<ArrayList<Youtube>>,
                    response: Response<ArrayList<Youtube>>
                ) {
                    if (response.isSuccessful) {
                        val youtubeList = response.body()
                        val adapter = youtubeList?.let {
                            YoutubeAdapter(
                                it,
                                glide
                            )
                        }
                        binding.contentList.adapter = adapter
                        binding.contentList.layoutManager =
                            LinearLayoutManager(
                                this@YoutubeActivity,
                                LinearLayoutManager.VERTICAL, false
                            )
                    }
                }

                override fun onFailure(call: Call<ArrayList<Youtube>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}

class YoutubeAdapter(
    private var youtubeList: ArrayList<Youtube>,
    var glide: RequestManager
) : RecyclerView.Adapter<YoutubeAdapter.YoutubeHolder>() {

    inner class YoutubeHolder(binding: ItemRecyclerYoutubeContentViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val youtubeTitle: TextView = binding.title
        val youtubeContent: TextView = binding.content
        val youtubeThumbnail: ImageView = binding.thumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeHolder {
        val binding = ItemRecyclerYoutubeContentViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return YoutubeHolder(binding)
    }

    override fun onBindViewHolder(holder: YoutubeHolder, position: Int) {
        holder.youtubeTitle.text = youtubeList[position].title
        holder.youtubeContent.text = youtubeList[position].content
        glide.load(youtubeList[position].thumbnail).into(holder.youtubeThumbnail)

        holder.youtubeThumbnail.setOnClickListener {
            val intent = Intent(holder.youtubeThumbnail.context, YoutubeDetailActivity::class.java)
            intent.putExtra("video_url", youtubeList[position].video)
            ContextCompat.startActivity(holder.youtubeThumbnail.context, intent, null)
        }
    }

    override fun getItemCount(): Int = youtubeList.size
}