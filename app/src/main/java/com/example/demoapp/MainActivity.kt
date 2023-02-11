package com.example.demoapp


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var textViewTitle: TextView
    private lateinit var imageViewThumbnail: ImageView
    private lateinit var textViewAverageRating: TextView
    private lateinit var textViewRatingCount: TextView
    private lateinit var textViewRetailPrice: TextView
    private lateinit var textViewDuration: TextView
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewTitle = findViewById(R.id.title)
        imageViewThumbnail = findViewById(R.id.thumbnail)
        textViewAverageRating = findViewById(R.id.rating)
        textViewRatingCount = findViewById(R.id.rating_count)
        textViewRetailPrice = findViewById(R.id.retail_price)
        textViewDuration = findViewById(R.id.duration)
        presenter = MainPresenter(this)
        presenter.fetchTourData()
    }

    override fun showTourData(tourData: TourData) {
        runOnUiThread {
            textViewTitle.text = tourData.title
            Picasso.get().load(tourData.thumbnail).into(imageViewThumbnail)
            textViewAverageRating.text = tourData.averageRating + "/5"
            textViewRatingCount.text = "(" + tourData.ratingCount.toString() + ")"
            textViewRetailPrice.text = tourData.retailPrice
            textViewDuration.text = tourData.duration + " minutes"
        }
    }
    override fun showErrorMessage(error: String) {
        Log.e("TourData", error)
    }
}

interface MainContract {
    interface View {
        fun showTourData(tourData: TourData)
        fun showErrorMessage(error: String)
    }

    interface Presenter {
        fun fetchTourData()
    }

}
