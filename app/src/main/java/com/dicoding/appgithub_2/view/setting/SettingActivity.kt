package com.dicoding.appgithub_2.view.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.appgithub_2.alarm.AlarmReceiver
import com.dicoding.appgithub_2.alarm.reminder.Reminder
import com.dicoding.appgithub_2.alarm.reminder.ReminderPreference
import com.dicoding.appgithub_2.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderPref = ReminderPreference(this)
        binding.swReminder.isChecked = reminderPref.getAlarmReminder().alarmReminder

        alarmReceiver = AlarmReceiver()

        binding.swReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                true.saveAlarm()
                alarmReceiver.setOnRepeatingAlarm(this,
                    "Alarm repeat", "09:00", "Reminder User")
            } else {
                alarmReceiver.setOffRepeatingAlarm(this)
            }
        }
    }

    private fun Boolean.saveAlarm() {
        val reminderPreference = ReminderPreference(this@SettingActivity)
        reminder = Reminder()

        reminder.alarmReminder = this
        reminderPreference.setAlarmReminder(reminder)
    }
}