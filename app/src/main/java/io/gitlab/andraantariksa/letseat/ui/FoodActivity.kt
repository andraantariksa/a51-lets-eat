package io.gitlab.andraantariksa.letseat.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.gitlab.andraantariksa.letseat.R
import io.gitlab.andraantariksa.letseat.databinding.ActivityFoodDetailBinding
import io.gitlab.andraantariksa.letseat.domain.models.Food

class FoodActivity : AppCompatActivity() {
    private var _binding: ActivityFoodDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFoodDetailBinding.inflate(layoutInflater)

        setupView()

        setContentView(binding.root)
    }

    private fun setupView() {
        intent.getParcelableExtra<Food>("food")?.let { food ->
            Glide.with(this)
                .load(food.imageUrl)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imageViewFood)
            binding.textViewFoodName.text = food.name
            binding.textViewFoodPrice.text = food.price
            binding.textViewFoodDescription.text = food.description
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}