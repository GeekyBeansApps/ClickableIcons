package com.geekybeans.clickableiconslib.adapters

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekybeans.clickableiconslib.models.ClickableIcon
import com.geekybeans.clickableiconslib.models.ClickableIconSet
import kotlinx.android.synthetic.main.clickable_icon_set.view.*


/**
 *  The ClickableIconAdapter class is an adapter for the Clickable icon's custom view.
 *
 *  @property iconSetView a reference to the custom view ClickableIconSet.
 *
 *  @property iconSet a list of ClickableIcons to be used in the view.
 *
 */
class ClickableIconAdapter(private val iconSetView: ClickableIconSet, private val iconSet: List<ClickableIcon>)
{
    private constructor(builder: Builder): this(builder.iconSetView, builder.iconSet)

    companion object
    {
        const val ORIENTATION_HORIZONTAL = RecyclerView.HORIZONTAL
        const val ORIENTATION_VERTICAL = RecyclerView.VERTICAL
        const val LAYOUT_LINEAR = 10
        const val LAYOUT_GRID = 11
        const val DEFAULT_SPAN_COUNT = 3
        const val DEFAULT_WIDTH = -2
        const val DEFAULT_HEIGHT = -2
    }

    /**
     *  The ClickableIconAdapter.Builder class is a builder for the Clickable icon's custom view adapter.
     *
     *  @property iconSetView a reference to the custom view ClickableIconSet.
     *
     *  @property iconSet a list of ClickableIcons to be used in the view.
     */
    class Builder
    {
        lateinit var iconSetView: ClickableIconSet
            private set

        lateinit var iconSet: List<ClickableIcon>
            private set

        private var orientation: Int = ORIENTATION_VERTICAL
        private var layout: Int = LAYOUT_LINEAR
        private var spanCount: Int = DEFAULT_SPAN_COUNT
        private var width: Int = DEFAULT_WIDTH
        private var height: Int = DEFAULT_HEIGHT

        fun iconSetView(iconSetView: ClickableIconSet) = apply { this.iconSetView = iconSetView }

        fun iconSet(iconSet: List<ClickableIcon>) = apply { this.iconSet = iconSet }

        /**
         * @param orientation sets the ClickableIconSet orientation (default is ORIENTATION_VERTICAL)
         */
        fun setOrientation(orientation: Int) = apply { this.orientation = orientation }

        /**
         * @param layout sets the ClickableIconSet layout manager (default is LAYOUT_LINEAR)
         */
        fun setLayoutManager(layout: Int) = apply { this.layout = layout}

        /**
         * @param spanCount sets the ClickableIconSet layout's span count (default is DEFAULT_SPAN_COUNT(3))
         */
        fun setLayoutSpanCount(spanCount: Int) = apply { this.spanCount = spanCount}

        /**
         * Set the adapter for the ClickableIcon set.
         *
         * @param context the context that will be using the view.
         *
         * @param iconClickListener the listener for the ClickableIcon's on click.
         * The IconClickedListener interface must be implemented in order to use it,
         * if icons are for visual purposes only, and no user interaction is required - a null value could be passed.
         */
        fun setAdapter(context:Context, iconClickListener: ClickableIconsRecyclerAdapter.IconClickedListener?) = apply {
            iconSetView.icon_set_recycler.apply {
                when (layout)
                {
                    LAYOUT_LINEAR -> layoutManager = LinearLayoutManager(context, orientation,false)
                    LAYOUT_GRID -> layoutManager = GridLayoutManager(context, spanCount)
                }

                adapter = ClickableIconsRecyclerAdapter(iconSet, iconClickListener)
            }
        }

        /**
         *  Build the ClickableIconSet view according to the builder's values.
         */
        fun build() = ClickableIconAdapter(this)
    }
}