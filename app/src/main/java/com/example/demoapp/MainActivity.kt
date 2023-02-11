package com.example.demoapp


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.squareup.picasso.Picasso
import org.json.JSONObject



private lateinit var textViewTitle: TextView
private lateinit var imageViewThumbnail: ImageView
private lateinit var textViewAverageRating: TextView
private lateinit var textViewRatingCount: TextView
private lateinit var textViewRetailPrice: TextView
private lateinit var textViewDuration: TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewTitle = findViewById(R.id.title)
        imageViewThumbnail = findViewById(R.id.thumbnail)
        textViewAverageRating = findViewById(R.id.rating)
        textViewRatingCount = findViewById(R.id.rating_count)
        textViewRetailPrice = findViewById(R.id.retail_price)
        textViewDuration = findViewById(R.id.duration)
        fetchTourData()

    }

    private fun fetchTourData() {
        val url = "https://create.cliomuseapp.com/tour.json"

        Fuel.get(url).responseString { _, _, result ->
            result.fold({ data ->
                val tour = parseTourData(data)
                Log.d("TourData", tour.toString())
            }, { error ->
                Log.e("TourData", error.message.toString())
            })
        }
    }
    private fun parseTourData(data: String): TourData {
        val tourData = JSONObject(data)

        val sku = tourData.getString("sku")
        val title = tourData.getString("title")
        val header_image = tourData.getString("header_image")
        val thumbnail = tourData.getString("thumbnail")
        val permalink = tourData.getString("permalink")
        val average_rating = tourData.getString("average_rating")
        val rating_count = tourData.getInt("rating_count")
        val retail_price = tourData.getString("retail_price")
        val sales_price = tourData.getString("sales_price")
        val duration = tourData.getString("duration")

        runOnUiThread {

            textViewTitle.text = title
            Picasso.get().load(thumbnail).into(imageViewThumbnail)
            textViewAverageRating.text = average_rating +"/5"
            textViewRatingCount.text = ("("+ rating_count.toString()+")")
            textViewRetailPrice.text = retail_price
            textViewDuration.text= duration + " minutes"
        }

        return TourData(sku, title, header_image, thumbnail, permalink, average_rating, rating_count, retail_price, sales_price, duration)
    }

}
