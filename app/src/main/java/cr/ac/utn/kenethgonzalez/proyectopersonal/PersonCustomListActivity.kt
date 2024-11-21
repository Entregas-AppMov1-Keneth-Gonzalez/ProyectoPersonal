package cr.ac.utn.kenethgonzalez.proyectopersonal

import Adapters.PersonListAdapter
import Entities.Person
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.PersonModel

class PersonCustomListActivity : AppCompatActivity() {
    lateinit var personModel: PersonModel
    lateinit var personList: List<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person_custom_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        personModel = PersonModel(this)
        val lstCustomList = findViewById<ListView>(R.id.lstCustomPersonList)
        personList = personModel.getPersons()
        val adapter = PersonListAdapter(this, R.layout.person_item_list, personList)
        lstCustomList.adapter = adapter

        lstCustomList.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val fullName = personList[position].FullDescription
            util.openActivity(this, AddPersonasActivity::class.java, EXTRA_MESSAGE_ID, fullName)
        }
    }
}