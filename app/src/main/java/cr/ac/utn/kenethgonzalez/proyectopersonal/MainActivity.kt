package cr.ac.utn.kenethgonzalez.proyectopersonal

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

        val btnPerson: Button = findViewById<Button>(R.id.btnMainAddPerson)
        btnPerson.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, AddPersonasActivity::class.java)
        })

        val btnPersonList: Button = findViewById<Button>(R.id.btnMainViewPersonList)
        btnPersonList.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, PersonListActivity::class.java)
        })

        val btnPersonCustomList: Button = findViewById<Button>(R.id.btnCustomPersonList)
        btnPersonCustomList.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, PersonCustomListActivity::class.java)
        })

        val btnMainDialog = findViewById<Button>(R.id.btnMainDialog)
        btnMainDialog.setOnClickListener(View.OnClickListener {
            DisplayDialog()
        })

        val btnFood: Button = findViewById<Button>(R.id.btnMainAddFood)
        btnFood.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, AddComidasActivity::class.java)
        })

        val btnFoodList: Button = findViewById<Button>(R.id.btnMainViewFoodList)
        btnFoodList.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, ComidaListActivity::class.java)
        })

        val btnFoodCustomList: Button = findViewById<Button>(R.id.btnCustomFoodList)
        btnFoodCustomList.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, PersonCustomListActivity::class.java)
        })
    }

override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.menu_addPerson -> {
            util.openActivity(this, AddPersonasActivity::class.java)
            true
        }
        R.id.menu_personList-> {
            util.openActivity(this, PersonListActivity::class.java)
            true
        }
        R.id.menu_addFood -> {
            util.openActivity(this, AddComidasActivity::class.java)
            true
        }
        R.id.menu_foodList-> {
            util.openActivity(this, ComidaListActivity::class.java)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}

private fun DisplayDialog(){
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(getString(R.string.btn_CloseApp))
        .setCancelable(false)
        .setPositiveButton(getString(R.string.Yes), DialogInterface.OnClickListener{
                dialog, id -> finish()
        })
        .setNegativeButton(getString(R.string.No), DialogInterface.OnClickListener {
                dialog, id -> dialog.cancel()
        })

    val alert = dialogBuilder.create()
    alert.setTitle(getString(R.string.Yes))
    alert.show()
    }
}