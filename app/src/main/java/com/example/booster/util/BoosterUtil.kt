package com.example.booster.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.booster.R

class BoosterUtil(var context: Context) {
    fun getPathFromUri(uri: Uri?) : String?
    {
        if(uri==null){
            return ""
        }
        val cursor: Cursor? = context.getContentResolver().query(uri, null, null, null, null)
        cursor?.moveToNext()
        var path = cursor?.getString (cursor?.getColumnIndex("_data"))
        cursor?.close()

        return path
    }

    fun getFileName(uri: Uri?):String?{
        if(uri==null){
            return ""
        }
        val cursor: Cursor? = context.getContentResolver().query(uri, null, null, null, null)
        cursor?.moveToNext()
        var path = cursor?.getString (cursor?.getColumnIndex("_data"))
        cursor?.close()
        val filePath = path?.split("/")

        return filePath?.get(filePath.size-1)
    }

    fun getFileType(uri: Uri?):String? {
        val typeList = getPathFromUri(uri)?.split(".")
        return typeList?.get(typeList.size-1)
    }

    fun getFileImage(type:String?) : Int{

        if(type==null){
            return R.drawable.ic_file_others
        }

        return when(type.toLowerCase()) {
            "doc" -> R.drawable.ic_file_docx
            "docx" -> R.drawable.ic_file_docx
            "pdf" -> R.drawable.ic_file_pdf
            "ppt" -> R.drawable.ic_file_pptx
            "pptx" -> R.drawable.ic_file_pptx
            else -> R.drawable.ic_file_others
        }
    }
}
