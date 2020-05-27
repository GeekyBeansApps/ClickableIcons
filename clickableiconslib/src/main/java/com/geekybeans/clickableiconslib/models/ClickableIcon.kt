package com.geekybeans.clickableiconslib.models

import com.airbnb.lottie.LottieDrawable

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
 *  If an empty String (or just whitespaces) will be passed, the TextView will not show.
 *
 *  @property fadeOutIconAfterSelection Set this to true if you want the icon will fade out on pressed.
 *  This option is only for ImageIcon or LottieAnimatedIcon (otherwise will be ignored).
 */
sealed class ClickableIcon()
{
    abstract val iconImageResource: Int
    var iconDescription: String = ""
    var fadeOutIconAfterSelection = false
}

/**
 * ImageIcon represents a class for creating a regular clickable icon to be used in the adapter.
 *
 * @param iconImageResource The icon's image resource.
 *
 */
class ImageIcon(override val iconImageResource: Int): ClickableIcon()
{

}

/**
 * LottieAnimatedIcon represents a class for creating an animated clickable icon to be used in the adapter.
 *
 * @param iconImageResource The icon's image resource - a Lottie JSON file.
 *
 * @property animationSpeed Sets the icon's animation speed.
 *
 * @property repeatCount Sets the icon's animation repeat count (infinite is the default).
 * Any positive int for the number of times the animation should repeat, 0 for one time only,
 * or LottieDrawable.INFINITE (-1) - for infinite repeat.
 *
 */
class LottieAnimatedIcon(override val iconImageResource: Int): ClickableIcon()
{
    var animationSpeed = 0f
    var repeatCount = LottieDrawable.INFINITE
}

/**
 * SelectableIcon represents a class for creating an icon that can be selected/deselected on pressed, to be used in the adapter.
 * The params passed to it's constructor will automatically reproduce a selector drawable.
 *
 * @param iconImageResource The icon's not selected state image resource.
 * @param iconImageResourceSelected The icon's selected state image resource.
 *
 */
class SelectableIcon(override val iconImageResource: Int, val iconImageResourceSelected: Int): ClickableIcon()
{

}