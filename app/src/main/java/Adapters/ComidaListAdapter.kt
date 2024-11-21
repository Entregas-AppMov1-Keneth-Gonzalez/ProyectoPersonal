package Adapters

import Entities.Comida
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cr.ac.utn.kenethgonzalez.proyectopersonal.R

class ComidaListAdapter(private val context: Context, private val resource: Int, private val datasource: List<Comida>): ArrayAdapter<Comida>(context, resource, datasource){

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.comida_item_list, parent, false)
        val  lbPhone = rowView.findViewById(R.id.lbItemNombre) as TextView
        val  lbFullname = rowView.findViewById(R.id.lbItemPrecio) as TextView
        val  lbEmail = rowView.findViewById(R.id.lbItemDescripcion) as TextView

        val comida = datasource[position] as Comida
        lbPhone.text = comida.Name
        lbFullname.text = comida.Precio.toString()
        lbEmail.text = comida.Descripcion

        return rowView
    }
}