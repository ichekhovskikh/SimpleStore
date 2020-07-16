package com.ecwid.simplestore.feature.product.screen.update.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.extension.PICK_IMAGE
import com.ecwid.simplestore.core.extension.observe
import com.ecwid.simplestore.core.extension.onTextChanged
import com.ecwid.simplestore.core.extension.tryOpenGallery
import com.ecwid.simplestore.core.fragment.ViewModelFragment
import com.ecwid.simplestore.core.tools.OPEN_GALLERY_PERMISSIONS_REQUEST_CODE
import com.ecwid.simplestore.core.tools.blur
import com.ecwid.simplestore.core.tools.getSupportBitmap
import com.ecwid.simplestore.feature.product.screen.update.di.UpdateProductFragmentModule.Companion.SCREEN_TITLE
import com.ecwid.simplestore.feature.product.screen.update.di.UpdateProductFragmentModule.Companion.VIEW_TRANSITION_NAME
import kotlinx.android.synthetic.main.fragment_update_product.*
import javax.inject.Inject
import javax.inject.Named

class UpdateProductFragment : ViewModelFragment<UpdateProductViewModel>() {

    override val layoutId = R.layout.fragment_update_product
    override val viewModelClass = UpdateProductViewModel::class

    @Inject
    @Named(SCREEN_TITLE)
    lateinit var screenTitle: String

    @Inject
    @Named(VIEW_TRANSITION_NAME)
    lateinit var viewTransitionName: String

    private var saveMenuItem: MenuItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.transitionName = viewTransitionName
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setTitle(screenTitle)
        setToolbarIcon(R.drawable.ic_close)
        setupListeners()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        saveMenuItem = menu.findItem(R.id.item_save)
        saveMenuItem?.isEnabled = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.item_save -> {
            viewModel.saveProduct()
            navigator.back()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupListeners() {
        civIcon.setOnClickListener {
            tryOpenGallery()
        }
        tietName.onTextChanged { text: CharSequence?, _, _, _ ->
            viewModel.updateName(text?.toString())
        }
        tietAddress.onTextChanged { text: CharSequence?, _, _, _ ->
            viewModel.updateAddress(text?.toString())
        }
        tietPrice.onTextChanged { text: CharSequence?, _, _, _ ->
            viewModel.updatePrice(text?.toString()?.toLongOrNull())
        }
        bDelete.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.init(Unit)

        viewModel.productLiveData.observe(this) { product ->
            tietName.setText(product.name)
            tietAddress.setText(product.address)
            tietPrice.setText(product.price.toString())
            setIcon(product.icon)
        }

        viewModel.isSaveEnabledLiveData.observe(this) { isEnabled ->
            saveMenuItem?.isEnabled = isEnabled
        }

        viewModel.isDeleteAvailableLiveData.observe(this) { isAvailable ->
            bDelete.isVisible = isAvailable
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == OPEN_GALLERY_PERMISSIONS_REQUEST_CODE) {
            tryOpenGallery(requestPermissions = false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            intent?.data?.let { imageUri ->
                val activity = activity ?: return@let
                val bitmap = getSupportBitmap(activity.contentResolver, imageUri)
                setIcon(bitmap)
            }
        }
    }

    private fun setIcon(bitmap: Bitmap?) {
        val blurBitmap = context?.let { bitmap?.blur(it) }
        ivBlurIcon.setImageBitmap(blurBitmap)
        civIcon.setImageBitmap(bitmap)
        viewModel.updateIcon(bitmap)
    }

    private fun showDeleteDialog() {
        val context = context ?: return
        AlertDialog.Builder(context)
            .setMessage(R.string.delete_product_dialog_text)
            .setNegativeButton(R.string.no) { _, _ -> }
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteProduct()
                navigator.back()
            }
            .show()
    }
}