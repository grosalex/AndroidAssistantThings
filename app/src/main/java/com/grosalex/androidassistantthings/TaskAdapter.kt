package com.grosalex.androidassistantthings

import android.app.ActionBar
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

/**
 * Created by grosalex on 10/09/17.
 */

class TaskAdapter(val mContext :Context,var datas :ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun getItemCount(): Int {
        return  datas.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if(!datas.get(position).hasToBeDone()){
            holder.v.visibility = View.GONE
            holder.v.layoutParams.height=0
            holder.v.layoutParams.width=0
            return
        }else{
            holder.v.visibility = View.VISIBLE
            holder.v.layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT
            holder.v.layoutParams.width= ViewGroup.LayoutParams.WRAP_CONTENT
        }
        holder.taskTitle.setText(datas.get(position).title)

        holder.taskDoer.setText(if(datas.get(position).doer) "Alex" else "Chloé")

        holder.taskDone.setOnClickListener(View.OnClickListener {
            datas.get(position).hasBeenDone()
            Log.d("task",datas.get(position).toString())
            notifyItemChanged(position)
        })

        holder.taskSwapper.setOnClickListener(View.OnClickListener {
            datas.get(position).swap()
            notifyItemChanged(position)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder{
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(v)
    }

    class TaskViewHolder(val v:View) : RecyclerView.ViewHolder(v){


         var taskTitle: TextView

         var taskDoer: TextView

         var taskDone: Button

         var taskSwapper: ImageButton

        init {
            taskTitle = v.findViewById(R.id.task_title) as TextView
            taskDoer = v.findViewById(R.id.task_doer) as TextView
            taskDone = v.findViewById(R.id.task_done) as Button
            taskSwapper = v.findViewById(R.id.task_swap) as ImageButton
        }
    }
}