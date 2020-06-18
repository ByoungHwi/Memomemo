package com.ybh.memomemo.Data


class UserData
{
    companion object{
        var userNoteList : MutableList<NoteData>? = mutableListOf()
        lateinit var currentNote : NoteData
        lateinit var currentImages : MutableList<NoteImage>
        lateinit var imagesBeforeUpdate : MutableList<NoteImage>

        fun clearUserCurrentItems() {
            currentNote = NoteData(null,null,null,System.currentTimeMillis(), null)
            currentImages = mutableListOf()
        }

        fun setNidOfCullentImages() {
            for(image : NoteImage in currentImages) image.nid = currentNote.id?:0
        }

        fun beforeEdit(){
            imagesBeforeUpdate = mutableListOf()
            imagesBeforeUpdate.addAll(currentImages)
        }
    }
}