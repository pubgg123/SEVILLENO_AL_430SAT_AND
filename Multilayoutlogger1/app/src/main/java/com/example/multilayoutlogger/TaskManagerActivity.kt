package com.example.multilayoutlogger

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class TaskManagerActivity : AppCompatActivity() {
    private lateinit var editTextTask: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var listViewTasks: ListView
    private lateinit var tasks: ArrayList<Task>
    private lateinit var adapter: TaskAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_manager)
        
        initializeViews()
        setupSpinner()
        loadTasks()
        setupListeners()
    }
    
    private fun initializeViews() {
        editTextTask = findViewById(R.id.editTextTask)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        listViewTasks = findViewById(R.id.listViewTasks)
        tasks = ArrayList()
        adapter = TaskAdapter(this, tasks)
        listViewTasks.adapter = adapter
    }
    
    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.task_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }
    }
    
    private fun setupListeners() {
        findViewById<Button>(R.id.buttonAddTask).setOnClickListener {
            addTask()
        }
        
        findViewById<Button>(R.id.buttonClearAll).setOnClickListener {
            clearAllTasks()
        }
        
        listViewTasks.setOnItemClickListener { _, _, position, _ ->
            val task = tasks[position]
            Snackbar.make(
                listViewTasks,
                "Task: ${task.name} (${task.category})",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        
        listViewTasks.setOnItemLongClickListener { _, _, position, _ ->
            removeTask(position)
            true
        }
    }
    
    private fun addTask() {
        val taskName = editTextTask.text.toString().trim()
        if (taskName.isEmpty()) {
            editTextTask.error = "Please enter a task"
            return
        }
        
        val category = spinnerCategory.selectedItem.toString()
        val task = Task(taskName, category)
        tasks.add(task)
        adapter.notifyDataSetChanged()
        editTextTask.text.clear()
        saveTasks()
    }
    
    private fun removeTask(position: Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        saveTasks()
    }
    
    private fun clearAllTasks() {
        tasks.clear()
        adapter.notifyDataSetChanged()
        saveTasks()
    }
    
    private fun saveTasks() {
        val prefs = getSharedPreferences("TaskManager", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("tasks", tasks.joinToString("|||") { "${it.name}::${it.category}" })
        editor.apply()
    }
    
    private fun loadTasks() {
        val prefs = getSharedPreferences("TaskManager", Context.MODE_PRIVATE)
        val tasksString = prefs.getString("tasks", "") ?: ""
        if (tasksString.isNotEmpty()) {
            tasksString.split("|||").forEach { taskString ->
                val (name, category) = taskString.split("::")
                tasks.add(Task(name, category))
            }
            adapter.notifyDataSetChanged()
        }
    }
}

data class Task(val name: String, val category: String)

class TaskAdapter(context: Context, private val tasks: List<Task>) :
    ArrayAdapter<Task>(context, 0, tasks) {
    
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_task, parent, false)
        
        val task = tasks[position]
        view.findViewById<TextView>(R.id.textViewTask).text = task.name
        view.findViewById<TextView>(R.id.textViewCategory).text = task.category
        
        return view
    }
} 