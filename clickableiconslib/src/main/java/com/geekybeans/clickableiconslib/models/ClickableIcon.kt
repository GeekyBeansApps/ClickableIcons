package com.geekybeans.clickableiconslib.models

import com.airbnb.lottie.LottieDrawable

sealed class ClickableIcon()
{
    abstract val iconImageResource: Int
    var iconDescription: String = ""
    var fadeOutIconAfterSelection = false


}


class ImageIcon(override val iconImageResource: Int): ClickableIcon()
{

}

class AnimatedIcon(override val iconImageResource: Int): ClickableIcon()
{
    var animationSpeed = 0f
    var repeatCount = LottieDrawable.INFINITE
}

class SelectableIcon(override val iconImageResource: Int, val iconImageResourceSelected: Int): ClickableIcon()
{

}