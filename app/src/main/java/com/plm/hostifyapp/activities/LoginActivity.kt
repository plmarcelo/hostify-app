package com.plm.hostifyapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.plm.hostifyapp.R
import com.plm.hostifyapp.models.User
import com.plm.hostifyapp.network.HostifyServer
import com.plm.hostifyapp.network.login.LoginService
import com.plm.hostifyapp.requests.Login
import com.plm.hostifyapp.utils.SessionManagerUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity() : AppCompatActivity() {
    private var loginServer = HostifyServer<LoginService>()
    private val loginService: LoginService = loginServer.create(LoginService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username: String = findViewById<EditText>(R.id.usernameEditText).text.toString()
        val password: String = findViewById<EditText>(R.id.passwordEditText).text.toString()
        val login = Login(username, password)
        val call = loginService.doLogin(login)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Toast.makeText(this@LoginActivity, user?.token, Toast.LENGTH_SHORT).show()
                    SessionManagerUtil.startUserSession(this@LoginActivity, user?.ttl ?: 60)
                    SessionManagerUtil.storeUserToken(this@LoginActivity, user?.token ?: "")
                    SessionManagerUtil.storeUserName(this@LoginActivity, user?.username ?: "")
                } else
                    Toast.makeText(this@LoginActivity, "Wrong login", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}