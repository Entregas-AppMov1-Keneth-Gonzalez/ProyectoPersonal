package Adapters

import Entities.Person
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cr.ac.utn.kenethgonzalez.proyectopersonal.R

class PersonListAdapter(private val context: Context, private val resource: Int, private val datasource: List<Person>): ArrayAdapter<Person>(context, resource, datasource){

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.person_item_list, parent, false)
        val  lbPhone = rowView.findViewById(R.id.lbItemPhone) as TextView
        val  lbFullname = rowView.findViewById(R.id.lbItemFullName) as TextView
        val  lbEmail = rowView.findViewById(R.id.lbItemEmail) as TextView

        val person = datasource[position] as Person
        lbPhone.text = person.Phone.toString()
        lbFullname.text = person.FullDescription
        lbEmail.text = person.Email

        return rowView
    }
}