package sweetbeanjelly.project.drawing_board.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

internal class Brush (
    var x: Float,
    var y: Float,
    var status: Int,
    var color: Int,
    var size: Int )
{

    val isMove: Boolean
        get() = status == STATE_MOVE

    companion object {
        // 그리기 상태
        const val STATE_START = 0
        const val STATE_MOVE = 1
    }
}

internal class DrawCanvas : View {
    val brush_size = 3 // 그리기
    val remove_size = 30 // 지우기
    var drawList : ArrayList<Brush>? = null
    var paint : Paint? = null
    var loadDrawImage : Bitmap? = null // 불러오기
    var color = 0 // 현재
    var size = 0 // 현재

    fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        drawList = ArrayList<Brush>()
        loadDrawImage = null
        color = Color.BLACK
        size = brush_size
    }

    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    val currentCanvas: Bitmap
        get() {
            val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            draw(canvas)
            return bitmap
        }

    fun changeTool(toolMode: Int) {
        if (toolMode == BrushMode) {
            color = Color.BLACK
            size = brush_size
        } else {
            color = Color.WHITE
            size = remove_size
        }
        paint!!.color = color
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        if (loadDrawImage != null) { canvas.drawBitmap(loadDrawImage!!, 0f, 0f, null) }
        for (i in drawList!!.indices) {
            val temp: Brush = drawList!![i]
            paint!!.color = temp.color
            paint!!.strokeWidth = temp.size.toFloat()
            if (temp.isMove) {
                val pre_temp: Brush = drawList!![i - 1]
                canvas.drawLine(pre_temp.x, pre_temp.y, temp.x, temp.y, paint!!)
            }
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        val state: Int = if (action == MotionEvent.ACTION_DOWN) Brush.STATE_START else Brush.STATE_MOVE
        drawList!!.add(
            Brush(
                e.x,
                e.y,
                state,
                color,
                size
            )
        )
        invalidate()
        return true
    }

    companion object {
        const val BrushMode = 1
        const val RemoveMode = 0
    }
}