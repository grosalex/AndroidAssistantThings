package com.grosalex.androidassistantthings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class TaskActivity : Activity(), View.OnClickListener {
    private var RC_ADD_TASK: Int = 1001
    private var TASK_PREFS:String = "TASK_LIST"
    private var TASKS: String = "TASKS"

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.ib_recreate_task -> {
               /* val intent = Intent(this, AddTaskActivity::class.java)
                startActivityForResult(intent, RC_ADD_TASK)*/
                createTaskList()

            }
            R.id.ibBack ->{
                finish()
            }
        }
    }

    private var  rvTask: RecyclerView? = null

    private var taskArrayList: ArrayList<Task>? =null

    private var taskAdapter: TaskAdapter? = null

    private var mLayoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val ibAdd = findViewById(R.id.ib_recreate_task)
        val ibBack = findViewById(R.id.ibBack)

        ibAdd.setOnClickListener(this)
        ibBack.setOnClickListener(this)
        rvTask = findViewById(R.id.rv_task) as RecyclerView
        taskArrayList = ArrayList<Task>()
        mLayoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        taskAdapter = TaskAdapter(this, taskArrayList as ArrayList<Task>)

        rvTask?.layoutManager = mLayoutManager
        rvTask?.adapter = taskAdapter
        loadTasks()

    }

    private fun loadTasks() {
        var gson = Gson()
        var sharedPrefs = getSharedPreferences(TASK_PREFS,0)

        val turnsType = object : TypeToken<ArrayList<Task>>() {}.type

        var s : String = sharedPrefs.getString(TASKS,"")
        if(s.isNullOrEmpty())return
        Log.d("LISTLOAD",s)
        taskArrayList = gson.fromJson(s,turnsType)
        taskAdapter?.datas= taskArrayList as ArrayList<Task>
        taskAdapter?.notifyDataSetChanged()

    }

    private fun createTaskList() {
        taskArrayList?.clear()
        taskArrayList?.add(Task("Faire les comptes",Date(),30,true))
        taskArrayList?.add(Task("Vaisselle", Date(),1,true))
        taskArrayList?.add(Task("Descendre poubelle ",Date(),3,true))
        taskArrayList?.add(Task("Salon",Date(),3,true))
        taskArrayList?.add(Task("Salle sur demande",Date(),7,true))
        taskArrayList?.add(Task("Chambre",Date(),4,true))
        taskArrayList?.add(Task("Cuisine sol lavette",Date(),7,true))
        taskArrayList?.add(Task("Cuisine sol aspi",Date(),3,true))
        taskArrayList?.add(Task("Salle de bain wc",Date(),7,true))
        taskArrayList?.add(Task("Salle de bain douche",Date(),7,true))
        taskArrayList?.add(Task("Salle de bain sol aspi",Date(),3,true))
        taskArrayList?.add(Task("Salle de bain sol lavette",Date(),7,true))
        taskArrayList?.add(Task("Salle de bain lavabo",Date(),7,true))
        taskAdapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= RESULT_OK) return
        if(requestCode==RC_ADD_TASK){

        }
    }


    override fun onDestroy() {
            var gson = Gson()
            var sharedPrefs = this.getSharedPreferences(TASK_PREFS, 0)
            var editor = sharedPrefs.edit()
            editor.putString(TASKS,gson.toJson(taskArrayList))
            editor.apply()
        Log.d("onDestroy",gson.toJson(taskArrayList))

        super.onDestroy()
        }
}
