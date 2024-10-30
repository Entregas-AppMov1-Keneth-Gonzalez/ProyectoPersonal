package cr.ac.utn.kenethgonzalez.proyectopersonal

import Util.util
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin: Button = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener(View.OnClickListener {
            util.openActivity(this, LoginActivity::class.java)
        })

        val btnMainDialog: Button = findViewById<Button>(R.id.btnCloseDialog)
        btnMainDialog.setOnClickListener(View.OnClickListener {
            DisplayCloseDialog()
        })

    }

    private fun DisplayCloseDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.closedialog).setCancelable(false).setPositiveButton(R.string.yes, DialogInterface.OnClickListener{
                dialog, id -> finish()
        })
            .setNegativeButton(R.string.no, DialogInterface.OnClickListener{
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle(R.string.closedialog)
        alert.show()
    }
}