package com.mazaady.mazaady.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.leo.searchablespinner.SearchableSpinner
import com.leo.searchablespinner.interfaces.OnItemSelectListener
import com.mazaady.domain.entity.cat.Option
import com.mazaady.domain.entity.cats.Category
import com.mazaady.mazaady.R
import com.mazaady.mazaady.databinding.ActivityMainBinding
import com.mazaady.mazaady.ui.AuctionDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    var b: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Go to the second page
        binding.textGoToTheSecondPage.setOnClickListener {
            startActivity(Intent(this, AuctionDetailsActivity::class.java))
        }

        // Initialization get categories from API Server
        viewModel.getCategories()

        lifecycleScope.launch {
            viewModel.categories.collect {
                val categories = it?.data?.categories
                val categoriesHashMap: HashMap<String, Int> = HashMap()

                if (categories != null) {
                    for (c in categories)
                        categoriesHashMap[c.slug] = c.id

                    searchableSpinnerInitialization(
                        binding.tiMainCategory,
                        categoriesHashMap,
                        categories
                    )
                }
            }
        }

        // Sub category
        lifecycleScope.launch {
            viewModel.subCategory.collect {
                val data = it?.data
                if (data != null) {
                    // visibility -> GONE
                    binding.linearProgressIndicator.visibility = View.INVISIBLE

                    for (d in data) {
                        setupTextInputLayout(d.slug, d.options)
                    }
                }
            }
        }

        // Options
        lifecycleScope.launch {
            viewModel.options.collect {
                val data = it?.data
                if (data != null) {
                    // visibility -> GONE
                    binding.linearProgressIndicator.visibility = View.INVISIBLE

                    for (d in data) {
                        setupTextInputLayout(d.slug, d.options)
                    }
                }
            }
        }
    }

    /**
     * Searchable spinner initialization
     *
     * @param textInputLayout TextInputLayout
     * @param hashMap get 'id' and 'slug'
     * @param removeAllViews remove all views
     */
    private fun searchableSpinnerInitialization(
        textInputLayout: TextInputLayout,
        hashMap: HashMap<String, Int>,
        removeAllViews: Boolean
    ) {
        // choose the driver is not null
        assert(textInputLayout.editText != null)
        val searchableSpinner = SearchableSpinner(this)
        searchableSpinner.windowTitle = getString(com.mazaady.mazaady.R.string.main_category)

        // Finding the Set of keys from
        // the HashMap
        // Finding the Set of keys from
        // the HashMap
        val keySet: Set<String> = hashMap.keys

        // Creating an ArrayList of keys
        // by passing the keySet

        // Creating an ArrayList of keys
        // by passing the keySet
        val listOfKeys = ArrayList(keySet)

        if (hashMap.size <= 0) {
            Log.i(TAG, "searchableSpinnerInitialization: is empty")
        } else {
            searchableSpinner.setSpinnerListItems(listOfKeys)
            searchableSpinner.onItemSelectListener =
                object : OnItemSelectListener {
                    override fun setOnItemSelectListener(position: Int, selectedString: String) {
                        textInputLayout.editText!!.setText(selectedString)
                        val selected = hashMap[selectedString]

                        if (removeAllViews) {
                            // visibility -> GONE
                            binding.linearProgressIndicator.visibility = View.VISIBLE

                            // remove all views before add view
                            binding.linearLayout.removeAllViews()
                            selected?.let { viewModel.getSubCategory(it) }

                            Log.e(TAG, "Selected (children): $selected")
                        } else {
                            Log.e(TAG, "Selected (sub-category -> options -> id): $selected")
                            selected?.let { viewModel.getOption(selected) }

                            val hint = textInputLayout.hint.toString()
                            if (hint == "brand") {
                                val et = findViewById<LinearLayout>(R.id.linearLayoutID)
                                et.removeAllViews()
                            }

                            b = true
                        }
                    }
                }

            textInputLayout.editText!!.setOnClickListener {
                searchableSpinner.highlightSelectedItem = true
                searchableSpinner.show()
            }
        }
    }

    /**
     * Searchable spinner initialization
     *
     * @param textInputLayout
     * @param hashMap (String, Int)
     * @param categories categories
     */
    private fun searchableSpinnerInitialization(
        textInputLayout: TextInputLayout,
        hashMap: HashMap<String, Int>,
        categories: List<Category>
    ) {
        // choose the driver is not null
        assert(textInputLayout.editText != null)
        val searchableSpinner = SearchableSpinner(this)
        searchableSpinner.windowTitle = getString(com.mazaady.mazaady.R.string.main_category)

        // Finding the Set of keys from
        // the HashMap
        // Finding the Set of keys from
        // the HashMap
        val keySet: Set<String> = hashMap.keys

        // Creating an ArrayList of keys
        // by passing the keySet

        // Creating an ArrayList of keys
        // by passing the keySet
        val listOfKeys = ArrayList(keySet)

        searchableSpinner.setSpinnerListItems(listOfKeys)
        searchableSpinner.onItemSelectListener =
            object : OnItemSelectListener {
                override fun setOnItemSelectListener(position: Int, selectedString: String) {
                    textInputLayout.editText!!.setText(selectedString)
                    val selected = hashMap[selectedString]
                    Log.e(TAG, "Selected (categories): $selected")

                    for (c in categories) {
                        if (c.id == selected) {
                            val childrenHashMap: HashMap<String, Int> = HashMap()
                            for (children in c.children)
                                childrenHashMap[children.slug] = children.id

                            searchableSpinnerInitialization(
                                binding.tiSubCategory,
                                childrenHashMap,
                                true
                            )
                        }
                    }
                }
            }
        textInputLayout.editText!!.setOnClickListener {
            searchableSpinner.highlightSelectedItem = true
            searchableSpinner.show()
        }
    }

    /**
     * Setup TextInputLayout
     *
     * @param hintText hintText
     * @param arrayList option
     */
    private fun setupTextInputLayout(hintText: String, arrayList: List<Option>) {
        val et = findViewById<LinearLayout>(R.id.linearLayoutID)
        val optionHashMap: HashMap<String, Int> = HashMap()

        for (option in arrayList) {
            optionHashMap[option.slug] = option.id
        }

        val tInput1 = addTextInputLayout(hintText, arrayList)

        if (b) {
            et.addView(tInput1)
            b = false
        } else {
            if (hintText == "brand") {
                binding.linearLayout.addView(tInput1)
                val createLinearLayout = createLinearLayout()
                binding.linearLayout.addView(createLinearLayout)
            } else {
                binding.linearLayout.addView(tInput1)
            }
        }

        searchableSpinnerInitialization(tInput1, optionHashMap, false)
    }

    /**
     * Add TextInputLayout
     *
     * @param hintText
     */
    private fun addTextInputLayout(hintText: String, arrayList: List<Option>): TextInputLayout {
        // =========================================================================================
        // modify in form(TextInputLayout)
        // =========================================================================================
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )

        var textInputLayout: TextInputLayout = if (arrayList.isEmpty()) {
            TextInputLayout(
                this,
                null,
                com.leo.searchablespinner.R.style.Base_Widget_AppCompat_EditText
            )
        } else {
            TextInputLayout(this, null, com.mazaady.mazaady.R.attr.customTextInputStyle)
        }

        textInputLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val margin = resources.getDimension(R.dimen.text_margin).toInt()
        layoutParams.setMargins(margin, 0, margin, margin)
        textInputLayout.layoutParams = layoutParams

        textInputLayout.layoutParams = layoutParams
        // =========================================================================================

        // =========================================================================================
        // modify in form(AutoCompleteTextView)
        // =========================================================================================
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )

        if (arrayList.isEmpty()) {
            val textInputEditText = TextInputEditText(this)
            textInputEditText.layoutParams = params
            textInputEditText.hint = hintText

            val padding = resources.getDimension(R.dimen.text_padding).toInt()
            textInputEditText.setPadding(padding, padding, padding, padding)
            textInputLayout.addView(textInputEditText)
        } else {
            val autoCompleteTextView =
                AutoCompleteTextView(this, null, R.style.Base_Theme_Mazaady)
            autoCompleteTextView.layoutParams = params
            autoCompleteTextView.hint = hintText

            val padding = resources.getDimension(R.dimen.text_padding).toInt()
            val paddingTop =
                resources.getDimension(R.dimen.text_padding_top).toInt()
            autoCompleteTextView.setPadding(padding, paddingTop, padding, padding)
            textInputLayout.addView(autoCompleteTextView)
        }
        // =========================================================================================

        return textInputLayout
    }

    private fun createLinearLayout() :LinearLayout{
        val parent = LinearLayout(this)
        parent.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        parent.orientation = LinearLayout.VERTICAL

        parent.id = R.id.linearLayoutID

        return parent
    }


    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}