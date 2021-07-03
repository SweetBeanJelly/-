package sweetbeanjelly.project.drawing_board.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

// 저장, 호출

private val file = "save_file.jpg"

fun saveFile(context: Context, save_file: Bitmap) {
    try {
        val fileOS = context.openFileOutput(file, Context.MODE_PRIVATE)
        save_file.compress(Bitmap.CompressFormat.PNG, 100, fileOS)
        fileOS.close()
    } catch (error: IOException) {
        println("save error")
    }
}

fun getFile(context: Context): Bitmap? {
    var result: Bitmap? = null

    try {
        val fileIS = context.openFileInput(file)
        result = BitmapFactory.decodeStream(fileIS)
        fileIS.close()
    } catch (error: IOException) { println("open error") }

    return result
}