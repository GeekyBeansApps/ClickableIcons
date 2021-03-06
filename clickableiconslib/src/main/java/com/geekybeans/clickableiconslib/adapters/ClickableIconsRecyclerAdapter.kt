package com.geekybeans.clickableiconslib.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.geekybeans.clickableiconslib.R
import com.geekybeans.clickableiconslib.models.ClickableIcon
import com.geekybeans.clickableiconslib.models.ImageIcon
import com.geekybeans.clickableiconslib.models.LottieAnimatedIcon
import com.geekybeans.clickableiconslib.models.SelectableIcon
import kotlinx.android.synthetic.main.item_clickable_icon_animtaion.view.*
import kotlinx.android.synthetic.main.item_clickable_icon_image.view.*
import kotlinx.android.synthetic.main.item_clickable_icon_selectable.view.*


class ClickableIconsRecyclerAdapter(private val clickableIcons: List<ClickableIcon>, val iconClickListener: IconClickedListener?)
    : RecyclerView.Adapter<ClickableIconsRecyclerAdapter.ClickableIconsBaseViewHolder>()
{
    companion object
    {
        val IMAGE_ICON_LAYOUT_ID:Int = R.layout.item_clickable_icon_image
        val ANIMATED_ICON_LAYOUT_ID = R.layout.item_clickable_icon_animtaion
        val SELECTABLE_ICON_LAYOUT_ID = R.layout.item_clickable_icon_selectable
        const val FADE_OUT_DURATION = 300L
        const val TEXT_SIZE_ADDITION = 5
        const val TEXT_SIZE_DIVIDER = 10
    }

    override fun getItemCount() = clickableIcons.size

    override fun getItemViewType(position: Int): Int
    {
        return when(clickableIcons[position])
        {
            is ImageIcon -> IMAGE_ICON_LAYOUT_ID
            is LottieAnimatedIcon -> ANIMATED_ICON_LAYOUT_ID
            is SelectableIcon -> SELECTABLE_ICON_LAYOUT_ID
//            is SelectableIcon, is MultiSelectableIcon -> SELECTABLE_ICON_LAYOUT_ID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableIconsBaseViewHolder
    {
        return when(viewType)
        {
            IMAGE_ICON_LAYOUT_ID -> ClickableIconsImageViewHolder(LayoutInflater.from(parent.context).inflate(IMAGE_ICON_LAYOUT_ID, parent, false))
            ANIMATED_ICON_LAYOUT_ID -> ClickableIconsAnimatedViewHolder(LayoutInflater.from(parent.context).inflate(ANIMATED_ICON_LAYOUT_ID, parent, false))
            SELECTABLE_ICON_LAYOUT_ID -> ClickableIconsSelectableViewHolder(LayoutInflater.from(parent.context).inflate(SELECTABLE_ICON_LAYOUT_ID, parent, false))
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }

    override fun onBindViewHolder(holder: ClickableIconsBaseViewHolder, position: Int)
    {
        holder.bind(clickableIcons[position])
    }

    abstract inner class ClickableIconsBaseViewHolder(private val baseItemView: View): RecyclerView.ViewHolder(baseItemView)
    {
        abstract fun bind(clickableIcon: ClickableIcon)
        val ripple = TypedValue()
        val baseContext = baseItemView.context
        val baseResources = baseContext.resources
        val density = baseResources.displayMetrics.density

        init
        {
            baseItemView.context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, ripple, true)
        }

        fun fadeOutClickedIcon(iconDescription: String, position: Int)
        {
            val anim = AnimationUtils.loadAnimation(baseItemView.context, android.R.anim.fade_out)
            anim.duration = FADE_OUT_DURATION

            anim.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?)
                {
                    iconClickListener?.onIconClicked(iconDescription, true, position)
                }
            })

            baseItemView.startAnimation(anim)
        }
    }

    inner class ClickableIconsImageViewHolder(private val view: View): ClickableIconsBaseViewHolder(view)
    {
        override fun bind(clickableIcon: ClickableIcon)
        {
            //set the icon's padding
            view.setPadding(
                clickableIcon.paddingStart*density.toInt(),
                clickableIcon.paddingTop*density.toInt(),
                clickableIcon.paddingEnd*density.toInt(),
                clickableIcon.paddingBottom*density.toInt())

            //set the image resource
            view.clickable_icon_image_imageView.apply {
                setBackgroundResource(ripple.resourceId)
//                setImageResource(clickableIcon.iconImageResource)
                setImageResource(baseResources.getIdentifier(clickableIcon.iconImageResource, clickableIcon.iconImageResourceDefType, baseContext.packageName))
            }

            //set the description if exists
            if (clickableIcon.iconDescription.isNotBlank() && clickableIcon.showIconDescriptionAsLabel)
            {
                //set the text's size
                val drawable = view.context.getDrawable(baseResources.getIdentifier(clickableIcon.iconImageResource, clickableIcon.iconImageResourceDefType, baseContext.packageName))
                val iconInDp = drawable!!.intrinsicWidth / density
                val calcTextSize = (TEXT_SIZE_ADDITION + (iconInDp / TEXT_SIZE_DIVIDER)).toInt()

                view.clickable_icon_image_description.apply{
                    textSize = calcTextSize.toFloat()
                    text = clickableIcon.iconDescription
                    isSingleLine = clickableIcon.singleLine
                }
            }
            else
            {
                view.clickable_icon_image_description.visibility = View.GONE
            }

            //set icon click listener
            if (iconClickListener != null)
            {
                view.clickable_icon_image_imageView.setOnClickListener {
                    when {
                        clickableIcon.fadeOutIconAfterSelection -> fadeOutClickedIcon(clickableIcon.iconDescription, adapterPosition)
                        else -> iconClickListener.onIconClicked(clickableIcon.iconDescription, true, adapterPosition)
                    }
                }
            }
        }
    }

    inner class ClickableIconsSelectableViewHolder(private val view: View): ClickableIconsBaseViewHolder(view)
    {
        override fun bind(clickableIcon: ClickableIcon)
        {
            var iconInDp = 100f
            //set the icon's padding
            view.setPadding(
                clickableIcon.paddingStart*density.toInt(),
                clickableIcon.paddingTop*density.toInt(),
                clickableIcon.paddingEnd*density.toInt(),
                clickableIcon.paddingBottom*density.toInt())

            //set the image resource
            view.clickable_icon_selectable_imageView.apply {
                setBackgroundResource(ripple.resourceId)
                if ((clickableIcon as SelectableIcon).iconSelectorResource != null)
                {
                    setImageDrawable((clickableIcon as SelectableIcon).iconSelectorResource)
                    iconInDp = (clickableIcon as SelectableIcon).iconSelectorResource!!.intrinsicWidth / density
                }
                else
                {
                    val drawable = baseContext.getDrawable(baseResources.getIdentifier((clickableIcon as SelectableIcon).selectorResource, clickableIcon.iconImageResourceDefType, baseContext.packageName))
                    setImageDrawable(drawable)
                    iconInDp = drawable?.intrinsicWidth!! / density
                }
            }

            //set the description if exists
            if (clickableIcon.iconDescription.isNotBlank() && clickableIcon.showIconDescriptionAsLabel)
            {
                //set the text's size
//                val iconInDp = (clickableIcon as SelectableIcon).iconSelectorResource!!.intrinsicWidth / density
                val calcTextSize = (TEXT_SIZE_ADDITION + (iconInDp / TEXT_SIZE_DIVIDER)).toInt()

                view.clickable_icon_selectable_description.apply {
                    textSize = calcTextSize.toFloat()
                    text = clickableIcon.iconDescription
                    isSingleLine = clickableIcon.singleLine
                }
            }
            else
            {
                view.clickable_icon_selectable_description.visibility = View.GONE
            }

            //set icon click listener
            if (iconClickListener != null)
            {
                view.clickable_icon_selectable_imageView.setOnClickListener {
                    it.isSelected = !it.isSelected
                    iconClickListener.onIconClicked(clickableIcon.iconDescription, it.isSelected, adapterPosition)
                }
            }
        }
    }

    inner class ClickableIconsAnimatedViewHolder(private val view: View): ClickableIconsBaseViewHolder(view)
    {
        override fun bind(clickableIcon: ClickableIcon)
        {
            //set the icon's padding
            view.setPadding(
                clickableIcon.paddingStart*density.toInt(),
                clickableIcon.paddingTop*density.toInt(),
                clickableIcon.paddingEnd*density.toInt(),
                clickableIcon.paddingBottom*density.toInt())

            view.clickable_icon_animated_imageView.apply {
                setBackgroundResource(ripple.resourceId)
                //set the icon's size
                val iconSize = ((clickableIcon as LottieAnimatedIcon).iconSize * density).toInt()
                layoutParams.width = iconSize
                layoutParams.height = iconSize
                //set the icon's animation
                val rawAnim = baseResources.getIdentifier(clickableIcon.iconImageResource, clickableIcon.iconImageResourceDefType, baseContext.packageName)
//                    view.context.getDrawable(baseResources.getIdentifier(clickableIcon.iconImageResource, clickableIcon.iconImageResourceDefType, baseContext.packageName))
                setAnimation(rawAnim)
                repeatCount = (clickableIcon as LottieAnimatedIcon).repeatCount
                speed = clickableIcon.animationSpeed
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                playAnimation()
            }

            //set the description if exists
            if (clickableIcon.iconDescription.isNotBlank() && clickableIcon.showIconDescriptionAsLabel)
            {
                //set the text's size
                val iconInDp = (clickableIcon as LottieAnimatedIcon).iconSize
                val calcTextSize = (TEXT_SIZE_ADDITION + (iconInDp / TEXT_SIZE_DIVIDER))

                view.clickable_icon_animated_description.apply {
                    textSize = calcTextSize.toFloat()
                    text = clickableIcon.iconDescription
                    isSingleLine = clickableIcon.singleLine
                }
            }
            else
            {
                view.clickable_icon_animated_description.visibility = View.GONE
            }

            //set icon click listener
            if (iconClickListener != null)
            {
                view.clickable_icon_animated_imageView.setOnClickListener {
                    if (clickableIcon.fadeOutIconAfterSelection)
                    {
                        fadeOutClickedIcon(clickableIcon.iconDescription, adapterPosition)
                    }
                    else
                    {
                        iconClickListener.onIconClicked(clickableIcon.iconDescription, true, adapterPosition)
                    }
                }
            }
        }
    }

    interface IconClickedListener
    {
        fun onIconClicked(iconDescription: String, isSelected: Boolean, position: Int)
    }
}