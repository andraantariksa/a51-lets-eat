package io.gitlab.andraantariksa.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.gitlab.andraantariksa.letseat.R
import io.gitlab.andraantariksa.letseat.databinding.FragmentMainBinding
import io.gitlab.andraantariksa.letseat.domain.models.Food
import io.gitlab.andraantariksa.letseat.ui.adapter.ListFoodAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    private val foods = MutableLiveData<List<Food>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, parent, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        @Suppress("BlockingMethodInNonBlockingContext")
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val moshi = Moshi.Builder().build()

            val json = resources
                .openRawResource(R.raw.data)
                .bufferedReader()
                .use {
                    val text = it.readText()
                    it.close()
                    text
                }

            val foodList = moshi
                .adapter<ArrayList<Food>>(
                    Types
                        .newParameterizedType(
                            List::class.java,
                            Food::class.java
                        )
                )
                .fromJson(json)!!
            foods.postValue(foodList)
        }

        foods.observe(viewLifecycleOwner) { foods ->
            binding.recyclerViewFood.adapter = ListFoodAdapter(foods) { food, position, holder ->
                val foodIntent = Intent(context, FoodActivity::class.java)
                foodIntent.putExtra("food", food)
                startActivity(foodIntent)
            }
        }
    }
}