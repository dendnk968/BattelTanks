package com.example.tanks.Map

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.random.Random

class Map {

    private lateinit var map: Array<IntArray>
    val height: Int
    val width: Int
    val heightCell: Int = 20
    val widthCell: Int = 20
    val scale: Int = 2

    val bitmap: Bitmap


    constructor(height: Int, width: Int) {
        this.height = height
        this.width = width

        bitmap = Bitmap.createBitmap(width * widthCell, height * heightCell,  Bitmap.Config.RGB_565)
        val r = Random(0)
        //Поменять на формирование битмапа карты
        for (i in 0 until height)
            for (j in 0 until width) {
                val color = if (r.nextBoolean()) Color.GREEN else Color.CYAN
                for (y in (i * heightCell) until (i * heightCell + heightCell))
                    for (x in (j * widthCell) until (j * widthCell + widthCell))
                        bitmap.setPixel(x, y, color)

            }
    }

    constructor(height: Int, width: Int, map: Array<IntArray>) : this(height, width) {
        this.map = map
        //Добавить битмап формирование

    }


}