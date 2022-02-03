package com.yuanhang.jd.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.yuanhang.jd.R

class SimpleToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {


    private var mRootView: View? = null
    private var mListener: OnClickListener? = null

    private var tvLeft: TextView? = null
    private var tvRight: TextView? = null
    private var tvCenter: TextView? = null
    private var etCenter: EditText? = null

    init {
        initRootView(context)
    }

    private fun initRootView(context: Context) {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(context).inflate(R.layout.simple_tool_bar, this, false)
            addView(mRootView)
            //ButterKnife.bind(this, mRootView!!)
            tvLeft = mRootView!!.findViewById(R.id.tv_left)
            tvRight = mRootView!!.findViewById(R.id.tv_right)
            tvCenter = mRootView!!.findViewById(R.id.tv_center)
            etCenter = mRootView!!.findViewById(R.id.et_center)
        }
        mRootView!!.setOnTouchListener { view, motionEvent -> false }
        tvLeft!!.setOnClickListener(this)
        tvRight!!.setOnClickListener(this)
        tvCenter!!.setOnClickListener(this)
    }

    override fun getRootView(): View? {
        return mRootView
    }

    override fun onClick(v: View) {
        if (mListener == null) {
            return
        }
        val viewId = v.id
        when (viewId) {
            R.id.tv_left -> {
                mListener!!.onTvLeftClick(tvLeft!!)
            }
            R.id.tv_center -> {
                mListener!!.onTvCenterClick(tvCenter!!)
            }
            R.id.tv_right -> {
                mListener!!.onTvRightClick(tvRight!!)
            }
        }
    }

    private fun setListener(listener: OnClickListener) {
        mListener = listener
    }

    interface OnClickListener {
        fun onTvLeftClick(textView: TextView)

        fun onTvCenterClick(textView: TextView)

        fun onTvRightClick(textView: TextView)
    }

    open class SimpleClickListener : OnClickListener {
        override fun onTvLeftClick(textView: TextView) {

        }

        override fun onTvCenterClick(textView: TextView) {

        }

        override fun onTvRightClick(textView: TextView) {

        }

    }


    fun backMode(context: Context, dialogFragment: DialogFragment, centerText: String) {
        tvCenter!!.text = centerText
        tvLeft!!.setCompoundDrawablesWithIntrinsicBounds(
            context.resources.getDrawable(R.mipmap.ic_chevron_left_white_64px),
            null,
            null,
            null
        )
        setListener(object : SimpleClickListener() {
            override fun onTvLeftClick(textView: TextView) {
                dialogFragment.dismiss()
            }
        })
    }

    fun backMode(activity: Activity, centerText: String) {
        tvCenter!!.text = centerText
        tvLeft!!.setCompoundDrawablesWithIntrinsicBounds(
            context.resources.getDrawable(R.mipmap.ic_chevron_left_white_64px),
            null,
            null,
            null
        )
        setListener(object : SimpleClickListener() {
            override fun onTvLeftClick(textView: TextView) {
                activity.finish()
            }
        })
    }


    fun inputMode() {
        etCenter!!.visibility = View.VISIBLE
        tvCenter!!.visibility = View.GONE
    }


    fun inputMode(context: Context, dialogFragment: DialogFragment) {
        backMode(context, dialogFragment, "")
        inputMode()
    }

    fun normalMode() {
        etCenter!!.visibility = View.GONE
        tvCenter!!.visibility = View.VISIBLE
        tvLeft!!.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    fun getTvLeft(): TextView {
        return tvLeft!!
    }

    fun getTvCenter(): TextView {
        return tvCenter!!
    }

    fun getTvRight(): TextView {
        return tvRight!!
    }

    fun getEtCenter(): EditText {
        return etCenter!!
    }
}