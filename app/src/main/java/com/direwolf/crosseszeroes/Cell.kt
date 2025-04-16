package com.direwolf.crosseszeroes

import com.direwolf.crosseszeroes.*
import androidx.constraintlayout.widget.*
import android.widget.*
import android.view.View
import android.graphics.*
import android.view.Gravity
import android.util.TypedValue

class Cell(private val layout:ConstraintLayout,
 		private val size:Int,private val number:Int,private val function:(Int)->Int,
	 	context: DefaultActivity){
	private var lives = 3
	private val textView = ImageView(context)
	private val backView = ImageView(context)
	private var isCross_ = false
	private var setOn = false
			 
	init {
		val layoutParams1 = ConstraintLayout.LayoutParams(size, size)
		layoutParams1.topToTop = ConstraintSet.PARENT_ID
		layoutParams1.startToStart = ConstraintSet.PARENT_ID
		layoutParams1.leftMargin = (number % 3 * (size + size * 0.05f)).toInt()
		layoutParams1.topMargin = (number / 3 * (size + size * 0.05f)).toInt()
		backView.layoutParams = layoutParams1
		backView.setBackgroundColor(Color.BLACK)
		layout.addView(backView)
		
		val layoutParams = ConstraintLayout.LayoutParams((size * 0.8f).toInt(), (size * 0.8f).toInt())
		layoutParams.topToTop = ConstraintSet.PARENT_ID
		layoutParams.startToStart = ConstraintSet.PARENT_ID
		layoutParams.leftMargin = (0.1f * size + number % 3 * (size + size * 0.05f)).toInt()
		layoutParams.topMargin = (0.1f * size + number / 3 * (size + size * 0.05f)).toInt()
		textView.layoutParams = layoutParams
		textView.setBackgroundColor(Color.BLACK)
		textView.elevation = 1f
		layout.addView(textView)
		
		textView.setOnClickListener{
			val res = function(number)
			if (res==1 || res==2){
				setColor(res - 1)
				lives = 3
			}
		}
	}
	
	fun update(hod:Int){
		val my = if (isCross_) 0 else 1
		if (my!=hod)lives-=1
		if (lives == 1 && hod == my) darkMode()
		else if (lives==0 && setOn){
			reset()
		}
	}
	
	fun reset(){
		textView.setImageDrawable(null)
		function((number+1)*10)
		setOn = false
	}
	
	fun darkMode(){
		textView.setImageAlpha(140)
	}
	
	fun isCross():Int{
		return if(!setOn){ 0}
		else if(isCross_){ 1 }
		else{ 2}
	}
	
	private fun setColor(mode:Int){
		setOn = true
		lives = 3
		textView.setImageAlpha(255)
		when (mode){
			0 -> {//cross
				isCross_ = true
				textView.setImageResource(R.drawable.cross)
			}
			1 -> {
				isCross_ = false
				textView.setImageResource(R.drawable.zero)
			}
		}
	}
}