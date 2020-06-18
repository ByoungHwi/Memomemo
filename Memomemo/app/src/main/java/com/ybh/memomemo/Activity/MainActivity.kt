package com.ybh.memomemo.Activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ybh.memomemo.DB.DBhelper
import com.ybh.memomemo.Data.NoteImage
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.Fragment.*
import com.ybh.memomemo.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*

const val FRAGMENT_HOME : Short = 0x1
const val FRAGMENT_SHOW : Short = 0x2
const val FRAGMENT_ADD : Short = 0x3
const val FRAGMENT_EDIT : Short = 0x4

class MainActivity : AppCompatActivity() {

    private var nowFragment : Short = FRAGMENT_HOME
    var db : DBhelper? = null
    var context: Context? = null
    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() { // 권한 허가시 실행 할 내용
            //
        }
        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            // 권한 거부시 실행  할 내용
            Toast.makeText(this@MainActivity, "접근 권한 제한시 앱이 정상적으로 정상되지 않을 수 있습니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity;
        checkPermissions()

        //startService(Intent(this,AutoSaveService::class.java))  강제종료시 자동저장 구현
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main,HomeFragment())
            .commit()

        db = DBhelper(this)
        UserData.userNoteList = db?.getNoteList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)

        when(nowFragment)
        {
            FRAGMENT_HOME-> {
                menu?.findItem(R.id.setting_toolbar)?.setVisible(true)
                menu?.findItem(R.id.add_toolbar)?.setVisible(false)
                menu?.findItem(R.id.delete_toolbar)?.setVisible(false)
                menu?.findItem(R.id.edit_toolbar)?.setVisible(false)
            }
            FRAGMENT_ADD, FRAGMENT_EDIT-> {
                menu?.findItem(R.id.setting_toolbar)?.setVisible(false)
                menu?.findItem(R.id.add_toolbar)?.setVisible(true)
                menu?.findItem(R.id.delete_toolbar)?.setVisible(false)
                menu?.findItem(R.id.edit_toolbar)?.setVisible(false)
            }
            FRAGMENT_SHOW-> {
                menu?.findItem(R.id.setting_toolbar)?.setVisible(false)
                menu?.findItem(R.id.add_toolbar)?.setVisible(false)
                menu?.findItem(R.id.delete_toolbar)?.setVisible(true)
                menu?.findItem(R.id.edit_toolbar)?.setVisible(true)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.add_toolbar -> {
                saveNote()
                refreshUserNoteList()
                replaceFragment(ShowNoteFragment.newInstance())
                useShowFragment()
            }//등록버튼 온클릭
            R.id.delete_toolbar -> { makeDeleteAlert() }//삭제버튼 온클릭
            R.id.edit_toolbar -> { replaceFragment(EditNoteFragment.newInstance()) }//수정버튼 온클릭
            R.id.setting_toolbar -> {
                val intent : Intent = Intent(this,SettingActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttachFragment(fragment: Fragment?) {

        when(fragment)
        {
            is HomeFragment -> { useHomeFragment() }
            is AddNoteFragment -> { useAddFragment() }
            is EditNoteFragment -> { useEditFragment() }
            is ShowNoteFragment -> { useShowFragment() }
        }
        super.onAttachFragment(fragment)
    }

    override fun onBackPressed() {

        when(nowFragment)
        {
            FRAGMENT_SHOW -> {
                useHomeFragment()
                supportFragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            FRAGMENT_ADD -> {
                useHomeFragment()
                super.onBackPressed()
            }
            FRAGMENT_EDIT ->{
                useShowFragment()
                super.onBackPressed()
            }
            else -> super.onBackPressed()
        }
    }


    fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main,fragment)
            .addToBackStack(null)
            .commit()
    }

    fun getAttachedFragment() : Short { return nowFragment }

    fun useHomeFragment() {
        nowFragment = FRAGMENT_HOME
        title_toolbar_main.text = "메모 목록"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        invalidateOptionsMenu()
        UserData.clearUserCurrentItems()
    }

   fun useAddFragment() {
       nowFragment = FRAGMENT_ADD
       title_toolbar_main.text = "메모 등록"
       supportActionBar?.setDisplayHomeAsUpEnabled(true)
       invalidateOptionsMenu()
   }

    fun useEditFragment() {
        nowFragment = FRAGMENT_EDIT
        title_toolbar_main.text = "메모 수정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        invalidateOptionsMenu()
    }

    fun useShowFragment() {
        nowFragment = FRAGMENT_SHOW
        title_toolbar_main.text = "메모 조회"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        invalidateOptionsMenu()
    }

    fun refreshUserNoteList(){
        UserData.userNoteList = db?.getNoteList()
    }

    fun saveNote() {
        if(UserData.currentImages.size != 0) UserData.currentNote.thumnail = UserData.currentImages[0].uri

        when(nowFragment)
        {
            FRAGMENT_ADD -> {
                UserData.currentNote.id = db?.addNote(UserData.currentNote)
                UserData.setNidOfCullentImages()
                db?.addImages(UserData.currentImages)
            }
            FRAGMENT_EDIT -> {
                db?.updateNote(UserData.currentNote)
                db?.updateImages(UserData.currentImages,UserData.currentNote.id!!)
                deleteImageOnUpdate()
            }
        }
    }


    fun deleteNote() {
        db?.deleteNote(UserData.currentNote.id!!)
        if(UserData.currentNote.thumnail != null) db?.deleteImagesByNid(UserData.currentNote.id!!)
    }
    
    fun makeDeleteAlert() {
        val alertDialogBuilder : AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setMessage("메모를 삭제 하시겠습니까?")
            .setNegativeButton("예",DialogInterface.OnClickListener { _, _ ->
                deleteNote()
                refreshUserNoteList()
                when(nowFragment)
                {
                    FRAGMENT_SHOW -> supportFragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                useHomeFragment()
            })
            .setPositiveButton("아니오",DialogInterface.OnClickListener { _, _ ->  })

        val alertDialog : AlertDialog = alertDialogBuilder.create()
        alertDialog.setOnShowListener(object : DialogInterface.OnShowListener{
            override fun onShow(dialog: DialogInterface?) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            }
        })
        alertDialog.show()
    }

    fun deleteImageOnUpdate() {
        UserData.imagesBeforeUpdate.removeAll(UserData.currentImages)
        for(image : NoteImage in UserData.imagesBeforeUpdate)
        {
            db?.deleteImageById(image.id)
        }
    }


    fun setCurrentImages() {
        UserData.currentImages = db?.findImagesByNid(UserData.currentNote.id?:0)?: mutableListOf()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 26) { // 출처를 알 수 없는 앱 설정 화면 띄우기
            val pm: PackageManager = context!!.getPackageManager()
            Log.e("Package Name", packageName)
            if (!pm.canRequestPackageInstalls()) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        Uri.parse("package:$packageName")
                    )
                )
            }
        }

        if (Build.VERSION.SDK_INT >= 23) { // 마시멜로(안드로이드 6.0) 이상 권한 체크
            TedPermission.with(context)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다")
                .setDeniedMessage("앱에서 요구하는 권한설정이 필요합니다.\n [설정] > [권한] 에서 사용으로 활성화해주세요.")
                .setPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).check()
        } else {
        }
    }
}
