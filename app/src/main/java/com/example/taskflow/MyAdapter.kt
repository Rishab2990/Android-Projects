package com.example.taskflow

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val taskList:MutableList<String>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // View holder hold the views means how each view look like in recycler view
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
       val eachItem = view.findViewById<TextView>(R.id.eachText)!!
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item , parent ,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eachItem.text = taskList[position]

        holder.checkbox.setOnClickListener {
            if(holder.checkbox.isChecked){
                taskList.removeAt(position)
                    notifyItemRemoved(position)
                notifyItemRangeChanged(position , taskList.size)
                    holder.checkbox.isChecked=false
            }

        }
        // item click listener in recycler view
        holder.eachItem.setOnClickListener {
            val alertDialog = AlertDialog.Builder(holder.itemView.context)
            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_box, null)

            // Button and Edit text for dialog box user Input and functionality
            val addTask = dialogView.findViewById<Button>(R.id.addButton)
            val cancelTask = dialogView.findViewById<Button>(R.id.cancelButton)
            val userTask = dialogView.findViewById<AppCompatEditText>(R.id.entertask)

          userTask.setText(taskList[position])
            alertDialog.setView(dialogView)
            val dialog = alertDialog.create()

            addTask.setOnClickListener {
                val updatedTask = userTask.text.toString()
                if (updatedTask.isNotEmpty()) {
                    taskList[position] = updatedTask // Update the task in the list
                    notifyItemChanged(position) // Notify the adapter to refresh the item
                    dialog.dismiss()
                } else {
                    userTask.error = "Task cannot be empty"
                }
            }
            cancelTask.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }
    override fun getItemCount(): Int {
        return  taskList.size

    }
}