package com.watnow.campusdiary.Calendar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.*
import com.watnow.campusdiary.R
import com.watnow.campusdiary.RealmDB.CalendarDB
import com.watnow.campusdiary.Utils.Constant
import io.realm.Realm
import io.realm.RealmObject
import kotlinx.android.synthetic.main.activity_calendar_add_schedule.*
import kotlinx.android.synthetic.main.layout_calendar_add_alert_dialog.*

class CalendarAddScheduleActivity : AppCompatActivity() {

    var colorId: Int = R.color.ruby

    lateinit var realm: Realm
    private lateinit var inflatedView: View
    private var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_add_schedule)
        date = intent.extras.getString(Constant.INTENT_KEY_DATE.name)
        val updateDateNum: Int = intent.extras.getInt("listPosition")
        val isUpdate: Boolean = intent.hasExtra("listPosition")
        if (isUpdate) {
            val updateDate: String = intent.extras.getString("date")
            realm = Realm.getDefaultInstance()
            val updateInfo = realm.where(CalendarDB::class.java).equalTo("date",updateDate).findAll()[updateDateNum]
            new_event_title.setText(updateInfo.title)
            button_color_select.setBackgroundColor(getColorFromString(updateInfo.theme))
            new_event_navigation.setBackgroundColor(getColorFromString(updateInfo.theme))
            new_event_title.setBackgroundColor(getColorFromString(updateInfo.theme))
            new_event_detail.setText(updateInfo.detail)
        }

        new_event_theme.setOnClickListener {
            inflatedView = layoutInflater.inflate(R.layout.layout_calendar_add_alert_dialog, null)
            val radioGroup = inflatedView.findViewById<RadioGroup>(R.id.radioGroup)
            val dialog = AlertDialog.Builder(this@CalendarAddScheduleActivity).apply {
                setView(inflatedView)
                setPositiveButton("OK") {dialogInterface, i ->
                    val selectedButton = inflatedView.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                    colorId = getSelectedColorFromRadio(selectedButton)
                    button_color_select.setBackgroundResource(colorId)
                    new_event_navigation.setBackgroundResource(colorId)
                    new_event_title.setBackgroundResource(colorId)
                }

                show()
            }
        }

        new_event_cancel_btn.setOnClickListener {
            finish()
        }

        new_event_save_btn.setOnClickListener {
            realm.beginTransaction()
            val targetDB: CalendarDB
            if (isUpdate) {
                val updateDate: String = intent.extras.getString("date")
                targetDB = realm.where(CalendarDB::class.java).equalTo("date",updateDate).findAll()[updateDateNum]

            } else {
                targetDB = realm.createObject(CalendarDB::class.java)
            }

            targetDB.title = new_event_title.text.toString()
            val radioGroup = inflatedView.findViewById<RadioGroup>(R.id.radioGroup)
            targetDB.theme = inflatedView.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
            targetDB.detail = new_event_detail.text.toString()
            targetDB.date = date
            realm.commitTransaction()
            finish()
            Toast.makeText(this@CalendarAddScheduleActivity, "登録しました", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    private fun getSelectedColorFromRadio(selectedButton: RadioButton): Int {
        var selectedColorId: Int = 0
        when (selectedButton.id) {
            R.id.radioDiamond -> selectedColorId = R.color.diamond
            R.id.radioPerl -> selectedColorId = R.color.perl
            R.id.radioRuby -> selectedColorId = R.color.ruby
            R.id.radioSapphire -> selectedColorId = R.color.sapphire
            R.id.radioTopaz -> selectedColorId = R.color.topaz
            R.id.radioEmerald -> selectedColorId = R.color.emerald
            R.id.radioGold -> selectedColorId = R.color.gold
            R.id.radioAmethyst -> selectedColorId = R.color.amethyst
            R.id.radioTigerEye -> selectedColorId = R.color.tigerEye
        }

        return selectedColorId
    }

    private fun getColorFromString(colorName: String): Int {
        var selectedColorId: Int = 0
        when (colorName) {
            "ルビー" -> selectedColorId = ContextCompat.getColor(this, R.color.ruby)
            "サファイア" -> selectedColorId = ContextCompat.getColor(this, R.color.sapphire)
            "エメラルド" -> selectedColorId = ContextCompat.getColor(this, R.color.emerald)
            "ゴールド" -> selectedColorId = ContextCompat.getColor(this, R.color.gold)
            "パール" -> selectedColorId = ContextCompat.getColor(this, R.color.perl)
            "アメジスト" -> selectedColorId = ContextCompat.getColor(this, R.color.amethyst)
            "タイガーアイ" -> selectedColorId = ContextCompat.getColor(this, R.color.tigerEye)
            "トパーズ" -> selectedColorId = ContextCompat.getColor(this, R.color.topaz)
            "ダイヤモンド" -> selectedColorId = ContextCompat.getColor(this, R.color.diamond)
        }
        return selectedColorId
    }
}
