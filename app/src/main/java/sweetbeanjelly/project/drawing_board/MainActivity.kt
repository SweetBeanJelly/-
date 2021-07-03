package sweetbeanjelly.project.drawing_board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sweetbeanjelly.project.drawing_board.canvas.DrawCanvas
import sweetbeanjelly.project.drawing_board.canvas.getFile
import sweetbeanjelly.project.drawing_board.canvas.saveFile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val canvas = findViewById<ConstraintLayout>(R.id.canvas)
        val draw = DrawCanvas(this)
        canvas.addView(draw)

        // view
        val btnDrawing = findViewById<FloatingActionButton>(R.id.btn_drawing)
        val btnEraser = findViewById<FloatingActionButton>(R.id.btn_eraser)
        val btnSave = findViewById<FloatingActionButton>(R.id.btn_save)
        val btnReload = findViewById<FloatingActionButton>(R.id.btn_reload)
        val btnGallery = findViewById<FloatingActionButton>(R.id.btn_gallery)

        // setOnClickListener
        btnDrawing.setOnClickListener {
            draw.changeTool(DrawCanvas.BrushMode)
        }
        btnEraser.setOnClickListener {
            draw.changeTool(DrawCanvas.RemoveMode)
        }
        btnReload.setOnClickListener {
            draw.init()
            draw.invalidate()
        }
        btnSave.setOnClickListener {
            draw.invalidate()
            val save = draw.currentCanvas
            saveFile(this, save)
            Toast.makeText(this, "저장 !", Toast.LENGTH_SHORT).show()
        }
        btnGallery.setOnClickListener {
            draw.init()
            draw.loadDrawImage = getFile(this)
            draw.invalidate()
            Toast.makeText(this, "불러오기 !", Toast.LENGTH_SHORT).show()
        }
    }
}