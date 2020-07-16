package com.ecwid.simplestore.feature.product.screen.list.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.extension.*
import com.ecwid.simplestore.core.fragment.ViewModelFragment
import com.ecwid.simplestore.core.tools.CREATE_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE
import com.ecwid.simplestore.core.tools.OPEN_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE
import com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.ListProductAdapter
import com.ecwid.simplestore.feature.product.screen.update.presentation.UpdateFragmentParams
import kotlinx.android.synthetic.main.fragment_list_product.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ListProductFragment : ViewModelFragment<ListProductViewModel>(),
    ListProductAdapter.ListProductListener {

    override val layoutId = R.layout.fragment_list_product
    override val viewModelClass = ListProductViewModel::class

    @Inject
    lateinit var adapter: ListProductAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setTitle(R.string.app_name)
        setupSearchView()
        setupListView()
        setupListeners()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_file, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.item_export -> {
            tryOpenFileCreationChooser(DEFAULT_EXPORT_FILE_NAME)
            true
        }
        R.id.item_import -> {
            tryOpenFileChooser()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearchView() {
        showSearch()
        svSearch.hint = resources.getString(R.string.product_search)
    }

    private fun setupListView() {
        rvList.adapter = adapter
        rvList.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
    }

    private fun setupListeners() {
        svSearch.setTextChangedListener {
            viewModel.onSearchTextChanged(it.toString())
        }
        fabAdd.setOnClickListener { view ->
            navigator.navigateTo(
                params = UpdateFragmentParams(null, view.transitionName),
                sharedElement = view,
                transition = ChangeBounds()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.init(svSearch.text.toString())
        viewModel.itemsLiveData.observe(this) { items ->
            adapter.items = items
        }
        viewModel.loaderLiveData.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.errorLiveData.observe(this) { message ->
            showTextDialog(message)
        }
    }

    override fun onProductClick(view: View, id: Long) {
        navigator.navigateTo(
            params = UpdateFragmentParams(id, view.transitionName),
            sharedElement = view,
            transition = ChangeBounds()
        )
    }

    override fun onShowOnMapClick(name: String, latitude: Double?, longitude: Double?) {
        openMap(latitude, longitude, name)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            OPEN_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE -> {
                tryOpenFileChooser(requestPermissions = false)
            }
            CREATE_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE -> {
                tryOpenFileCreationChooser(DEFAULT_EXPORT_FILE_NAME, requestPermissions = false)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode != Activity.RESULT_OK) return
        val uri = intent?.data ?: return
        when (requestCode) {
            CHOOSE_FILE -> viewModel.importProducts(uri)
            CREATE_FILE -> viewModel.exportProducts(uri)
        }
    }

    companion object {
        const val DEFAULT_EXPORT_FILE_NAME = "products"
    }
}