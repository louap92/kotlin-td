package com.example.td_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import network.Api
import network.TasksRepository
import com.example.td_2.network.Api
import com.example.td_2.network.TasksViewModel

private val cocoScope = MainScope()
private val tasksRepository = TasksRepository()
private val tasks = mutableListOf<Task>()

class TasksFragment : Fragment() {
    private val coroutineScope = MainScope()
    private val adapter = TasksAdapter()
    private val viewModel by lazy {
        ViewModelProvider(this).get(TasksViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_td2.adapter = adapter
        recycler_view_td2.layoutManager = LinearLayoutManager(activity)
        add_button.setOnClickListener{
            // Instanciation d'un objet task avec des données préremplies:
            val task = Task(title = "Task!")
            viewModel.addTask(task)
        }
        adapter.onDeleteClickListener = {task ->
            viewModel.deleteTask(task)
        }

        viewModel.taskListLiveData.observe(this, Observer { newList ->
            adapter.tasks = newList.orEmpty()
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            recycler_info.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        viewModel.loadTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}