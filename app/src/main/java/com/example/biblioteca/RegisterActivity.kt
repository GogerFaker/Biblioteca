package com.example.biblioteca

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import androidx.core.app.ActivityCompat
import android.os.Build
import android.util.Log


class RegisterActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 1

    lateinit var campoNome:TextInputEditText
    lateinit var campoEmail:TextInputEditText
    lateinit var campoTelefone:TextInputEditText
    lateinit var campoPassword:TextInputEditText
    lateinit var campoConfirmPass:TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        campoNome = findViewById(R.id.campoNome)
        campoEmail = findViewById(R.id.id_label_email)
        campoPassword = findViewById(R.id.id_label_password)
        campoConfirmPass = findViewById(R.id.id_label_confirmPass)
        campoTelefone = findViewById(R.id.id_label_phone)

        if (!checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            requestPermission(Manifest.permission.READ_PHONE_STATE)
        } else {
            preenchePhone()
        }
    }

    fun preenchePhone(){
        campoTelefone.setText(getPhone())
    }

    fun salvar(v:View){
        if(!valida())
        {
            return
        }

        Toast.makeText(this, getString(R.string.msg_registro_salvo), Toast.LENGTH_SHORT).show()
    }

    fun valida():Boolean {
        var result = true
        campoNome.error = null
        campoEmail.error = null
        campoPassword.error = null
        campoConfirmPass.error = null

        if (TextUtils.isEmpty(campoNome.text.toString())){
            campoNome.error = getString(R.string.msg_campo_obrigatorio)
            result = false
        }

        if (TextUtils.isEmpty(campoEmail.text.toString())){
            campoEmail.error = getString(R.string.msg_campo_obrigatorio)
            result = false
        }

        if (TextUtils.isEmpty(campoPassword.text.toString())){
            campoPassword.error = getString(R.string.msg_campo_obrigatorio)
            result = false
        }

        if (TextUtils.isEmpty(campoConfirmPass.text.toString())){
            campoConfirmPass.error = getString(R.string.msg_campo_obrigatorio)
            result = false
        }



        return result
    }

    private fun getPhone(): String {
        val phoneMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        return if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ""
        } else phoneMgr.line1Number
    }

    private fun requestPermission(permission: String) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(
                this,
                "Phone state permission allows us to get phone number. Please allow it for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        }

        ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                preenchePhone()
            } else {
                Toast.makeText(
                    this,
                    "Permission Denied. We can't get phone number.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            result == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
