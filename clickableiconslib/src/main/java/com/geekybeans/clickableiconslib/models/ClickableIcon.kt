package com.geekybeans.clickableiconslib.models

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import com.airbnb.lottie.LottieDrawable


/**
 *  ClickableIcon is an abstract class representing an icon that can be used in the adapter.
 *  To use an object of this class implement one of it's sub-classes:
 *
 *  ImageIcon: a regular clickable icon.
 *  SelectableIcon: an icon that can be selected/deselected on pressed.
 *  LottieAnimatedIcon: an animated clickable icon.
 *
 *  @property iconImageResource The icon's image resource.
 *
 *  @property iconDescription The icon's text label, positioned below it.
 *  Empty String (or just whitespaces) will be ignored and the TextView will not show.
 *
 *  @property showIconDescriptionAsLabel set whether to show/hide the icon's description as a TextView below it.
 *
 *  @property fadeOutIconAfterSelection Set this to true if you want the icon will fade out on pressed.
 *  This option is only for ImageIcon or LottieAnimatedIcon (otherwise will be ignored).
 */
sealed class ClickableIcon()
{
    companion object
    {
        const val DEFAULT_PADDING = 0
    }

    abstract val iconImageResource: Int
    abstract val iconDescription: String
    abstract val showIconDescriptionAsLabel: Boolean
    abstract val fadeOutIconAfterSelection: Boolean
    var paddingStart = DEFAULT_PADDING
    var paddingEnd = DEFAULT_PADDING
    var paddingTop = DEFAULT_PADDING
    var paddingBottom = DEFAULT_PADDING

    /**
     * Set the icon set's padding
     *
     * @param start sets the icon set's start padding (default is 0)
     * @param end sets the icon set's end padding (default is 0)
     * @param top sets the icon set's top padding (default is 0)
     * @param bottom sets the icon set's bottom padding (default is 0)
     */
    fun setPadding(start:Int = DEFAULT_PADDING,end:Int = DEFAULT_PADDING,top:Int = DEFAULT_PADDING,bottom:Int = DEFAULT_PADDING)
    {
        paddingStart = start
        paddingEnd = end
        paddingTop = top
        paddingBottom = bottom
    }
}

/**
 * A clickable ImageIcon constructor.
 *
 * @param iconImageResource The icon's image resource.
 *
 * @param iconDescription The icon's text label, positioned below it.
 *
 * @param showIconDescriptionAsLabel (optional) set whether to show/hide the icon's description as a TextView below it.
 *
 * @param fadeOutIconAfterSelection (optional) Set this to true if you want the icon will fade out on pressed.
 *
 */
class ImageIcon(override val iconImageResource: Int, override val iconDescription: String,
                override val showIconDescriptionAsLabel: Boolean = false, override val fadeOutIconAfterSelection: Boolean = false): ClickableIcon()
{

}

/**
 * A clickable LottieAnimatedIcon constructor.
 *
 * @param iconImageResource The icon's image resource - a Lottie JSON file.
 *
 * @param iconDescription The icon's text label, positioned below it.
 *
 * @param showIconDescriptionAsLabel (optional) set whether to show/hide the icon's description as a TextView below it (default is hide=false).
 *
 * @param fadeOutIconAfterSelection (optional) Set this to true if you want the icon will fade out on pressed (default is false).
 *
 * @param animationSpeed (optional) Sets the icon's animation speed (default is 1f).
 *
 * @param repeatCount (optional) Sets the icon's animation repeat count (default is infinite).
 * Any positive int for the number of times the animation should repeat, 0 for one time only,
 * or LottieDrawable.INFINITE (-1) - for infinite repeat.
 *
 */
class LottieAnimatedIcon(override val iconImageResource: Int, override val iconDescription: String,
                         override val showIconDescriptionAsLabel: Boolean = false, override val fadeOutIconAfterSelection: Boolean = false,
                         val iconSize: Int = 100, val animationSpeed: Float = 1f, val repeatCount: Int = LottieDrawable.INFINITE): ClickableIcon()
{

}

/**
 * A ResourcesSelectableIcon constructor.
 * The params passed to it's constructor will automatically reproduce a selector drawable.
 *
 * @param selectorResource The icon's selector resource.
 *
 * @param iconDescription The icon's text label, positioned below it.
 *
 * @param showIconDescriptionAsLabel (optional) set whether to show/hide the icon's description as a TextView below it.
 *
 */

class SelectableIcon(private val selectorResource: Drawable?, override val iconDescription: String,
                     override val showIconDescriptionAsLabel: Boolean = false): ClickableIcon()
{
    override var iconImageResource = 0
    override val fadeOutIconAfterSelection: Boolean = false

    var iconSelectorResource = selectorResource

    companion object
    {
        private val STATE_SELECTED_VAL = intArrayOf(android.R.attr.state_selected)
        private val STATE_DESELECTED_VAL = intArrayOf(-android.R.attr.state_selected)
    }


    /**
     * A ResourcesSelectableIcon constructor.
     * The params passed to it's constructor will automatically reproduce a selector drawable.
     *
     * @param context view's context.
     *
     * @param deselectedImageResource The icon's de-selected resource.
     *
     * @param selectedImageResource The icon's selected resource.
     *
     * @param iconDescription The icon's text label, positioned below it.
     *
     * @param showIconDescriptionAsLabel (optional) set whether to show/hide the icon's description as a TextView below it.
     *
     */
    constructor(context: Context, deselectedImageResource: Int, selectedImageResource: Int, iconDescription: String, showIconDescriptionAsLabel: Boolean = false)
            : this(context.getDrawable(deselectedImageResource), iconDescription, showIconDescriptionAsLabel)
    {
        //get drawables
        val selectedDrawable = context.getDrawable(selectedImageResource)
        val deSelectedDrawable = context.getDrawable(deselectedImageResource)
        //create a selector drawable
        val selector = StateListDrawable()
        selector.addState(STATE_SELECTED_VAL, selectedDrawable)
        selector.addState(STATE_DESELECTED_VAL, deSelectedDrawable)
        iconSelectorResource = selector
    }
}

//TODO: not yet implemented
//class MultiSelectableIcon(val iconImageResourceList: IntArray, override val iconDescription: String, override val showIconDescriptionAsLabel: Boolean): ClickableIcon()
//{
//    override val iconImageResource: Int = 0
//    override val fadeOutIconAfterSelection: Boolean = false
//}