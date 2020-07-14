package com.example.booster.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import com.shockwave.pdfium.PdfiumCore
import java.io.IOException


object PDFThumbnailUtils {
    fun convertPDFtoBitmap(context: Context, uri: Uri, pageNumber: Int): Bitmap? {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pdfRenderer = parcelFileDescriptor?.let { PdfRenderer(it) }
            val currentPage = pdfRenderer?.openPage(pageNumber)
            val bitmap = Bitmap.createBitmap(currentPage?.width!!, currentPage?.height!!, Bitmap.Config.ARGB_8888)
            currentPage?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            // Here, we render the page onto the Bitmap.
            return bitmap
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
    }


    fun convertPDFtoBitmap(context: Context, uri: Uri): Bitmap? {
        val pdfiumCore = PdfiumCore(context)
        try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            if (parcelFileDescriptor != null) {
                val pdfDocument = pdfiumCore.newDocument(parcelFileDescriptor)
                pdfiumCore.openPage(pdfDocument, 0)
                val width = pdfiumCore.getPageWidthPoint(pdfDocument, 0)
                val height = pdfiumCore.getPageHeightPoint(pdfDocument, 0)
                // ARGB_8888 - best quality, high memory usage, higher possibility of OutOfMemoryError
                // RGB_565 - little worse quality, twice less memory usage
                val bitmap = Bitmap.createBitmap(
                    width, height,
                    Bitmap.Config.RGB_565
                )
                pdfiumCore.renderPageBitmap(pdfDocument, bitmap, 0, 0, 0, width, height)
                pdfiumCore.closeDocument(pdfDocument) // important!
                return bitmap
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }
}