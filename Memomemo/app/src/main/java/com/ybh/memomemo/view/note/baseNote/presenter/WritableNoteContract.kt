package com.ybh.memomemo.view.note.baseNote.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable

interface WritableNoteContract {

    interface View{
        fun isFabListOpen() : Boolean
        fun openFabList()
        fun closeFabList()
        fun startCameraActivity()
        fun startGalleryActivity()
        fun refreshViewPager()
    }

    interface Presenter{
        fun onMenuFabClicked()
        fun onCameraFabClicked()
        fun onGalleryFabClicked()
        fun setCurrentTitle(changedTitle : Editable?)
        fun setCurrentBody(changedBody : Editable?)
        fun addImageFromGallery(context: Context?, data : Intent?)
        fun addImageFromCamera()
        fun createNewImageUri(context: Context?) : Uri
    }
}