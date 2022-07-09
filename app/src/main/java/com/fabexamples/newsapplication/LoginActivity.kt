package com.fabexamples.newsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.fabexamples.newsapplication.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity(){

    lateinit var loginBinding: ActivityLoginBinding
    private lateinit var username :String
    private lateinit var email :String
    var loginModel : LoginModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding  =  DataBindingUtil.setContentView(this,R.layout.activity_login)
        setClickListeners()

    }

    fun setClickListeners(){
        loginBinding.loginButton.setOnClickListener{

            username = loginBinding.etName.text.toString().trim()
            email = loginBinding.etEmail.text.toString().trim()

            if(!UtilityFunctions.checkIsNotNull(username)){
                loginBinding.etName.error ="Username should not be empty"
                loginBinding.etName.requestFocus()
            }
            else if(!UtilityFunctions.checkIsNotNull(email)){
                loginBinding.etEmail.error = "Email should not be empty"
                loginBinding.etEmail.requestFocus()
            }
            else{
                getLoginDetails()
            }
        }
    }

    private fun getLoginDetails(){

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.npoint.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(APIInterface::class.java)

        val call : Call<LoginModel> = apiInterface.getLoginDetails()
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel?>, response: Response<LoginModel?>) {
                if(response.body() != null) {
                    val data : LoginModel = response.body() as LoginModel
                    if(data.full_name == "Abhishek Singh" &&
                        data.token == "aa9b6f9f-408c-474b-9faa-811159697abc") {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                        Toast.makeText(this@LoginActivity,"Wrong Credentials",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Error connecting to server",Toast.LENGTH_SHORT).show()
            }
        })
    }
}