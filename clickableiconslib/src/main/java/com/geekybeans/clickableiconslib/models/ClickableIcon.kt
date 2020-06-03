package com.geekybeans.clickableiconslib.models

import android.os.Parcelable
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


/**
 *  ClickableIcon is an abstract class representing an icon that will be used in the adapter.
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
sealed class ClickableIcon(): Serializable
{
    abstract val iconImageResource: Int
    abstract val iconDescription: String
    abstract val showIconDescriptionAsLabel: Boolean
    abstract val fadeOutIconAfterSelection: Boolean
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
@Parcelize
class ImageIcon(override val iconImageResource: Int, override val iconDescription: String,
                override val showIconDescriptionAsLabel: Boolean = false, override val fadeOutIconAfterSelection: Boolean = false): ClickableIcon(), Parcelable
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
 * @param iconWidth (optional) Sets the icon's width (default is WRAP_CONTENT)
 *
 * @param iconHeight (optional) Sets the icon's height (default is WRAP_CONTENT)
 *
 */
@Parcelize
class LottieAnimatedIcon(override val iconImageResource: Int, override val iconDescription: String,
                         override val showIconDescriptionAsLabel: Boolean = false, override val fadeOutIconAfterSelection: Boolean = false,
                         val animationSpeed: Float = 1f, val repeatCount: Int = LottieDrawable.INFINITE): ClickableIcon(), Parcelable
{

}

/**
 * A SelectableIcon constructor.
 * The params passed to it's constructor will automatically reproduce a selector drawable.
 *
 * @param iconImageResource The icon's not selected state image resource.
 *
 * @param iconImageResourceSelected The icon's selected state image resource.
 *
 * @param iconDescription The icon's text label, positioned below it.
 *
 * @param showIconDescriptionAsLabel (optional) set whether to show/hide the icon's description as a TextView below it.
 *
 */
@Parcelize
class SelectableIcon(override val iconImageResource: Int, val iconImageResourceSelected: Int, override val iconDescription: String,
                     override val showIconDescriptionAsLabel: Boolean = false): ClickableIcon(), Parcelable
{
    @IgnoredOnParcel
    override val fadeOutIconAfterSelection: Boolean = false
}