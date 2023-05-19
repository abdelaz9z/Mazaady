package com.mazaady.mazaady.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.mazaady.mazaady.databinding.ActivityAuctionDetailsBinding

class AuctionDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuctionDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuctionDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // initialization image slider
        initImageSlider(binding.imageSlider)
    }

    private fun initImageSlider(imageSlider: ImageSlider) {
        val imageList = ArrayList<SlideModel>() // Create image list

        // imageList.add(SlideModel("String Url" or R.drawable)
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(
            SlideModel(
                "https://mazaady-test.s3.us-east-2.amazonaws.com/categories/1/dsMm47dXgwUnnq7LnbgXwVTuNSqAXBYpHUdnCNfI.png",
                "The animal population decreased by 58 percent in 42 years.",
                ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://mazaady-test.s3.us-east-2.amazonaws.com/categories/1/o0XfOKPJ8qbPv3eTGohQ8lXafJ5nvmOJSQYIFNHW.png",
                "Elephants and tigers may become extinct.",
                ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://mazaady-test.s3.us-east-2.amazonaws.com/categories/1/2LpHboKSmYu9YE55pJWuRpEZkQ8qmeXzVRU6LnZ1.png",
                "And people do that.",
                ScaleTypes.FIT
            )
        )

        imageSlider.setImageList(imageList)
    }
}