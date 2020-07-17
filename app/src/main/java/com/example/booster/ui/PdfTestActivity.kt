package com.example.booster.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.booster.R
import com.example.booster.onlyOneClickListener
import kotlinx.android.synthetic.main.activity_pdf_text.*
import java.io.File
import java.io.IOException

class PdfTestActivity : AppCompatActivity() {

    private val PICK_PDF_FILE = 2
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_text)

        verifyStoragePermissions(this)
        act_main_btn_pdf.onlyOneClickListener {
            openFile()
        }
    }

    // get file permission
    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so Prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/*"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = resultData!!.data
            val filePath = getRealPathFromURIAPI19(this, imageUri!!)
            Log.e("filePath -> ", filePath)
            val file = File(filePath!!)
            openPDF(file)
        }
    }

//    fun getFileName(uri: Uri): String? {
//        var result: String? = null
//        if (uri.scheme == "content") {
//            val cursor =
//                contentResolver.query(uri, null, null, null, null)
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                }
//            } finally {
//                cursor?.close()
//            }
//        }
//        if (result == null) {
//            result = uri.lastPathSegment
//        }
//        return result
//    }

    @Throws(IOException::class)
    private fun openPDF(file : File) {
        val fileDescriptor: ParcelFileDescriptor?
        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

        //min. API Level 21
        val pdfRenderer: PdfRenderer?
        pdfRenderer = PdfRenderer(fileDescriptor)
        val pageCount: Int = pdfRenderer.pageCount
        act_main_total_page.text = pageCount.toString()
        Toast.makeText(this, "pageCount = $pageCount", Toast.LENGTH_LONG).show()

        for(i in 0 until pageCount-1){
            act_main_cur_page.text = (i+1).toString()
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) // value is in pixels
            val rendererPage = pdfRenderer.openPage(i)
            val rendererPageWidth: Int = rendererPage.width
            val rendererPageHeight: Int = rendererPage.height
            val bitmap = Bitmap.createBitmap(rendererPageWidth, rendererPageHeight, Bitmap.Config.ARGB_8888)
            rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            imageView.setImageBitmap(bitmap)
            act_main_ll.addView(imageView)
            rendererPage!!.close()
        }
        pdfRenderer.close()
        fileDescriptor.close()
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author Niks
     */
    @SuppressLint("NewApi")
    fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                var cursor: Cursor? = null
                try {
                    cursor = context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)
                    cursor!!.moveToNext()
                    val fileName = cursor.getString(0)
                    val path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    if (!TextUtils.isEmpty(path)) {
                        return path
                    }
                } finally {
                    cursor?.close()
                }
                val id = DocumentsContract.getDocumentId(uri)
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:".toRegex(), "")
                }
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), java.lang.Long.valueOf(id))

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }
        // File
        // MediaStore (and general)
        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author Niks
     */
    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

}
