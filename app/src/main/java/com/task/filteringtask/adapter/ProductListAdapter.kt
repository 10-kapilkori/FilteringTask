package com.task.filteringtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.filteringtask.R
import com.task.filteringtask.databinding.CustomProductsBinding
import com.task.filteringtask.pojos.ProductData
import com.task.filteringtask.status.Constants
import com.task.filteringtask.views.MainActivity

class ProductListAdapter(
    private val context: Context,
    private val productList: ArrayList<ProductData>
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>(), Filterable {

    private var originalList = listOf<ProductData>()
    private var filteredList = listOf<ProductData>()

    init {
        originalList = productList
        filteredList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = CustomProductsBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_products, parent, false)
        )

        return ProductListViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.onBind(filteredList, position)
    }

    inner class ProductListViewHolder(private val productBinding: CustomProductsBinding) :
        RecyclerView.ViewHolder(productBinding.root) {

        fun onBind(productList: List<ProductData>, position: Int) {
            productBinding.apply {
                when (MainActivity.filteredBy) {
                    Constants.NONE, Constants.NAME_A_TO_Z, Constants.NAME_Z_TO_A -> {
                        customTvUsername.text =
                            context.getString(R.string.name)
                                .plus(" ")
                                .plus(
                                    if (productList[position].name != "") productList[position].name
                                    else productList[position].userName
                                )
                    }
                    Constants.CATEGORY_NAME_A_TO_Z, Constants.CATEGORY_NAME_Z_TO_A -> {
                        customTvUsername.text =
                            context.getString(R.string.category_name)
                                .plus(" ")
                                .plus(
                                    if (productList[position].categoryName != "") productList[position].categoryName
                                    else productList[position].name
                                )
                    }
                    Constants.RATING -> {
                        customTvUsername.text = context.getString(R.string.rating)
                            .plus(" ")
                            .plus(
                                if (productList[position].averageRating != "") productList[position].averageRating
                                else "0"
                            )
                    }
                    Constants.PRICE_HIGH_TO_LOW, Constants.PRICE_LOW_TO_HIGH -> {
                        customTvUsername.text = context.getString(R.string.price)
                            .plus(" ")
                            .plus(
                                if (productList[position].minPrice != "") productList[position].minPrice
                                else "0"
                            )
                    }
                }

                Glide.with(context)
                    .load(productList[position].userProfile)
                    .error(R.drawable.no_image)
                    .into(customIvUserImg)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                filteredList = when (charSearch) {
                    Constants.NONE -> {
                        originalList
                    }
                    Constants.NAME_A_TO_Z -> {
                        originalList.sortedBy { it.name }
                    }
                    Constants.NAME_Z_TO_A -> {
                        originalList.sortedByDescending { it.name }
                    }
                    Constants.CATEGORY_NAME_A_TO_Z -> {
                        originalList.sortedBy { it.categoryName }
                    }
                    Constants.CATEGORY_NAME_Z_TO_A -> {
                        originalList.sortedByDescending { it.categoryName }
                    }
                    Constants.RATING -> {
                        originalList.sortedBy { it.averageRating }
                    }
                    Constants.PRICE_LOW_TO_HIGH -> {
                        originalList.sortedBy { it.minPrice?.toFloat() }
                    }
                    Constants.PRICE_HIGH_TO_LOW -> {
                        originalList.sortedByDescending { it.minPrice?.toFloat() }
                    }
                    else -> {
                        originalList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<ProductData>
                notifyDataSetChanged()
            }
        }
    }
}