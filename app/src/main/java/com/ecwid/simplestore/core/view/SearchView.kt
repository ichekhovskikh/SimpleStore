package com.ecwid.simplestore.core.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.extension.onTextChanged
import kotlinx.android.synthetic.main.view_search.view.*

class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defVal: Int = 0
) : CardView(context, attrs, defVal) {

    var text: CharSequence?
        get() = etSearch.text
        set(value) {
            etSearch.setText(value)
        }

    var hint: CharSequence?
        get() = etSearch.hint
        set(value) {
            etSearch.hint = value
        }

    private var headerView: View? = null
    private var initialHeaderHeight: Int = 0

    private var onFocusChangedListener: ((hasFocus: Boolean) -> Unit)? = null
    private var onTextChangedListener: ((text: CharSequence?) -> Unit)? = null
    private val animator = ValueAnimator.ofFloat(0f, 1f)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
        setCardBackgroundColor(ContextCompat.getColor(context, UNFOCUSED_BACKGROUND_RES))
        elevation = resources.getDimension(UNFOCUSED_ELEVATION_RES)
        ivLeftIcon.isEnabled = false
        processCustomAttrs(attrs)
        initAnimation()
        setupListeners()
    }

    private fun processCustomAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SearchView,
            0,
            0
        )
        try {
            text = typedArray.getString(R.styleable.SearchView_android_text)
            hint = typedArray.getString(R.styleable.SearchView_android_hint)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setupListeners() {
        etSearch.setOnFocusChangeListener { _, hasFocus ->
            setFocusState(hasFocus)
            onFocusChangedListener?.invoke(hasFocus)
        }
        etSearch.onTextChanged { text: CharSequence?, _, _, _ ->
            val isNotEmpty = !text.isNullOrEmpty()
            ivRightIcon.isVisible = etSearch.isFocused && isNotEmpty
            onTextChangedListener?.invoke(text)
        }
        ivRightIcon.setOnClickListener { clearText() }
        ivLeftIcon.setOnClickListener {
            if (etSearch.isFocused) {
                clearInputFocus()
            }
        }
    }

    fun setHeaderView(view: View?) {
        headerView = view
        view?.measure(WRAP_CONTENT, WRAP_CONTENT)
        initialHeaderHeight = view?.measuredHeight ?: 0
    }

    private fun setFocusState(isFocused: Boolean) {
        ivRightIcon.isVisible = isFocused && !text.isNullOrEmpty()
        animator.cancel()
        if (isFocused) {
            animator.start()
        } else {
            animator.reverse()
        }
    }

    private fun clearText() {
        if (!text.isNullOrEmpty()) {
            text = null
        }
    }

    private fun clearInputFocus() {
        etSearch.clearFocus()
    }

    fun setTextChangedListener(listener: (text: CharSequence?) -> Unit) {
        onTextChangedListener = listener
    }

    fun setFocusChangedListener(listener: (hasFocus: Boolean) -> Unit) {
        onFocusChangedListener = listener
    }

    private fun initAnimation() {
        val colorEvaluator = ArgbEvaluator()

        val unfocusedElevation = resources.getDimension(UNFOCUSED_ELEVATION_RES)
        val focusedElevation = resources.getDimension(FOCUSED_ELEVATION_RES)
        val unfocusedBackgroundColor = ContextCompat.getColor(context, UNFOCUSED_BACKGROUND_RES)
        val focusedBackgroundColor = ContextCompat.getColor(context, FOCUSED_BACKGROUND_RES)
        val unfocusedHintColor = ContextCompat.getColor(context, UNFOCUSED_HINT_COLOR_RES)
        val focusedHintColor = ContextCompat.getColor(context, FOCUSED_HINT_COLOR_RES)
        val unfocusedTextColor = ContextCompat.getColor(context, UNFOCUSED_TEXT_COLOR_RES)
        val focusedTextColor = ContextCompat.getColor(context, FOCUSED_TEXT_COLOR_RES)
        val unfocusedLeftButtonIcon = ContextCompat.getDrawable(context, UNFOCUSED_LEFT_BUTTON_RES)
        val focusedLeftButtonIcon = ContextCompat.getDrawable(context, FOCUSED_LEFT_BUTTON_RES)

        animator.addUpdateListener {
            val fraction = it.animatedValue as Float

            val backgroundColor = colorEvaluator.evaluate(
                fraction,
                unfocusedBackgroundColor,
                focusedBackgroundColor
            ) as Int

            val hintColor = colorEvaluator.evaluate(
                fraction,
                unfocusedHintColor,
                focusedHintColor
            ) as Int

            val textColor = colorEvaluator.evaluate(
                fraction,
                unfocusedTextColor,
                focusedTextColor
            ) as Int

            headerView?.apply {
                alpha = 1f - fraction
                val headerLayoutParams = layoutParams
                headerLayoutParams.height = when (fraction == 0f) {
                    true -> WRAP_CONTENT
                    else -> ((1f - fraction) * initialHeaderHeight).toInt()
                }
                layoutParams = headerLayoutParams
            }

            setCardBackgroundColor(backgroundColor)
            etSearch.setTextColor(textColor)
            etSearch.setHintTextColor(hintColor)
            if (fraction > 0.5) {
                elevation = focusedElevation
                ivLeftIcon.setImageDrawable(focusedLeftButtonIcon)
                ivLeftIcon.alpha = 2 * (fraction - 0.5f)
                ivLeftIcon.isEnabled = true
            } else {
                elevation = unfocusedElevation
                ivLeftIcon.setImageDrawable(unfocusedLeftButtonIcon)
                ivLeftIcon.alpha = 1f - 2f * fraction
                ivLeftIcon.isEnabled = false
            }
        }
    }

    companion object {
        private const val FOCUSED_ELEVATION_RES = R.dimen.zero
        private const val UNFOCUSED_ELEVATION_RES = R.dimen.micro

        private const val FOCUSED_TEXT_COLOR_RES = R.color.white
        private const val UNFOCUSED_TEXT_COLOR_RES = R.color.black

        private const val FOCUSED_HINT_COLOR_RES = R.color.white_50
        private const val UNFOCUSED_HINT_COLOR_RES = R.color.grey

        private const val FOCUSED_BACKGROUND_RES = R.color.colorPrimary
        private const val UNFOCUSED_BACKGROUND_RES = R.color.white

        private const val FOCUSED_LEFT_BUTTON_RES = R.drawable.ic_arrow_back
        private const val UNFOCUSED_LEFT_BUTTON_RES = R.drawable.ic_search
    }
}
