package io.gitlab.andraantariksa.letseat

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        val foodName = intent.getStringExtra("name")
        val foodPrice = intent.getStringExtra("price")
        val foodImageUrl = intent.getStringExtra("imageUrl")
        val foodDescription = intent.getStringExtra("description")

        val textViewFoodName : TextView = findViewById(R.id.text_view_food_name)
        val textViewFoodPrice : TextView = findViewById(R.id.text_view_food_price)
        val imageViewFoodImage : ImageView = findViewById(R.id.image_view_food_image)
        val textViewFoodDescription : TextView = findViewById(R.id.text_view_food_description)

        Glide.with(this)
            .load(foodImageUrl)
            .apply(RequestOptions().override(55, 55))
            .into(imageViewFoodImage)
        textViewFoodName.text = foodName
        textViewFoodPrice.text = foodPrice
        textViewFoodDescription.text = foodDescription
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}