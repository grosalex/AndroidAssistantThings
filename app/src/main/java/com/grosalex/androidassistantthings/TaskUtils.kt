package com.grosalex.androidassistantthings

import android.content.Context

/**
 * Created by grosalex on 9/24/17.
 */

var RC_ADD_TASK: Int = 1001
var TASK_PREFS: String = "TASK_LIST"
var TASKS: String = "TASKS"

fun taskList(context: Context): String {
    var sharedPrefs = context.getSharedPreferences(TASK_PREFS, 0)
    return sharedPrefs.getString(TASKS, "")

}