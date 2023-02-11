package com.example.demoapp

import org.json.JSONObject
import android.util.Log
import com.github.kittinunf.fuel.Fuel

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    private val url = "https://create.cliomuseapp.com/tour.json"

    override fun fetchTourData() {
        Fuel.get(url).responseString { _, _, result ->
            result.fold({ data ->
                val tourData = parseTourData(data)
                Log.d("TourData", tourData.toString())
                view.showTourData(tourData)
            }, { error ->
                view.showErrorMessage(error.message.toString())
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

        return TourData(sku, title, header_image, thumbnail, permalink, average_rating, rating_count, retail_price, sales_price, duration
        )
    }
}
