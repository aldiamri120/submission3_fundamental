package com.dicoding.appgithub_2.alarm.reminder

import android.content.Context

class ReminderPreference(mContext: Context) {
    companion object {
        const val NAME_PREF = "pref"
        private const val REMINDER = "reminder"
    }

    private val pref = mContext.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)

    fun setAlarmReminder(util: Reminder) {
        val editor = pref.edit()
        editor.putBoolean(REMINDER, util.alarmReminder)
        editor.apply()
    }

    fun getAlarmReminder(): Reminder {
        val model = Reminder()
        model.alarmReminder = pref.getBoolean(REMINDER, false)
        return model
    }
}