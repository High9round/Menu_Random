package com.hi9h_9r0und.menu_random

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.FileOutputStream
import java.io.IOException

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        button_Add.setOnClickListener {

            setAddButton()

        }

        button_remove.setOnClickListener{

            if(MenuList.menu.size==0)
            {
                Toast.makeText(this,"항목이 없습니다",Toast.LENGTH_SHORT).show()
            }
            else
                setRemoveButton()
        }

    }

    fun setAddButton()
    {
        var alert:AlertDialog.Builder=AlertDialog.Builder(this)

        alert.setTitle("추가할 메뉴?")

        val input=EditText(this)
        alert.setView(input)

        alert.setPositiveButton("추가", object:DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                //val username = name.getText().toString()
                var isOverrap=false
                for (s in MenuList.menu)
                {
                    if(input.text.toString()==s)
                    {
                        isOverrap=true
                    }
                }

                if(isOverrap)
                {
                    Toast.makeText(applicationContext,"이미 있는 메뉴입니다.",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    MenuList.menu.add(input.text.toString())
                    Toast.makeText(applicationContext,"메뉴가 추가되었습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        })
        alert.setNegativeButton("취소",object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {

            }
        })

        alert.show()
    }

    fun setRemoveButton()
    {
        var cs = MenuList.menu.toArray(arrayOfNulls<CharSequence>(MenuList.menu.size))

        var isChecked=ArrayList<Boolean>()

        for(i in MenuList.menu)
        {
            isChecked.add(false)
        }

        var builder=AlertDialog.Builder(this)
        builder.setTitle("삭제할 항목을 선택하세요.")
                .setPositiveButton("삭제",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                        var isRemove=false
                        for (b in isChecked)
                        {
                            if(b)
                            {
                                isRemove=true
                                break
                            }
                        }

                        var removeList=ArrayList<String>()

                        for(i in isChecked.indices)
                        {
                            if(isChecked[i])
                            {
                                removeList.add(MenuList.menu[i])
                            }
                        }

                        for(r in removeList)
                        {
                            MenuList.menu.remove(r)
                        }
                        if(isRemove)
                            Toast.makeText(applicationContext,"메뉴가 삭제되었습니다.",Toast.LENGTH_SHORT).show()
                    }

                })
                .setNegativeButton("취소", null)
                .setMultiChoiceItems(cs, isChecked.toBooleanArray(), object :DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        isChecked[p1]=p2
                    }

                })
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater=menuInflater
        inflater.inflate(R.menu.menu_setting, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.action_back)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        //save menuList
        val fileName="savedata"
        try {
            var os:FileOutputStream=openFileOutput(fileName, Context.MODE_PRIVATE)
            for(s in MenuList.menu)
            {
                os.write(s.toByteArray())
                os.write("\n".toByteArray())
            }

            os.close()
            Toast.makeText(this,"파일이 저장 되었습니다. ",Toast.LENGTH_SHORT).show()

        }
        catch (e: IOException)
        {
        e.printStackTrace()
    }
    }
}

