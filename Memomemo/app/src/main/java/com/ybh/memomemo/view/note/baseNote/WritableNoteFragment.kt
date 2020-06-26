package com.ybh.memomemo.view.note.baseNote

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
import com.ybh.memomemo.view.main.MainActivity
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ybh.memomemo.view.note.baseNote.presenter.WritableNoteContract
import com.ybh.memomemo.view.note.baseNote.presenter.WritableNotePresenter


const val OPEN_GALLERY : Int = 0x1
const val OPEN_CAMERA : Int = 0x2

open class WritableNoteFragment : NoteFragment(), WritableNoteContract.View
{
    lateinit var fab_menu : FloatingActionButton
    lateinit var fab_camera : FloatingActionButton
    lateinit var fab_gallery : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view  = super.onCreateView(inflater, container, savedInstanceState)

        titleEditText.isFocusable=true
        bodyEditText.isFocusable=true

        titleEditText.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                (presenter as WritableNotePresenter).setCurrentTitle(s)
            }
        })

        bodyEditText?.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                (presenter as WritableNotePresenter).setCurrentBody(s)
            }
        })

        fab_menu = view?.findViewById(R.id.fab_menu_note)!!
        fab_camera  = view.findViewById(R.id.fab_camera_note)
        fab_gallery = view.findViewById(R.id.fab_gallery_note)

        fab_menu.show()
        fab_menu.setOnClickListener { (presenter as WritableNotePresenter).onMenuFabClicked() }

        fab_gallery.setOnClickListener { (presenter as WritableNotePresenter).onGalleryFabClicked() }
        
        fab_camera.setOnClickListener { (presenter as WritableNotePresenter).onCameraFabClicked() }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK)
        {
            when(requestCode)
            {
                OPEN_GALLERY -> {
                    (presenter as WritableNotePresenter).addImageFromGallery(context,data)
                }
                OPEN_CAMERA -> {
                    (presenter as WritableNotePresenter).addImageFromCamera()
                }
            }
        }
        else { }
    }

    override fun setPresenter() {
        presenter = WritableNotePresenter()
        (presenter as WritableNotePresenter).takeView(this)
    }

    override fun isFabListOpen(): Boolean {
        return fab_camera?.visibility==View.VISIBLE
    }

    override fun openFabList() {
        fab_menu.setImageResource(R.drawable.minus)
        fab_camera?.show()
        fab_gallery?.show()
    }

    override fun closeFabList() {
        fab_menu.setImageResource(R.drawable.plus)
        fab_camera.hide()
        fab_gallery.hide()
    }

    override fun startCameraActivity() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity((activity as MainActivity).packageManager)?.also {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, (presenter as WritableNotePresenter).createNewImageUri((activity as MainActivity).context))
                    startActivityForResult(takePictureIntent,
                        OPEN_CAMERA
                    )
                }
            }
    }

    override fun startGalleryActivity() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun refreshViewPager() {
        super.viewPagerAdapter.notifyDataSetChanged()
        super.imageViewPager.setCurrentItem(UserData.currentImages.size-1)
    }

}