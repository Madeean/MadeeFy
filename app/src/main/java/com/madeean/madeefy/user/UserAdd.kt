package com.madeean.madeefy.user

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentUris
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.madeean.madeefy.R
import com.madeean.madeefy.api.ApiRequest
import com.madeean.madeefy.api.Server
import com.madeean.madeefy.model.ModelDataMusik
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URISyntaxException


class UserAdd : AppCompatActivity() {

    lateinit var et_judul_user_add:EditText
    lateinit var et_deskripsi_user_add:EditText
    lateinit var sw_user_add: Switch
    lateinit var btn_file_user_add: Button
    lateinit var tv_nama_file_user_add:TextView
    lateinit var btn_upload_admin_add: Button
    var switches:Int? = 0


    lateinit var dialog: Dialog
    lateinit var btn_flac:Button
    lateinit var btn_mp3:Button

    var file:File? = null

    lateinit var tokenSP:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add)

        val sh = getSharedPreferences("MadeeFy", MODE_PRIVATE)
        tokenSP = sh.getString("token", "")!!

        et_judul_user_add = findViewById(R.id.et_judul_user_add)
        et_deskripsi_user_add = findViewById(R.id.et_deskripsi_user_add)
        sw_user_add = findViewById(R.id.sw_user_add)
        btn_file_user_add = findViewById(R.id.btn_file_user_add)
        tv_nama_file_user_add = findViewById(R.id.tv_nama_file_user_add)
        btn_upload_admin_add = findViewById(R.id.btn_upload_admin_add)

        dialog = Dialog(this)

        sw_user_add.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switches = 0
            } else {
                switches = 1
            }
        }

        btn_file_user_add.setOnClickListener {



            dialog.setContentView(R.layout.dialog_pilih_file)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setCancelable(true)
            btn_flac = dialog.findViewById(R.id.btn_dialog_flac)
            btn_mp3 = dialog.findViewById(R.id.btn_dialog_mp3)

            btn_flac.setOnClickListener {
                requestPermissionDexter("flac")
//                val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.type = "audio/flac"
//                startActivityForResult(intent, 1)
            }

            btn_mp3.setOnClickListener {
                requestPermissionDexter("mp3")
//                val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.type = "audio/mpeg"
//                startActivityForResult(intent, 1)
            }
            dialog.show()

        }


        val botttomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_user)

        botttomNavigationView.selectedItemId = R.id.plus

        botttomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, UserHome::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
                R.id.plus -> {

                    return@OnNavigationItemSelectedListener true
                }
                R.id.setting -> {
                    val intent = Intent(this, UserSetting::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.file_sendiri -> {
                    val intent = Intent(this, UserFileSendiri::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
            }
            false
        })

        btn_upload_admin_add.setOnClickListener {
            if(et_judul_user_add.text.toString().isEmpty()){
                et_judul_user_add.error = "Judul tidak boleh kosong"
                et_judul_user_add.requestFocus()

            }else if(et_deskripsi_user_add.text.toString().isEmpty()) {
                et_deskripsi_user_add.error = "Deskripsi tidak boleh kosong"
                et_deskripsi_user_add.requestFocus()

            }else if(file == null) {
                Toast.makeText(this, "File tidak boleh kosong", Toast.LENGTH_SHORT).show()

            }else{
                postHandle()
            }
        }



    }

    private fun postHandle(){
        val dialog = ProgressDialog(this@UserAdd)
        dialog.setMessage("Waiting")
        dialog.setCancelable(false)
        dialog.setInverseBackgroundForced(false)
        dialog.show()


        val judul:String = et_judul_user_add.text.toString()
        val deskripsi:String = et_deskripsi_user_add.text.toString()
        val file_lagu:File = file!!
        val token:String = tokenSP
        val publik = switches

        println("FILE"+judul + deskripsi + file_lagu + publik)

        val api: ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!

        val requestFile:RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),file_lagu!!)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file_lagu.name, requestFile)
        val judulBody:RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), judul)
        val deskripsiBody:RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), deskripsi)
        val publikBody:RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), publik.toString())

        val tampilData: Call<ModelDataMusik?>? = api.tambahMusik(token,body , judulBody, deskripsiBody, publikBody)

        tampilData?.enqueue(object : Callback<ModelDataMusik?> {
            override fun onResponse(call: Call<ModelDataMusik?>, response: Response<ModelDataMusik?>) {
                if (response.isSuccessful) {
                    dialog.hide();
                    Toast.makeText(this@UserAdd, "Berhasil tambah lagu", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserAdd, UserHome::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    dialog.hide();
                    val data = response.errorBody()?.string()
                    Toast.makeText(this@UserAdd, "gagal menambah lagu", Toast.LENGTH_SHORT).show()
                    print("ERROR "+data)
                }
            }

            override fun onFailure(call: Call<ModelDataMusik?>, t: Throwable) {
                dialog.hide();
                println("ERROR "+t.message)
                Toast.makeText(this@UserAdd, "Gagal menghubungi server", Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun requestPermissionDexter(item:String) {
        Dexter.withActivity(this) // below line is use to request the number of permissions which are required in our app.
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,  // below is the list of permissions
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now
                        val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
                        if(item == "flac"){
                            intent.type = "audio/flac"
                        }else{
                            intent.type = "audio/mpeg"
                        }
                        startActivityForResult(intent, 1)
                        Toast.makeText(
                            this@UserAdd,
                            "All the permissions are granted..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permanently, we will show user a dialog message.
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? ->
                // we are displaying a toast message for error message.
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }

    private fun showSettingsDialog() {

        // we are displaying an alert dialog for permissions
        val builder = AlertDialog.Builder(this@UserAdd)

        // below line is the title for our alert dialog.

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions")

        // below line is our message for our dialog

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog: DialogInterface, which: Int ->
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel()
            // below is the intent from which we are redirecting our user.
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int ->
            // this method is called when user click on negative button.
            dialog.cancel()
        }
        // below line is used to display our dialog
        // below line is used to display our dialog
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {

//            get file path
            val uri= data?.data
            val string = getFilePath(this, uri!!)
            file = File(string)
            tv_nama_file_user_add.text = file!!.name
            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()
            dialog.cancel()


        }
    }


}

@SuppressLint("NewApi")
@Throws(URISyntaxException::class)
fun getFilePath(context: Context, uri: Uri): String? {
    var uri = uri
    var selection: String? = null
    var selectionArgs: Array<String>? = null
    // Uri is different in versions after KITKAT (Android 4.4), we need to
    if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
            context.getApplicationContext(),
            uri
        )
    ) {
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            uri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]
            if ("image" == type) {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            selection = "_id=?"
            selectionArgs = arrayOf(
                split[1]
            )
        }
    }
    if ("content".equals(uri.scheme, ignoreCase = true)) {
        if (isGooglePhotosUri(uri)) {
            return uri.lastPathSegment
        }
        val projection = arrayOf(
            MediaStore.Images.Media.DATA
        )
        var cursor: Cursor? = null
        try {
            cursor = context.getContentResolver()
                .query(uri, projection, selection, selectionArgs, null)
            val column_index: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    if (cursor != null) {
                        return column_index?.let { cursor.getString(it) }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}

fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}