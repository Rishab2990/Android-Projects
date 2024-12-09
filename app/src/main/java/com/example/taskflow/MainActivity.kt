package com.example.taskflow
import android.os.Bundle
import android.view.View.inflate
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var taskList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingButton)

        //
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = MyAdapter(taskList)
        recyclerView.adapter = adapter

        // Custom Dialog Box creation
        floatingActionButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)

           val  dialogView = inflate(this, R.layout.dialog_box, null)

            val addTask = dialogView.findViewById<Button>(R.id.addButton)
            val cancelTask = dialogView.findViewById<Button>(R.id.cancelButton)
            val userTask = dialogView.findViewById<AppCompatEditText>(R.id.entertask)


            alertDialog.setView(dialogView)
            val dialog = alertDialog.create()

            addTask.setOnClickListener {
                if (userTask.text?.isNotEmpty() == true) {
                    taskList.add(userTask.text.toString())
                    adapter.notifyItemInserted(adapter.itemCount - 1)
                    userTask.text?.clear()

                }
                    else {
                    Toast.makeText(this, "Enter the task", Toast.LENGTH_LONG).show()

                }
            }
            dialog.show()

            // cancel Task Button to Dismiss the Dialog Box
            cancelTask.setOnClickListener {
                dialog.dismiss()
            }
        }



    }
}





