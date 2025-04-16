package com.direwolf.crosseszeroes

import com.direwolf.crosseszeroes.*
import android.widget.*
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout

class Game(private val context:DefaultActivity,
			private val field: ConstraintLayout) {
	private val cells = mutableListOf<Cell>()
	private var pause = false
	private val pole = mutableListOf<MutableList<Int>>()
	private var hod = 0
	private var inGame = true
	
	init {
		
		val cellSize = (field.layoutParams.width / 3.1f).toInt() + 2
		for (i in 0..8){
			val cell = Cell(field, cellSize.toInt(), i, ::onClick, context)
			cells.add(cell)
		}
		for (i in 0..2){
			val row = mutableListOf(0,0,0)
			pole.add(row)
		}
	}
	
	fun onClick(i:Int):Int{
		//showToast(i.toString())
		if (pause || !inGame) return 0
		if (i in 0..8){
			if (pole[i/3][i%3]!=0)return 0
			pole[i/3][i%3] = hod + 1
			hod = (hod + 1) % 2
			for (i in 0..8) cells[i].update(hod)
			check()
			return (hod + 1) % 2 + 1
		} else {
			//showToast("r$i")
			val n = i/10-1
			pole[n/3][n%3] = 0
		}
		return 0
	}
	
	fun set_pause(){
		if (inGame){
			pause = !pause
		}
	}
	
	protected fun showToast(text: String){
		val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
	
		toast.show()
	}
	
	fun check(){
		hod=(hod+1)%2
		var flag = false
		for (i in 0..2){
			flag = flag || (pole[i][0] ==hod+1&& pole[i][1] ==hod+1&& pole[i][2] == hod + 1)
			flag = flag || (pole[0][i] ==hod+1&& pole[1][i] ==hod+1&& pole[2][i] == hod + 1)
		}
		flag = flag || (pole[0][0]==hod+1&&pole[1][1]==hod+1&&pole[2][2]==hod+1)
		flag = flag || (pole[2][0]==hod+1&&pole[1][1]==hod+1&&pole[0][2]==hod+1)
		
		if (flag) win()
		hod=(hod+1)%2
	}
	
	fun win(){
		var txt = "win "
		txt += if (hod==0)"crosses" else "zeroes"
		showToast(txt)
		//set_pause()
		inGame = false
		for (x in 0..8){
			if (cells[x].isCross()==1+(hod+1)%2) cells[x].darkMode()
		}
	}
	
	fun restart(){
		pause = false
		inGame = true
		for (i in 0..8){
			cells[i].reset()
		}
		for (i in 0..2) pole[i] = mutableListOf(0,0,0)
		hod = 0
	}
	
	fun isOnPause(): Boolean {
		return pause
	}
}