package cr.ac.utn.kenethgonzalez.proyectopersonal

import Adapters.ComidaListAdapter
import Entities.Comida
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.ComidaModel

class ComidaCustomListActivity : AppCompatActivity() {
    lateinit var comidaModel: ComidaModel
    lateinit var comidaList: List<Comida>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comida_custom_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        comidaModel = ComidaModel(this)
        val lstCustomList = findViewById<ListView>(R.id.lstCustomComidaList)
        comidaList = comidaModel.getComidas()
        val adapter = ComidaListAdapter(this, R.layout.person_item_list, comidaList)
        lstCustomList.adapter = adapter

        lstCustomList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val comidaId = comidaList[position].Id
            util.openActivity(this, AddComidasActivity::class.java, EXTRA_MESSAGE_ID, comidaId)
        }
    }
}