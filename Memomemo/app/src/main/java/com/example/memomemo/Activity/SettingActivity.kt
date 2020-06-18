package com.example.memomemo.Activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.memomemo.R
import kotlinx.android.synthetic.main.activity_setting.*

const val PASSWORD_SETTING : Int = 0x3
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(toolbar_setting)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_setting,SettingFragment())
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }



    class SettingFragment : PreferenceFragmentCompat(){

        lateinit var usePasswordSwitch : Preference

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.setting)

            usePasswordSwitch = findPreference("isLock")
            val passWordSetting = findPreference("passwordSetting")

            val prefs : SharedPreferences = activity!!.getSharedPreferences("MAIN_SHARED_PREF",0)
            val defaultPrefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)

            passWordSetting.isEnabled = defaultPrefs.getBoolean("isLock",false)

            usePasswordSwitch.setOnPreferenceChangeListener { preference, newValue ->
                if(newValue as Boolean)
                {
                    passWordSetting.isEnabled = true
                    if(prefs.getString("PASSWORD","NO_PASSWORD") == "NO_PASSWORD")
                    {
                        val intent : Intent = Intent(this.context,PasswordSettingActivity::class.java)
                        startActivityForResult(intent, PASSWORD_SETTING)
                        false
                    }
                    else
                    {
                        true
                    }
                }else
                {
                    passWordSetting.isEnabled = false
                    true
                }

            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if(resultCode == Activity.RESULT_OK)
            {
                when(requestCode)
                {
                    PASSWORD_SETTING -> {
                        //val defaultPrefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
                        //defaultPrefs.edit().putBoolean("isLock",true).apply()
                        (usePasswordSwitch as SwitchPreference).isChecked = true
                    }
                }
            }
            else { }
        }
    }
}
