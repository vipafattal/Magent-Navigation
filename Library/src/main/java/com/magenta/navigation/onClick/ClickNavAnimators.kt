package com.magenta.navigation.onClick

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.support.design.card.MaterialCardView
import android.support.graphics.drawable.ArgbEvaluator
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.magenta.navigation.helpers.doOnEnd
import com.magenta.navigation.helpers.get
import com.magenta.navigation.helpers.unitFun

/**
 * Created by Abed on 2/25/2018.
 */


@SuppressLint("RestrictedApi")
internal inline fun ViewGroup.cardBackgroundAnimator(duration: Long, activeNavColor: Int,defaultNavColor:Int, accentActiveColor:Int, accentDefaultColor: Int, crossinline doOnEnd: unitFun) {
    val cardView = this[0] as MaterialCardView
    val imgView = (cardView[0] as RelativeLayout)[0] as ImageView
    val textView = this[1] as? TextView

    val cardViewAnimator = ValueAnimator.ofObject(ArgbEvaluator(),defaultNavColor, activeNavColor)
    cardViewAnimator.duration = duration

    val imgViewAnimator = ValueAnimator.ofObject(ArgbEvaluator(), accentDefaultColor,accentActiveColor)
    imgViewAnimator.duration = duration - 50

    val textViewAnimator = ValueAnimator.ofObject(ArgbEvaluator(), accentDefaultColor, activeNavColor)
    textViewAnimator.duration = duration - 50
    cardViewAnimator.addUpdateListener {
        cardView.setCardBackgroundColor(it.animatedValue as Int)
    }
    imgViewAnimator.addUpdateListener {
        imgView.setColorFilter(it.animatedValue as Int)
    }

    textViewAnimator.addUpdateListener {
        textView?.setTextColor(it.animatedValue as Int)
    }
    cardViewAnimator.start()
    imgViewAnimator.start()
    textViewAnimator.start()

    cardViewAnimator.doOnEnd {
        doOnEnd.invoke()
    }
}
