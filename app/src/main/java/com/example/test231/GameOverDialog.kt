import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.test231.R

open class GameOverDialog(context: Context, private val restartListener: () -> Unit) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //remove title bar
        setContentView(R.layout.game_over)

        val restartButton = findViewById<ImageButton>(R.id.button_over)
        restartButton.setOnClickListener {
            restartListener.invoke()  //It invokes a restartListener function and then dismisses the dialog.
            dismiss()
        }
    }
}
