package com.example.newsapp.ui.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.size
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.view.*

class ToolBarBehavior(private val context:Context, attributeSet: AttributeSet)
    :CoordinatorLayout.Behavior<Toolbar>(context, attributeSet) {


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: Toolbar,
        dependency: View
    ): Boolean {
        return dependency is NestedScrollView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: Toolbar,
        dependency: View
    ): Boolean {
        if (dependency is NestedScrollView) {
            dependency.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                changeToolBarColor(child, scrollY)
            }
        }
        return true
    }


    private fun changeToolBarColor(toolbar: Toolbar, position:Int){
        val p = convertPixelsToDP(position)
        val header = toolbar.findViewById<TextView>(R.id.first_title)
        val searchIco = toolbar.menu.findItem(R.id.search_btn).icon
        val wrappedDrawable = DrawableCompat.wrap(searchIco)

        if (p in 300.0..400.0){
            val a = (p - 300) / 100f
            toolbar.setBackgroundColor(getColorWithAlpha(Color.WHITE, a))
            header.setTextColor(ColorUtils.blendARGB(Color.WHITE, Color.BLACK, a))
            DrawableCompat.setTint(wrappedDrawable, ColorUtils.blendARGB(Color.WHITE, Color.BLACK, a))
        }
        if (p < 300) {
            toolbar.setBackgroundColor(getColorWithAlpha(Color.WHITE, 0f))
            header.setTextColor(Color.WHITE)
            DrawableCompat.setTint(wrappedDrawable, Color.WHITE)
        }
        if (p > 400) {
            toolbar.setBackgroundColor(getColorWithAlpha(Color.WHITE, 1f))
            header.setTextColor(Color.BLACK)
            DrawableCompat.setTint(wrappedDrawable, Color.BLACK)
        }
    }



    private fun convertPixelsToDP(px:Int): Float {
       return px / context.resources.displayMetrics.density
    }

    private fun getColorWithAlpha(color:Int, ratio:Float):Int{
        val alpha = Math.round(Color.alpha(color) * ratio)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return Color.argb(alpha, r,g,b)
    }

}