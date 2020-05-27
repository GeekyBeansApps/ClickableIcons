package com.geekybeans.clickableiconslib.models

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.geekybeans.clickableiconslib.R
import kotlinx.android.synthetic.main.clickable_icon_set.view.*

class ClickableIconSet(private val viewsContext: Context, attrs: AttributeSet): ConstraintLayout(viewsContext, attrs)
{

    init
    {
        View.inflate(viewsContext, R.layout.clickable_icon_set, this)

        val attributes = viewsContext.obtainStyledAttributes(attrs, R.styleable.ClickableIconSet)
        icon_set_title.text = attributes.getString(R.styleable.ClickableIconSet_title)
        icon_set_title.textSize = attributes.getFloat(R.styleable.ClickableIconSet_title_text_size, 14f)
        icon_set_title.setTextColor(attributes.getColor(R.styleable.ClickableIconSet_title_text_color, Color.BLACK))

        attributes.recycle()
    }





}