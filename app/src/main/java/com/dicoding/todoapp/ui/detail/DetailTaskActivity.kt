package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val taskId = intent.getIntExtra(TASK_ID, 1)
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailTaskViewModel::class.java)
        val detailEdTitle: EditText = findViewById(R.id.detail_ed_title)
        val detailEdDesc: EditText = findViewById(R.id.detail_ed_description)
        val detailEdDate: EditText = findViewById(R.id.detail_ed_due_date)
        val deleteButton: Button = findViewById(R.id.btn_delete_task)
        viewModel.setTaskId(taskId)
        viewModel.task.observe(this) { task ->
            detailEdTitle.setText(task.title)
            detailEdDesc.setText(task.description)
            detailEdDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))

            deleteButton.setOnClickListener {
                viewModel.task.removeObservers(this)
                viewModel.deleteTask()

                val taskActivityIntent = Intent(this, TaskActivity::class.java)
                this.startActivity(taskActivityIntent)
            }
        }
    }

}