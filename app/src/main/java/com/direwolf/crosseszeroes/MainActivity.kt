package com.direwolf.crosseszeroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.direwolf.crosseszeroes.*
import android.widget.*
import android.graphics.Color
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

public class MainActivity : DefaultActivity() {
	
	private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
    }
	
	override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
		val minSize = ((screenWidth * 0.8f / 3.1f / 0.05f).toInt() * 3.1f * 0.05f).toInt()
		val layout = findViewById<ConstraintLayout>(R.id.layout)

		val textView = ConstraintLayout(this)
		textView.setBackgroundColor(Color.WHITE)

		val layoutParams = ConstraintLayout.LayoutParams(minSize - 10, minSize - 10)
		layoutParams.topToTop = ConstraintSet.PARENT_ID
		layoutParams.bottomToBottom = ConstraintSet.PARENT_ID
		layoutParams.startToStart = ConstraintSet.PARENT_ID
		layoutParams.endToEnd = ConstraintSet.PARENT_ID
		textView.layoutParams = layoutParams

		layout.addView(textView)
		
		game = Game(this, textView)
		
		val pause = findViewById<ImageView>(R.id.pause)
		pause.setOnClickListener {
			
			window.decorView.windowInsetsController!!.hide(android.view.WindowInsets.Type.statusBars())
			game.set_pause()
			if (game.isOnPause()) pause.setImageResource(R.drawable.play)
			else pause.setImageResource(R.drawable.pause)
		}
		val start = findViewById<ImageView>(R.id.start)
		start.setOnClickListener {
			window.decorView.windowInsetsController!!.hide(android.view.WindowInsets.Type.statusBars())
			game.restart()
			pause.setImageResource(R.drawable.pause)
		}
	}
}