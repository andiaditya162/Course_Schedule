package com.dityapra.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dityapra.courseschedule.R
import com.dityapra.courseschedule.notification.DailyReminder
import com.dityapra.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val darkModePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark)) as ListPreference
        val dailyReminderPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify)) as SwitchPreference
        darkModePreference.setOnPreferenceChangeListener { _, newValue ->
            when(newValue.toString()) {
                getString(R.string.pref_dark_auto) -> updateTheme(NightMode.AUTO.value)
                getString(R.string.pref_dark_on) -> updateTheme(NightMode.ON.value)
                getString(R.string.pref_dark_off) -> updateTheme(NightMode.OFF.value)
            }
            true
        }

        val dailyReminder = DailyReminder()
        dailyReminderPreference.setOnPreferenceChangeListener { preference, newValue ->
            val checked = newValue as Boolean
            if (checked) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}