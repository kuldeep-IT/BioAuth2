package com.example.mvvmwithdi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.databinding.DataBindingUtil
import com.example.mvvmwithdi.R
import com.example.mvvmwithdi.R.id.btnLogin
import com.example.mvvmwithdi.biometric.BiometricAuthListener
import com.example.mvvmwithdi.biometric.BiometricUtil
import com.example.mvvmwithdi.constants.Constants.ERROR_OBJECT
import com.example.mvvmwithdi.constants.Constants.FACE_DATA
import com.example.mvvmwithdi.databinding.ActivityMainBinding
import com.example.mvvmwithdi.model.FaceData
import com.example.mvvmwithdi.utils.Utils.getObjectBody
import com.example.mvvmwithdi.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener, BiometricAuthListener {


    private val dataViewModel: DataViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnLogin.setOnClickListener(this)

        dataViewModel.getData(FACE_DATA)
    }

    fun apiFaceData() {
        dataViewModel.getLiveData().observe(this) { response ->
            when (response) {
                is Resource.Success -> {

                    BiometricUtil.isBiometricReady(this)
                    onClickBiometrics()

                    when (response.endpoint) {
                        FACE_DATA -> {

                            response.data?.let { responseData ->
                                val faceDataModel =
                                    responseData.getObjectBody<FaceData>(className = FaceData::class.java)
                                Log.d("FACE_API_RESPONSE", responseData.toString())
                            }
//                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        }
                        ERROR_OBJECT -> {
                            Log.d("API_ERROR", "ERROR")
                        }
                    }

                }
                is Resource.Error -> {
                    Log.d("API_ERROR", response.message.toString())
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            btnLogin -> {
                if (!binding.edtMNumber.text.isNullOrBlank() && !binding.edtPass.text.isNullOrBlank()) {
                    apiFaceData()
//                    BiometricUtil.isBiometricReady(this)
//                    onClickBiometrics()
                    Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun onClickBiometrics() {
        BiometricUtil.showBiometricPrompt(
            activity = this,
            listener = this,
            cryptoObject = null,
            allowDeviceCredential = true,
        )
    }


    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        Toast.makeText(this, "Biometric success", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Toast.makeText(this, "Biometric login. Error: $errorMessage", Toast.LENGTH_SHORT)
            .show()
    }

    /*private fun showBiometricLoginOption() {
        buttonBiometricsLogin.visibility =
            if (BiometricUtil.isBiometricReady(this)) View.VISIBLE
            else View.GONE
    }*/


}