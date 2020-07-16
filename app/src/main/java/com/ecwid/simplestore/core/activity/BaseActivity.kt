package com.ecwid.simplestore.core.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.extension.inTransaction
import com.ecwid.simplestore.core.fragment.BaseFragment
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract val baseFragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        addBaseFragment()
    }

    private fun addBaseFragment() {
        val fragment = supportFragmentManager.fragments.firstOrNull()
        if (fragment == null) {
            supportFragmentManager.inTransaction {
                add(R.id.flContainer, baseFragment)
            }
        }
    }

    fun shouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0 || !isTaskRoot
        supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is BaseFragment && fragment.onBackPressed()) {
                return true
            }
        }
        super.onBackPressed()
        return false
    }
}
