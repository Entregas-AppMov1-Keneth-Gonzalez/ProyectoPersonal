package cr.ac.utn.kenethgonzalez.proyectopersonal

import Entities.Person
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.PersonModel

class AddPersonasActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtRol: EditText
    private lateinit var imgPhoto: ImageView
    private lateinit var btnAddPhoto: Button
    private lateinit var rowImgPhoto: TableRow

    private lateinit var personModel: PersonModel
    private var isEditionMode: Boolean = false
    private lateinit var menuItemDelete: MenuItem

    private var selectedPhotoBitmap: Bitmap? = null
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_personas)

        txtId = findViewById(R.id.txtPersonID)
        txtName = findViewById(R.id.txtPersonName)
        txtLastName = findViewById(R.id.txtPersonLastName)
        txtPhone = findViewById(R.id.txtPersonPhone)
        txtEmail = findViewById(R.id.txtPersonEmail)
        txtRol = findViewById(R.id.txtPersonRol)
        imgPhoto = findViewById(R.id.imgPhoto)
        btnAddPhoto = findViewById(R.id.btnAddPhoto)
        rowImgPhoto = findViewById(R.id.rowImgPhoto)

        rowImgPhoto.visibility = View.GONE

        personModel = PersonModel(this)

        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imgPhoto.setImageBitmap(imageBitmap)
                selectedPhotoBitmap = imageBitmap
                rowImgPhoto.visibility = View.VISIBLE
            } else {
                Toast.makeText(this,R.string.err_Photo, Toast.LENGTH_SHORT).show()
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                imgPhoto.setImageBitmap(bitmap)
                selectedPhotoBitmap = bitmap
                rowImgPhoto.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, R.string.err_Photo, Toast.LENGTH_SHORT).show()
            }
        }

        btnAddPhoto.setOnClickListener {
            val options = arrayOf(getString(R.string.takePhoto), getString(R.string.pickFromGallery))
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.takePhoto))
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        if (checkAndRequestCameraPermission()) {
                            openCamera()
                        }
                    }
                    1 -> openGallery()
                }
            }
            builder.show()
        }

        val personId = intent.getStringExtra(EXTRA_MESSAGE_ID)
        if (personId != null && personId.isNotEmpty()) loadPerson(personId)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkAndRequestCameraPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, R.string.CameraPermissionDenied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(takePictureIntent)
        } else {
            Toast.makeText(this, R.string.cameraisnotavalilable, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(pickPhotoIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.crud_menu, menu)
        menuItemDelete = menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditionMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                savePerson()
                true
            }
            R.id.mnu_delete -> {
                deletePerson()
                true
            }
            R.id.mnu_cancel -> {
                cleanScreen()
                val intentAddContacttoHome = Intent(this, MainActivity::class.java)
                startActivity(intentAddContacttoHome)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePerson() {
        try {
            val person = Person(
                _id = txtId.text.toString(),
                _name = txtName.text.toString(),
                _lastName = txtLastName.text.toString(),
                _phone = txtPhone.text.toString().toIntOrNull() ?: 0,
                _email = txtEmail.text.toString(),
                _rol = txtRol.text.toString(),
                photoBitmap = selectedPhotoBitmap
            )

            if (dataValidation(person)) {
                if (!isEditionMode) {
                    personModel.addPerson(person)
                    val intentAddContacttoHome = Intent(this, MainActivity::class.java)
                    startActivity(intentAddContacttoHome)
                }else {
                    personModel.updatePerson(person)
                    val intentAddContacttoHome = Intent(this, MainActivity::class.java)
                    startActivity(intentAddContacttoHome)
                }
                cleanScreen()
                Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.FieldsRequired, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun dataValidation(person: Person): Boolean {
        return person.Id.isNotEmpty() &&
                person.Name.isNotEmpty() &&
                person.LastName.isNotEmpty() &&
                person.Phone > 0 &&
                person.Email.isNotEmpty() &&
                person.Rol.isNotEmpty()
    }

    private fun deletePerson() {
        personModel.removePerson(txtId.text.toString())
        Toast.makeText(this, R.string.dialog_Delete, Toast.LENGTH_LONG).show()
        cleanScreen()
        val intentAddContacttoHome = Intent(this, MainActivity::class.java)
        startActivity(intentAddContacttoHome)
    }

    private fun cleanScreen() {
        txtId.setText("")
        txtName.setText("")
        txtLastName.setText("")
        txtPhone.setText("")
        txtEmail.setText("")
        txtRol.setText("")
        imgPhoto.setImageResource(0)
        rowImgPhoto.visibility = View.GONE
        selectedPhotoBitmap = null
        txtId.isEnabled = true
        isEditionMode = false
        invalidateOptionsMenu()
    }

    private fun loadPerson(id: String) {
        try {
            val person = personModel.getPerson(id) as Person
            txtId.setText(person.Id)
            txtName.setText(person.Name)
            txtLastName.setText(person.LastName)
            txtPhone.setText(person.Phone.toString())
            txtEmail.setText(person.Email)
            txtRol.setText(person.Rol)
            person.photoBitmap?.let {
                imgPhoto.setImageBitmap(it)
                rowImgPhoto.visibility = View.VISIBLE
            } ?: imgPhoto.setImageResource(R.drawable.person_img)

            isEditionMode = true
            txtId.isEnabled = false
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}