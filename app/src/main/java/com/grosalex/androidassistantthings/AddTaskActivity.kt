package com.grosalex.androidassistantthings

import android.app.Activity
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class AddTaskActivity : Activity(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when(v?.id){
            R.id.ib_add -> {

            }
            R.id.ib_back -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        var ibBack = findViewById(R.id.ib_back) as ImageButton
        var ibAdd = findViewById(R.id.ib_add) as ImageButton

        ibBack.setOnClickListener(this)
        ibAdd.setOnClickListener(this)
    }
}
