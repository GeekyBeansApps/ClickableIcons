package com.geekybeans.clickableiconslib.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.geekybeans.clickableiconslib.R
import com.geekybeans.clickableiconslib.models.AnimatedIcon
import com.geekybeans.clickableiconslib.models.ClickableIcon
import com.geekybeans.clickableiconslib.models.ImageIcon
import com.geekybeans.clickableiconslib.models.SelectableIcon
import kotlinx.android.synthetic.main.item_clickable_icon_image.view.*
import kotlinx.android.synthetic.main.item_clickable_icon_animtaion.view.*
import kotlinx.android.synthetic.main.item_clickable_icon_selectable.view.*


class ClickableIconsAdapter(private val clickableIcons: List<ClickableIcon>, val iconClickListener: IconClickedListener?):
    RecyclerView.Adapter<ClickableIconsAdapter.ClickableIconsBaseViewHolder>()
{
    companion object
    {
        val IMAGE_ICON_LAYOUT_ID:Int = R.layout.item_clickable_icon_image
        val ANIMATED_ICON_LAYOUT_ID = R.layout.item_clickable_icon_animtaion
        val SELECTABLE_ICON_LAYOUT_ID = R.layout.item_clickable_icon_selectable
    }

    override fun getItemCount() = clickableIcons.size

    override fun getItemViewType(position: Int): Int
    {
        return when(clickableIcons[position])
        {
            is ImageIcon -> IMAGE_ICON_LAYOUT_ID
            is AnimatedIcon -> ANIMATED_ICON_LAYOUT_ID
            is SelectableIcon -> SELECTABLE_ICON_LAYOUT_ID
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

        fun fadeOutClickedIcon(iconDescription: String)
        {
            val anim = AnimationUtils.loadAnimation(baseItemView.context, android.R.anim.fade_out)
            anim.duration = 300 //TODO: const

            anim.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?)
                {
                    iconClickListener?.onIconClicked(iconDescription)
                }
            })

            baseItemView.startAnimation(anim)
        }
    }

    inner class ClickableIconsImageViewHolder(private val view: View): ClickableIconsBaseViewHolder(view)
    {
        override fun bind(clickableIcon: ClickableIcon)
        {
            //set the image resource
            view.clickable_icon_image_imageView.setBackgroundResource(clickableIcon.iconImageResource)
            //set the description if exists
            if (clickableIcon.iconDescription.isNotBlank())
            {
                view.clickable_icon_image_description.text = clickableIcon.iconDescription
            }
            else
            {
                view.clickable_icon_image_description.visibility = View.GONE
            }
            //set icon click listener
            if (iconClickListener != null)
            {
                view.clickable_icon_image_imageView.setOnClickListener {
                    if (clickableIcon.fadeOutIconAfterSelection)
                    {
                        fadeOutClickedIcon(clickableIcon.iconDescription)
                    }
                    else
                    {
                        iconClickListener.onIconClicked(clickableIcon.iconDescription)
                    }
                }
            }
        }
    }

    inner class ClickableIconsSelectableViewHolder(val view: View): ClickableIconsBaseViewHolder(view)
    {
        override fun bind(clickableIcon: ClickableIcon)
        {
            //set the image resource
            view.clickable_icon_selectable_imageView.setBackgroundResource(clickableIcon.iconImageResource)
            //set the description if exists
            if (clickableIcon.iconDescription.isNotBlank())
            {
                view.clickable_icon_selectable_description.text = clickableIcon.iconDescription
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
                    iconClickListener.onIconClicked(clickableIcon.iconDescription)
                }
            }
        }
    }

    inner class ClickableIconsAnimatedViewHolder(val view: View): ClickableIconsBaseViewHolder(view)
    {
        override fun bind(clickableIcon: ClickableIcon)
        {
            view.clickable_icon_animated_imageView.apply {
                setAnimation(clickableIcon.iconImageResource)
                repeatCount = (clickableIcon as AnimatedIcon).repeatCount
                speed = (clickableIcon as AnimatedIcon).animationSpeed
                playAnimation()
            }

            //set the description if exists
            if (clickableIcon.iconDescription.isNotBlank())
            {
                view.clickable_icon_animated_description.text = clickableIcon.iconDescription
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
                        fadeOutClickedIcon(clickableIcon.iconDescription)
                    }
                    else
                    {
                        iconClickListener.onIconClicked(clickableIcon.iconDescription)
                    }
                }
            }
        }
    }

    interface IconClickedListener
    {
        fun onIconClicked(iconDescription: String)
    }
}