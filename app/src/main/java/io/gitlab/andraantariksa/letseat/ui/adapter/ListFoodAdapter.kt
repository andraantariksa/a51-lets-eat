package io.gitlab.andraantariksa.letseat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.gitlab.andraantariksa.letseat.domain.models.Food
import io.gitlab.andraantariksa.letseat.R
import io.gitlab.andraantariksa.letseat.databinding.FoodRowBinding

class ListFoodAdapter(
    private val listFood: List<Food>,
    private val onFoodClick: (Food, Int, FoodRowBinding) -> Unit
) : RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FoodRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val food = listFood[position]

        Glide.with(holder.itemView.context)
            .load(food.imageUrl)
            .apply(RequestOptions().override(55, 55))
            .into(holder.binding.imageViewFood)
        holder.binding.textViewFoodName.text = food.name
        holder.binding.textViewFoodPrice.text = "$${food.price}"
        holder.binding.root.setOnClickListener {
            onFoodClick(food, position, holder.binding)
        }
    }

    inner class ListViewHolder(val binding: FoodRowBinding) :
        RecyclerView.ViewHolder(binding.root)

}