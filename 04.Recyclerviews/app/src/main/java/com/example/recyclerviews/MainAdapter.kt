package com.example.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviews.databinding.RecyclerviewItemBinding

class MainAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(private val itemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(task: Task) {
            itemBinding.titleTv.text = task.title
            itemBinding.timeTv.text = task.timestamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        // binding data into item cells
        val task = taskList[position]
        holder.bindItem(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}