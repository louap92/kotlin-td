package com.example.td_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*

class TasksAdapter(val tasks : List<Task>) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val task = tasks[position]
        holder.bind(task)


    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task){
            itemView.task_title.text = task.title
            itemView.task_description.text = task.description
            itemView.delete_button.setOnClickListener{onDeleteClickListener.invoke(task)}
        }
    }

    var onDeleteClickListener: (Task) -> Unit = {}


}