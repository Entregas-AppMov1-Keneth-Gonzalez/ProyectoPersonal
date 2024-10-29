package Entities

class Food {
    private var _idFood: String = ""
    private var _nameFood: String = ""
    private var _descriptionFood: String = ""
    private var _priceFood: Int = 0

    constructor()

    constructor(idFood: String, nameFood: String, descriptionFood: String, priceFood: Int){
        this._idFood = idFood
        this._nameFood = nameFood
        this._descriptionFood = descriptionFood
        this._priceFood = priceFood
    }

    var idFood: String
        get() = this._idFood
        set(value) {this._idFood = value}

    var nameFood: String
        get() = this._nameFood
        set(value) {this._nameFood = value}

    var descriptionFood: String
        get() = this._descriptionFood
        set(value)  {this._descriptionFood = value}

    var priceFood: Int
        get() = this._priceFood
        set(value) {this._priceFood = value}
}