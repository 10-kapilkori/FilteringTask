package com.task.filteringtask.views

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.task.filteringtask.R
import com.task.filteringtask.adapter.ProductListAdapter
import com.task.filteringtask.databinding.ActivityMainBinding
import com.task.filteringtask.databinding.CustomFilterListBinding
import com.task.filteringtask.factory.ProductModelFactory
import com.task.filteringtask.pojos.ProductData
import com.task.filteringtask.retrofit.ApiHelper
import com.task.filteringtask.retrofit.RetrofitClient
import com.task.filteringtask.retrofit.Status
import com.task.filteringtask.status.Constants
import com.task.filteringtask.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        /**
         * 0 -> None
         * 1 -> Name (A-Z)
         * 2 -> Name (Z-A)
         * 3 -> Category Name (A-Z)
         * 4 -> Category Name (Z-A)
         * 5 -> Rating
         * 6 -> Price High-Low
         * 7 -> Price Low-High
         * */
        var filteredBy: String = Constants.NONE
    }

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var productListAdapter: ProductListAdapter

    var productList: ArrayList<ProductData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        productViewModel = ViewModelProvider(
            this,
            ProductModelFactory(ApiHelper(RetrofitClient.apiInterface))
        )[ProductViewModel::class.java]
    }

    private fun setupUI() {
        activityMainBinding.apply {
            productListAdapter = ProductListAdapter(this@MainActivity, productList)
            mainRvFilteredData.adapter = productListAdapter

            mainTvFilterData.setOnClickListener {
                openPopupForFilteringData(this@MainActivity)
            }
        }

        callRetrieveProduct()
    }

    private fun openPopupForFilteringData(context: Context) {
        val dialog = Dialog(context)
        val dialogBinding: CustomFilterListBinding =
            CustomFilterListBinding.bind(
                LayoutInflater.from(context).inflate(R.layout.custom_filter_list, null)
            )

        dialog.setContentView(dialogBinding.root)

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val dialogWidth = metrics.widthPixels
        val window = dialog.window
        window?.setLayout(
            (dialogWidth * 2.8 / 3).toInt(),
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()

        dialogBinding.apply {
            customFilterRg.check(
                when (filteredBy) {
                    Constants.NAME_A_TO_Z -> R.id.custom_filter_rb_asc_name
                    Constants.NAME_Z_TO_A -> R.id.custom_filter_rb_desc_name
                    Constants.CATEGORY_NAME_A_TO_Z -> R.id.custom_filter_rb_asc_category_name
                    Constants.CATEGORY_NAME_Z_TO_A -> R.id.custom_filter_rb_desc_category_name
                    Constants.RATING -> R.id.custom_filter_rb_rating
                    Constants.PRICE_HIGH_TO_LOW -> R.id.custom_filter_rb_asc_price
                    Constants.PRICE_LOW_TO_HIGH -> R.id.custom_filter_rb_desc_price
                    else -> customFilterRg.checkedRadioButtonId
                }
            )

            customFilterRg.setOnCheckedChangeListener { group, checkedId ->
                filteredBy = when (checkedId) {
                    R.id.custom_filter_rb_asc_name -> {
                        Constants.NAME_A_TO_Z
                    }
                    R.id.custom_filter_rb_desc_name -> {
                        Constants.NAME_Z_TO_A
                    }
                    R.id.custom_filter_rb_asc_category_name -> {
                        Constants.CATEGORY_NAME_A_TO_Z
                    }
                    R.id.custom_filter_rb_desc_category_name -> {
                        Constants.CATEGORY_NAME_Z_TO_A
                    }
                    R.id.custom_filter_rb_rating -> {
                        Constants.RATING
                    }
                    R.id.custom_filter_rb_asc_price -> {
                        Constants.PRICE_HIGH_TO_LOW
                    }
                    R.id.custom_filter_rb_desc_price -> {
                        Constants.PRICE_LOW_TO_HIGH
                    }
                    else -> {
                        Constants.NONE
                    }
                }
            }

            customFilterBtnOk.setOnClickListener {
                filterData(dialog)
            }

            customFilterBtnReset.setOnClickListener {
                filteredBy = Constants.NONE
                filterData(dialog)
            }
        }
    }

    private fun callRetrieveProduct() {
        productViewModel.retrieveProductData().observe(this) {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    displayLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    it.data?.let { data ->
                        if (data.productDataList.size > 0) {
                            productList.addAll(data.productDataList[0].productData)

                            for (i in 0 until activityMainBinding.mainRvFilteredData.itemDecorationCount)
                                activityMainBinding.mainRvFilteredData.removeItemDecorationAt(i)

                            if (productList.size % 2 == 0)
                                activityMainBinding.mainRvFilteredData.addItemDecoration(
                                    BottomItemDecoration()
                                )
                        } else {
                            activityMainBinding.mainTvNoData.visibility = VISIBLE
                            activityMainBinding.mainRvFilteredData.visibility = GONE
                        }
                    }
                    productListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun filterData(dialog: Dialog) {
        productListAdapter.filter.filter(filteredBy)
        productListAdapter.notifyDataSetChanged()
        dialog.dismiss()
    }

    private fun displayLoader() {
        activityMainBinding.mainPb.visibility = VISIBLE
        activityMainBinding.mainRvFilteredData.visibility = GONE
    }

    private fun hideLoader() {
        activityMainBinding.mainPb.visibility = GONE
        activityMainBinding.mainRvFilteredData.visibility = VISIBLE
    }

    inner class BottomItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            val spacing = activityMainBinding.mainTvFilterData.measuredHeight + 50

            if ((position == state.itemCount - 1 || position == state.itemCount - 2) && (productList.size % 2 == 0))
                outRect.bottom = spacing
        }
    }
}
