package com.ybh.memomemo.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.FileProvider
import com.ybh.memomemo.Activity.MainActivity
import com.ybh.memomemo.Data.NoteImage
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.Others.RealPathUtil
import com.ybh.memomemo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat


const val OPEN_GALLERY : Int = 0x1
const val OPEN_CAMERA : Int = 0x2

open class WritableNoteFragment : NoteFragment()
{
    lateinit var tempFilePath: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view  = super.onCreateView(inflater, container, savedInstanceState)
        val et_title : EditText? = view?.findViewById<EditText>(R.id.et_title_note)
        val et_body : EditText? = view?.findViewById<EditText>(R.id.et_body_note)

        et_title?.isFocusable=true
        et_body?.isFocusable=true

        et_title?.addTextChangedListener(object :
            TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                UserData.currentNote.title = s.toString()
            }
        })

        et_body?.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                UserData.currentNote.text = s.toString()
            }
        })

        val fab_menu : FloatingActionButton? = view?.findViewById(R.id.fab_menu_note)
        val fab_camera : FloatingActionButton? = view?.findViewById(R.id.fab_camera_note)
        val fab_gallery : FloatingActionButton? = view?.findViewById(R.id.fab_gallery_note)

        fab_menu?.show()
        fab_menu?.setOnClickListener {
            if(fab_camera?.visibility==View.VISIBLE)
            {
                fab_menu.setImageResource(R.drawable.plus)
                fab_camera.hide()
                fab_gallery?.hide()
            }
            else
            {
                fab_menu.setImageResource(R.drawable.minus)
                fab_camera?.show()
                fab_gallery?.show()
            }
        }

        fab_gallery?.setOnClickListener { openGallery() }
        
        fab_camera?.setOnClickListener { openCamera() }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK)
        {
            when(requestCode)
            {
                OPEN_GALLERY -> {
                    val newImage : NoteImage = NoteImage(0,UserData.currentNote.id?:0,RealPathUtil.getRealPath(context!!,data?.data!!).toString())
                    UserData.currentImages.add(newImage)
                    //super.viewPagerAdapter.items.add(newImage)
                    super.viewPagerAdapter.notifyDataSetChanged()
                    super.imageViewPager.setCurrentItem(UserData.currentImages.size-1)
                }
                OPEN_CAMERA -> {
                    val newImage : NoteImage =NoteImage(0,UserData.currentNote.id?:0,tempFilePath)
                    UserData.currentImages.add(newImage)
                    //super.viewPagerAdapter.items.add(newImage)
                    super.viewPagerAdapter.notifyDataSetChanged()
                    super.imageViewPager.setCurrentItem(UserData.currentImages.size-1)
                }
            }
        }
        else { }
    }

    fun openGallery() {
        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity((activity as MainActivity).packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch(ex:IOException){
                    null
                }
                photoFile?.also{
                    val photoURI : Uri = FileProvider.getUriForFile(
                        (activity as MainActivity).context!!,
                        "com.ybh.memomemo.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, OPEN_CAMERA)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile() : File{
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis())
        val storageDir : File? = (activity as MainActivity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply{
            tempFilePath = absolutePath
        }
    }

}