package com.ybh.memomemo.view.note.baseNote.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.Editable
import androidx.core.content.FileProvider
import com.ybh.memomemo.Data.NoteImage
import com.ybh.memomemo.Data.UserData
import com.ybh.memomemo.Others.RealPathUtil
import com.ybh.memomemo.view.main.MainActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat

open class WritableNotePresenter : WritableNoteContract.Presenter, NotePresenter(){

    lateinit var tempFilePath: String

    override fun setCurrentTitle(changedTitle: Editable?) {
        UserData.currentNote.title = changedTitle.toString()
    }

    override fun setCurrentBody(changedBody: Editable?) {
        UserData.currentNote.text = changedBody.toString()
    }

    override fun onMenuFabClicked() {
        if((view as WritableNoteContract.View).isFabListOpen())
        {
            (view as WritableNoteContract.View).closeFabList()
        }else
        {
            (view as WritableNoteContract.View).openFabList()
        }
    }

    override fun onCameraFabClicked() {
        (view as WritableNoteContract.View).startCameraActivity()
    }

    override fun onGalleryFabClicked() {
        (view as WritableNoteContract.View).startGalleryActivity()
    }

    override fun addImageFromGallery(context: Context?, data : Intent?)
    {
        val newImage = NoteImage(0,null,
            RealPathUtil.getRealPath(context!!,data?.data!!).toString())
        UserData.currentImages.add(newImage)

        (view as WritableNoteContract.View).refreshViewPager()
    }

    override fun addImageFromCamera() {
        val newImage = NoteImage(0,null,tempFilePath)
        UserData.currentImages.add(newImage)

        (view as WritableNoteContract.View).refreshViewPager()
    }

    override fun createNewImageUri(context: Context?) : Uri {
        lateinit var photoURI: Uri

        val photoFile: File? = try {
            createImageFile(context)
        } catch (ex: IOException) {
            null
        }
        photoFile?.also {
            photoURI = FileProvider.getUriForFile(
                context!!,
                "com.ybh.memomemo.provider",
                it
            )
        }

        return photoURI
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context?) : File {
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis())
        val storageDir : File? = (context as MainActivity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply{
            tempFilePath = absolutePath
        }
    }

}
