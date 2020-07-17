package com.example.booster.ui

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.booster.R
import com.example.booster.onlyOneClickListener
import kotlinx.android.synthetic.main.activity_pdf_viewer.*
import kotlinx.android.synthetic.main.my_file.view.*
import java.io.IOException

class PdfViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        val intent = intent

        val pdffile: String? = intent.getStringExtra("pdffile")
        Log.e("intent check", "check: " + pdffile)
        val imgfile: String? = intent.getStringExtra("imgfile")

        pdffile?.let{
            openPDF(java.io.File(pdffile))
        }.let{
            Glide.with(this).load(imgfile).into(pdfviewer_act_main_imageview)
        }


        pdfviewer_act_main_close.onlyOneClickListener {

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    @Throws(IOException::class)
    private fun openPDF(file: java.io.File) {
        val fileDescriptor: ParcelFileDescriptor?
        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

        //min. API Level 21
        val pdfRenderer: PdfRenderer?
        pdfRenderer = PdfRenderer(fileDescriptor)
        val pageCount: Int = pdfRenderer.pageCount
        Log.e(
            "pagecount",
            "check: " + pageCount.toString() + " " + pdfviewer_act_main_total_page.text
        )
        pdfviewer_act_main_total_page.text = pageCount.toString()
        Toast.makeText(this, "pageCount = $pageCount", Toast.LENGTH_LONG).show()

        val parentlayout = LinearLayout(this)
        parentlayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
        parentlayout.orientation = LinearLayout.HORIZONTAL

        if (pageCount != 1) {
            pdfviewer_act_main_hs.removeView(pdfviewer_act_main_ll)
            pdfviewer_act_main_hs.addView(parentlayout)
        }

        for (i in 0 until pageCount) {
            pdfviewer_act_main_cur_page.text = (i + 1).toString()
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) // value is in pixels
            val rendererPage = pdfRenderer.openPage(i)
            val rendererPageWidth: Int = rendererPage.width
            val rendererPageHeight: Int = rendererPage.height
            val bitmap =
                Bitmap.createBitmap(rendererPageWidth, rendererPageHeight, Bitmap.Config.ARGB_8888)
            rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            imageView.setImageBitmap(bitmap)

            if (pageCount == 1) {
                pdfviewer_act_main_ll.addView(imageView)
            }else {
                parentlayout.addView(imageView)
            }

            rendererPage!!.close()
        }

        pdfRenderer.close()
        fileDescriptor.close()
    }


}
