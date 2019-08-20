package io.gitlab.andraantariksa.letseat

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import kotlinx.coroutines.*

data class Food(var price : String, var name : String, var imageUrl : String, var description : String)

class ListFoodAdapter(val listFood: ArrayList<Food>) : RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>(), View.OnClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.food_row, parent, false)

        val foodItem: RelativeLayout = view.findViewById(R.id.food_item)
        foodItem.setOnClickListener(this)

        return ListViewHolder(view)
    }

    fun getFoodByName(foodName: String) : Int {
        listFood.forEachIndexed { index, food ->
            if (food.name.equals(foodName)) {
                return index
            }
        }
        return -1
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (price, name, imageUrl, description) = listFood[position]
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imageViewFood)
        holder.textViewFoodName.text = name
        holder.textViewFoodPrice.text = price
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.food_item -> {
                val textViewFoodName : TextView = view.findViewById(R.id.text_view_food_name)
                val foodName = textViewFoodName.getText();

                val foodIndex = this.getFoodByName(foodName.toString())
                val foodObject = listFood[foodIndex]

                val foodPrice = foodObject.price
                val foodImageUrl = foodObject.imageUrl
                val foodDescription = foodObject.description

                val foodIntent = Intent(MainActivity.singleton, FoodActivity::class.java)
                foodIntent.putExtra("name", foodName)
                foodIntent.putExtra("price", foodPrice)
                foodIntent.putExtra("imageUrl", foodImageUrl)
                foodIntent.putExtra("description", foodDescription)
                MainActivity.singleton.startActivity(foodIntent)

//                Log.d("info", "Pressed ${foodNameView.text}")
            }
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewFoodName: TextView = itemView.findViewById(R.id.text_view_food_name)
        var textViewFoodPrice: TextView = itemView.findViewById(R.id.text_view_food_price)
        var imageViewFood: ImageView = itemView.findViewById(R.id.image_view_food_image)
    }

}

class MainFragment : Fragment() {
    private lateinit var fragmentView : View

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.content_main, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onStart()

