package com.hi9h_9r0und.menu_random

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getMenu()

        button_getMenu.setOnClickListener {
            lateinit var strResult:String

            if(MenuList.menu.size>0)
            {
                strResult=MenuList.menu[rand(0,MenuList.menu.size)]
            }
            else
            {
                strResult="후보가 없어"
            }

            textView_result.text=strResult
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater=menuInflater
        inflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.action_setting)
        {
            var intent= Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    fun getMenu()
    {
        MenuList.menu.clear()

        val fileName="savedata"

        var file= File(fileName)

            try
            {
                var fis = openFileInput(fileName)
                var isr = InputStreamReader(fis)

                var bufferedReader = BufferedReader(isr)
                bufferedReader.lineSequence().forEach {
                    MenuList.menu.add(it)
                }

                Toast.makeText(this,"파일을 불러왔습니다.",Toast.LENGTH_SHORT).show()

            }
            catch (e:IOException)
            {
                e.printStackTrace()
            }





    }

    fun rand(from: Int, to: Int) : Int {
        var random= Random()
        return random.nextInt(to - from) + from
    }
}
