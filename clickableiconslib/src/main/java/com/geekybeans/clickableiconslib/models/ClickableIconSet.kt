package com.geekybeans.clickableiconslib.models

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.geekybeans.clickableiconslib.R
import kotlinx.android.synthetic.main.clickable_icon_set.view.*


/**
 *  ClickableIconSet class represents the Clickable icon's custom view, which holds a set of ClickableIcon objects.
 *  Optional params:
 *  title -> set a title for the ClickableIcon Set.
 *  title_text_size -> the title's text size.
 *  title_text_color -> the title's text color.
 *  frame -> a boolean whether to draw a frame around the set and title.
 *  frame_color -> set the frame's color.
 *
 *  @param viewsContext the context which will use this view.
 *
 *  @param attrs the view's additional attributes
 *
 */
class ClickableIconSet(private val viewsContext: Context, attrs: AttributeSet): ConstraintLayout(viewsContext, attrs)
{
    private var frameIsShowing = false
    private var currentFrameColor: Int = Color.TRANSPARENT
    private var currentBackgroundColor: Int = Color.TRANSPARENT

    init
    {
        View.inflate(viewsContext, R.layout.clickable_icon_set, this)

        //get the view's custom attributes
        val attributes = viewsContext.obtainStyledAttributes(attrs, R.styleable.ClickableIconSet)

        //view's title
        if (attributes.getBoolean(R.styleable.ClickableIconSet_show_title, false))
        {
            setTitle(true, attributes.getString(R.styleable.ClickableIconSet_title).toString(),
                attributes.getFloat(R.styleable.ClickableIconSet_title_text_size, 14f),
                attributes.getColor(R.styleable.ClickableIconSet_title_text_color, Color.BLACK))
        }

        //view's frame
        if (attributes.getBoolean(R.styleable.ClickableIconSet_show_frame, false))
        {
            setFrame(true, attributes.getColor(R.styleable.ClickableIconSet_frame_color, Color.WHITE))
        }

        //view's background
        setBackground(attributes.getColor(R.styleable.ClickableIconSet_fill_color, Color.TRANSPARENT))

        attributes.recycle()
    }

    /**
     * Sets the view's title and it's params.
     *
     * @param show show/hide the title.
     *
     * @param title (optional) the title's text.
     *
     * @param textSize (optional) the title's text size.
     *
     * @param textColor (optional) the title's text color.
     *
     */
    fun setTitle(show: Boolean, title: String = "", textSize: Float = 14f, textColor:Int = Color.BLACK)
    {
        if (show)
        {
            icon_set_title.visibility = View.VISIBLE
            icon_set_title.text = title
            icon_set_title.textSize = textSize
            icon_set_title.setTextColor(textColor)
        }
        else
        {
            icon_set_title.visibility = View.GONE
            icon_set_title.text = ""
        }
    }

    /**
     * Sets the view's frame and it's params.
     *
     * @param show show/hide the view's frame.
     *
     * @param frameColor (optional) the frame color.
     *
     */
    fun setFrame(show: Boolean, frameColor: Int = Color.TRANSPARENT)
    {
        if(show)
        {
            frameIsShowing = true
            currentFrameColor = frameColor
            val shape = ( ContextCompat.getDrawable(viewsContext, R.drawable.shape_frame) as GradientDrawable)
            shape.setStroke(8, frameColor)
            shape.setColor(currentBackgroundColor)
            icon_set_root_view.background = shape
        }
        else
        {
            frameIsShowing = false
            icon_set_root_view.setBackgroundColor(currentBackgroundColor)
        }
    }

    /**
     * Sets the view's background color.
     *
     * @param color the background color to set to.
     */
    fun setBackground(color: Int)
    {
        currentBackgroundColor = color

        if (frameIsShowing)
        {
            val shape = ( ContextCompat.getDrawable(viewsContext, R.drawable.shape_frame) as GradientDrawable)
            shape.setStroke(8, currentFrameColor)
            shape.setColor(color)
            icon_set_root_view.background = shape
        }
        else
        {
            icon_set_root_view.setBackgroundColor(color)
        }
    }
}