        prepareMenu()
    }



    fun prepareMenu() = runBlocking {
        launch {
            var foodList: ArrayList<Food>

            val result = Klaxon().parseArray<Food>(
                """
        [
          {
            "price": "${'$'}2.18",
            "name": "Hamburger",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/RedDot_Burger.jpg/250px-RedDot_Burger.jpg",
            "description": "A hamburger (short: burger) is a sandwich consisting of one or more cooked patties of ground meat, usually beef, placed inside a sliced bread roll or bun. The patty may be pan fried, grilled, or flame broiled. Hamburgers are often served with cheese, lettuce, tomato, onion, pickles, bacon, or chiles; condiments such as ketchup, mayonnaise, mustard, relish, or \"special sauce\"; and are frequently placed on sesame seed buns. A hamburger topped with cheese is called a cheese\burger. "
          },
          {
            "price": "${'$'}3.78",
            "name": "Pizza",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/220px-Eq_it-na_pizza-margherita_sep2005_sml.jpg",
            "description": "Pizza (Italian: [ˈpittsa], Neapolitan: [ˈpittsə]) is a savory dish of Italian origin, consisting of a usually round, flattened base of leavened wheat-based dough topped with tomatoes, cheese, and various other ingredients (anchovies, olives, meat, etc.) baked at a high temperature, traditionally in a wood-fired oven. In formal settings, like a restaurant, pizza is eaten with knife and fork, but in casual settings it is cut into wedges to be eaten while held in the hand. Small pizzas are sometimes called pizzettas. "
          },
          {
            "price": "${'$'}5.25",
            "name": "Meatball",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Soup_with_meatballs-01.jpg/220px-Soup_with_meatballs-01.jpg",
            "description": "A meatball is ground meat rolled into a small ball, sometimes along with other ingredients, such as bread crumbs, minced onion, eggs, butter, and seasoning. Meatballs are cooked by frying, baking, steaming, or braising in sauce. There are many types of meatballs using different types of meats and spices. The term is sometimes extended to meatless versions based on vegetables or fish; the latter are commonly known as fishballs. "
          },
          {
            "price": "${'$'}3.01",
            "name": "Klappertaart",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Coconut_custard_klappertaart.jpg/220px-Coconut_custard_klappertaart.jpg",
            "description": "Klappertaart is a Dutch-influenced Indonesian cake originating from Manado, North Sulawesi. It literally means \"coconut cake\" or \"coconut tart\", which is made from flour, sugar, milk, butter, as well as coconut flesh and juice."
          },
          {
            "price": "${'$'}4.37",
            "name": "Tanuki soba",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Tanuki_soba_by_rhosoi_in_Cupertino%2C_CA.jpg/240px-Tanuki_soba_by_rhosoi_in_Cupertino%2C_CA.jpg",
            "description": "Soba (そば or 蕎麦) (/ˈsoʊbə/, Japanese pronunciation: [soba]) is the Japanese name for buckwheat. It usually refers to thin noodles made from buckwheat flour, or a combination of buckwheat and wheat flours (Nagano soba). They contrast to thick wheat noodles, called udon. Soba noodles are served either chilled with a dipping sauce, or in hot broth as a noodle soup."
          },
          {
            "price": "${'$'}3.63",
            "name": "Spaghetti aglio e olio",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Spaghetti_di_Gragnano_e_colatura_di_alici.jpg/304px-Spaghetti_di_Gragnano_e_colatura_di_alici.jpg",
            "description": "Spaghetti (Italian pronunciation: [spaˈɡetti]; sing. spaghetto) is a long, thin, solid, cylindrical pasta. Spaghettoni is a thicker form of spaghetti, while capellini is a very thin spaghetti. It is a staple food of traditional Italian cuisine. Like other pasta, spaghetti is made of milled wheat and water and sometimes enriched with vitamins and minerals. Authentic Italian spaghetti is made from durum wheat semolina, but elsewhere it may be made with other kinds of flour. Usually the pasta is white because refined flour is used, but whole wheat flour may be added. "
          },
          {
            "price": "${'$'}4.11",
            "name": "Ramen",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Soy_ramen.jpg/250px-Soy_ramen.jpg",
            "description": "Ramen (/ˈrɑːmən/) (拉麺, ラーメン rāmen, IPA: [ɾaꜜːmeɴ]) is a Japanese dish with a translation of \"pulled noodles\" (originating in northwest China). It consists of Chinese-style wheat noodles[5] served in a meat or (occasionally) fish-based broth, often flavored with soy sauce or miso, and uses toppings such as sliced pork (叉焼 chāshū), nori (dried seaweed), menma, and scallions. Nearly every region in Japan has its own variation of ramen, such as the tonkotsu (pork bone broth) ramen of Kyushu and the miso ramen of Hokkaido."
          },
          {
            "price": "${'$'}4.94",
            "name": "Satay",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Sate_Ponorogo.jpg/250px-Sate_Ponorogo.jpg",
            "description": "Satay (/ˈsæteɪ/ SA-tay, /ˈsɑːteɪ/ SAH-tay), or sate in Indonesian spelling, is a Southeast Asian dish of seasoned, skewered and grilled meat, served with a sauce. It is a dish from Indonesia, and popular in Malaysia, Singapore, Thailand, and Brunei. Satay may consist of diced or sliced chicken, goat, mutton, beef, pork, fish, other meats, or tofu; the more authentic version uses skewers from the midrib of the coconut palm frond, although bamboo skewers are often used. These are grilled or barbecued over a wood or charcoal fire, then served with various spicy seasonings. Satay can be served in various sauces, however most often they are served in a combination of soy and peanut sauce. Hence, peanut sauce is often called satay sauce."
          },
          {
            "price": "${'$'}1.14",
            "name": "Nasi uduk",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Nasi_uduk_netherlands.jpg/250px-Nasi_uduk_netherlands.jpg",
            "description": "Nasi uduk is an Indonesian Betawi style steamed rice cooked in coconut milk dish, originally from Jakarta, that can be widely found across the country."
          },
          {
            "price": "${'$'}3.41",
            "name": "Nasi bakar",
            "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Nasi_Bakar_Ayam.JPG/250px-Nasi_Bakar_Ayam.JPG",
            "description": "Nasi bakar (Indonesian for \"burned or grilled rice\") refer to steamed rice seasoned with spices and ingredients and wrapped in banana leaf secured with lidi semat (small needle made of central rib of coconut leaf) and later grilled upon charcoal fire. The burned banana leaf produced a unique aroma upon the rice. The banana leaf package is opened upon consumption. It is a relatively newly developed Indonesian dish around the early 2000s, probably derived from nasi timbel rice wrapped in banana leaf."
          }
        ]
            """
            )

            foodList = ArrayList(result!!)

            var recyclerViewFood: RecyclerView? = getView()?.findViewById(R.id.recycler_view_food)

            val foodListAdapter = ListFoodAdapter(foodList)

            recyclerViewFood?.adapter = foodListAdapter

//            setOnClick()
        }
    }

//    fun setOnClick() {
//        val imgView : ImageView? = getView()?.findViewById(R.id.image_view_item_photo)
//        Log.d("info", imgView.toString())
//        imgView?.setOnClickListener(this)
//    }

}