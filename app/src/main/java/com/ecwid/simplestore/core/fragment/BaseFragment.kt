package com.ecwid.simplestore.core.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.activity.BaseActivity
import com.ecwid.simplestore.core.extension.hideKeyboard
import com.ecwid.simplestore.core.navigation.Navigator
import com.ecwid.simplestore.core.view.ProgressDialog
import com.ecwid.simplestore.core.view.SearchView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    private var params: Any? = null

    private var toolbar: Toolbar? = null

    @Inject
    protected lateinit var navigator: Navigator

    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.setNavigationFragment(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        val view = view ?: return
        toolbar = view.findViewById(R.id.toolbar)
        val activity = activity as? AppCompatActivity
        activity?.setSupportActionBar(toolbar)
        setToolbarIcon(R.drawable.ic_arrow_back)
    }

    protected fun showSearch() {
        val view = view ?: return
        val searchView = view.findViewById<SearchView>(R.id.svSearch)
        searchView.setHeaderView(toolbar)
        searchView.visibility = View.VISIBLE
        searchView.setFocusChangedListener { hasFocus ->
            if (!hasFocus) {
                view.hideKeyboard()
            }
        }
    }

    protected fun setTitle(@StringRes title: Int) {
        activity?.setTitle(title)
    }

    protected fun setTitle(title: String) {
        activity?.title = title
    }

    protected fun setToolbarIcon(@DrawableRes iconRes: Int) {
        val activity = activity as? AppCompatActivity ?: return
        val actionBar = activity.supportActionBar ?: return
        actionBar.setHomeAsUpIndicator(iconRes)
    }

    override fun onResume() {
        super.onResume()
        val activity = activity ?: return
        if (activity is BaseActivity) {
            activity.shouldDisplayHomeUp()
        }
    }

    fun showTextDialog(message: String) {
        val context = context ?: return
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.close) { _, _ -> }
            .show()
    }

    protected fun showLoading(isLoading: Boolean) {
        val context = context ?: return
        if (progressDialog == null && isLoading) {
            progressDialog = ProgressDialog(context)
        }
        if (isLoading) {
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <Params : Parcelable> getParams(): Params? =
        (params ?: arguments?.get(PARAMS_EXTRA_TAG)) as? Params

    fun <Params : Parcelable> setParams(params: Params) {
        val args = arguments ?: Bundle()
        args.putParcelable(PARAMS_EXTRA_TAG, params)
        arguments = args
    }

    @CallSuper
    open fun onBackPressed(): Boolean {
        return progressDialog?.isShowing ?: false
    }

    companion object {
        private const val PARAMS_EXTRA_TAG = "paramsExtraTag"
    }
}
