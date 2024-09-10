package com.emanh.sqlite.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.emanh.sqlite.databinding.ActivityUpdateUserBinding
import com.emanh.sqlite.model.UserModel
import com.emanh.sqlite.viewModel.UserViewModel

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var viewModel: UserViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        initUI()
    }

    private fun initUI() {
        userId = intent.getIntExtra("userId", 0)

        viewModel.getUserById(userId).observe(this) { user ->
            if (user != null) {
                binding.editTextName.setText(user.username)
                binding.editTextAddress.setText(user.address)
                binding.editTextYear.setText(user.year.toString())
            }
        }

        binding.buttonUpdate.setOnClickListener {
            val username = binding.editTextName.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            val year = binding.editTextYear.text.toString().trim()

            if (username.isNotEmpty() && address.isNotEmpty() && year.isNotEmpty()) {
                val user = UserModel(id = userId, username = username, address = address, year = year.toInt())
                viewModel.update(user)

                Toast.makeText(this, "Sửa thông tin thành công", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
            }
        }
    }
}