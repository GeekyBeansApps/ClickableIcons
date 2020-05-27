package com.geekybeans.clickableiconslib.adapters

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekybeans.clickableiconslib.models.ClickableIcon
import com.geekybeans.clickableiconslib.models.ClickableIconSet
import kotlinx.android.synthetic.main.clickable_icon_set.view.*

class ClickableIconAdapter(val context:Context, private val iconSetView: ClickableIconSet, private val iconSet: List<ClickableIcon>) :
    ClickableIconsRecyclerAdapter.IconClickedListener {
    private constructor(context:Context, builder: Builder): this(context, builder.iconSetView, builder.iconSet)

    inner class Builder
    {
        lateinit var iconSetView: ClickableIconSet
            private set

        lateinit var iconSet: List<ClickableIcon>
            private set


        fun iconSetView(iconSetView: ClickableIconSet) = apply { this.iconSetView = iconSetView }

        fun iconSet(iconSet: List<ClickableIcon>) = apply { this.iconSet = iconSet }

        fun build() =
            ClickableIconAdapter(
                context,
                this
            )
    }

    fun setAdapter()
    {
        iconSetView.icon_set_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ClickableIconsRecyclerAdapter(iconSet, this@ClickableIconAdapter)
        }
    }

    override fun onIconClicked(iconDescription: String) {
        TODO("Not yet implemented")
    }
}