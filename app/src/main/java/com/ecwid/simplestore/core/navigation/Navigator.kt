package com.ecwid.simplestore.core.navigation

import android.transition.Explode
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.extension.inTransaction
import com.ecwid.simplestore.core.fragment.BaseFragment
import javax.inject.Inject

interface Navigator {

    fun setNavigationFragment(fragment: BaseFragment)

    fun navigateTo(
        params: Params,
        sharedElement: View? = null,
        transition: Transition? = null
    )

    fun back()
}

class NavigatorImpl @Inject constructor() : Navigator {

    private var navigationFragment: BaseFragment? = null

    override fun setNavigationFragment(fragment: BaseFragment) {
        navigationFragment = fragment
    }

    override fun navigateTo(
        params: Params,
        sharedElement: View?,
        transition: Transition?
    ) {
        val navigationFragment = navigationFragment ?: return
        val newFragment = params.fragment()

        navigationFragment.exitTransition = TransitionSet()
            .addTransition(Fade())
            .addTransition(Explode())

        newFragment.apply {
            setParams(params)
            sharedElementEnterTransition = transition
            sharedElementReturnTransition = transition
        }

        navigationFragment.parentFragmentManager.inTransaction {
            sharedElement?.let { addSharedElement(it, it.transitionName) }
            addToBackStack(navigationFragment::class.simpleName)
            replace(R.id.flContainer, newFragment)
        }
    }

    override fun back() {
        navigationFragment?.parentFragmentManager?.popBackStackImmediate()
    }
}