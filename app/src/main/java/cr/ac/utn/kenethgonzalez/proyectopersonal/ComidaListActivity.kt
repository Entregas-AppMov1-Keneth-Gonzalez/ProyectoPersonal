package cr.ac.utn.kenethgonzalez.proyectopersonal

import Adapters.ComidaListAdapter
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.ComidaModel

class ComidaListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comida_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var comidaModel = ComidaModel(this)
        val lstCustomList = findViewById<ListView>(R.id.lstComidaList)
        var comidaList = comidaModel.getComidas()

        val adapter = ComidaListAdapter (this, R.layout.comida_item_list, comidaList)
        lstCustomList.adapter = adapter

        lstCustomList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val comidaId = comidaList[position].Id
            util.openActivity(this, AddComidasActivity::class.java, EXTRA_MESSAGE_ID, comidaId)
        }
    }
}