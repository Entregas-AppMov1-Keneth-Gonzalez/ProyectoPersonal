package Entities

import android.graphics.Bitmap

class Comida(
    private var _id: String = "",
    private var _name: String = "",
    private var _descripcion: String = "",
    private var _precio: Int = 0,
    var photoBitmap: Bitmap? = null
) {
    var Id: String
        get() = this._id
        set(value) {this._id = value}

    var Name: String
        get() = this._name
        set(value) {this._name = value}

    var Descripcion: String
        get() = this._descripcion
        set(value) {this._descripcion = value}

    val FullDescription get() = this._name + " " + this._precio

    var Precio: Int
        get() = this._precio
        set(value) {this._precio = value}
}