package com.example.tanks.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.View
import com.example.tanks.R

/**
 * TODO: document your custom view class.
 */
class MapView : View {
    // Дефолтные значения видимой ширины карты
    private val DEFAULT_SHOW_WIDTH_MAP = 16
    //Дефолтные значения видимой  высоты
    private val DEFAULT_SHOW_HEIGHT_MAP = 9

    private var widthShowMap = 16
    private var heightShowMap = 9

    private var widthCell = 20
    private var heightCell = 20


    private val paint = Paint()


    private var map: Map = Map(9, 16)
    private var showMap: Bitmap
    private var buffer: Bitmap
    private var offset = Pair(0, 0)
        set(value) {
            field = value
            updateShowBitmap(value)
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.MapView
            )
            widthShowMap =
                typedArray.getInt(R.styleable.MapView_show_map_width, DEFAULT_SHOW_WIDTH_MAP)
            heightShowMap =
                typedArray.getInt(R.styleable.MapView_show_map_height, DEFAULT_SHOW_HEIGHT_MAP)
            typedArray.recycle()
        }
        showMap = Bitmap.createBitmap(
            widthShowMap * widthCell,
            heightCell * heightShowMap,
            Bitmap.Config.RGB_565
        )

        buffer = Bitmap.createBitmap(
            widthShowMap * widthCell,
            heightCell * heightShowMap,
            Bitmap.Config.RGB_565
        )

        updateShowBitmap(offset)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.scale(
            width.toFloat() / (widthCell * widthShowMap),
            height.toFloat() / (heightCell * heightShowMap)
        )
        canvas.drawBitmap(showMap, 0F, 0F, paint)
    }

    private fun updateShowBitmap(value: Pair<Int, Int>) {
        synchronized(map) {
            for (i in 0 until (heightCell * heightShowMap)) {
                for (j in 0 until (widthCell * widthShowMap)) {

                    val color =
                        if(i + value.first < 0 || j + value.second < 0
                            || i + value.first >= map.height * map.heightCell
                            || j + value.second >= map.width * map.widthCell)
                            Color.BLACK
                        else
                            map.bitmap.getPixel(j + offset.second, i + offset.first)
                    buffer.setPixel(j, i, color)
                }
            }
        }
        showMap = buffer
        invalidate()
    }
}
