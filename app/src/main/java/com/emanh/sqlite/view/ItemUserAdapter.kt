package com.emanh.sqlite.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.emanh.sqlite.databinding.ViewholderItemUserBinding
import com.emanh.sqlite.model.UserModel
import com.emanh.sqlite.viewModel.UserViewModel

class ItemUserAdapter(
    private val viewModel: UserViewModel,
    private val items: MutableList<UserModel>
) : RecyclerView.Adapter<ItemUserAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ViewholderItemUserBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.binding.textViewUsername.text = item.username
        holder.binding.textViewAddress.text = item.address
        holder.binding.textViewYear.text = item.year.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateUserActivity::class.java)
            intent.putExtra("userId", item.id)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Xóa người dùng")
                setMessage("Bạn có chắc muốn xóa người dùng ${item.username} không?")
                setPositiveButton("Xóa") { dialog, _ ->
                    deleteUser(item)
                    dialog.dismiss()
                }
                setNegativeButton("Hủy") { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()

            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newUser: List<UserModel>) {
        items.clear()
        items.addAll(newUser)
        notifyDataSetChanged()
    }

    private fun deleteUser(user: UserModel) {
        viewModel.delete(user)

        val position = items.indexOf(user)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}