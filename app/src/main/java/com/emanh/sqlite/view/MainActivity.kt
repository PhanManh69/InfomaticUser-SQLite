package com.emanh.sqlite.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanh.sqlite.databinding.ActivityMainBinding
import com.emanh.sqlite.model.UserModel
import com.emanh.sqlite.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ItemUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        initUI()
    }

    private fun initUI() {

        viewModel.allUser.observe(this, Observer {

            adapter = ItemUserAdapter(viewModel, mutableListOf())
            binding.listUsers.layoutManager = LinearLayoutManager(this)
            binding.listUsers.adapter = adapter

            it?.let {
                adapter.updateData(it)
            }
        })

        binding.buttonAddUser.setOnClickListener {
            val username = binding.editTextName.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            val year = binding.editTextYear.text.toString().trim()

            if (username.isNotEmpty() && address.isNotEmpty() && year.isNotEmpty()) {
                isUsernameExist(username) { exists ->
                    if (!exists) {
                        val user = UserModel(username = username, address = address, year = year.toInt())
                        viewModel.insert(user)

                        binding.editTextName.text = null
                        binding.editTextAddress.text = null

                        Toast.makeText(this, "Thêm người dùng thành công", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Tên người dùng đã tồn tại", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
            }
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val username = s.toString().trim()
                if (username.isNotEmpty()) {
                    searchUser(username)
                } else {
                    viewModel.allUser.observe(this@MainActivity, Observer {
                        adapter.updateData(it)
                    })
                }
            }
        })
    }

    private fun isUsernameExist(username: String, callback: (Boolean) -> Unit) {
        val listUsername: LiveData<MutableList<UserModel>> = viewModel.checkUsername(username)
        listUsername.observe(this, Observer { users ->
            if (users.isEmpty()) {
                callback(false)
            } else {
                callback(true)
            }
            listUsername.removeObservers(this)
        })
    }

    private fun searchUser(username: String) {
        viewModel.searchUsername(username).observe(this, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })
    }

}