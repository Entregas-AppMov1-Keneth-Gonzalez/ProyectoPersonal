package cr.ac.utn.kenethgonzalez.proyectopersonal

import Adapters.PersonListAdapter
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.PersonModel

class PersonListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var personModel = PersonModel(this)
        val lstCustomList = findViewById<ListView>(R.id.lstPersonList)
        var personList = personModel.getPersons()

        val adapter = PersonListAdapter (this, R.layout.person_item_list, personList)
        lstCustomList.adapter = adapter

        lstCustomList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val personId = personList[position].Id
            util.openActivity(this, AddPersonasActivity::class.java, EXTRA_MESSAGE_ID, personId)
        }
    }
}