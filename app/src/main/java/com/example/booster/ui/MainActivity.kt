package com.example.booster.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.booster.*
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.fileStorage.StoreFileOptionActivity
import com.example.booster.ui.orderList.OrderListActivity
import com.example.booster.ui.payment.PaymentActivity
import com.example.booster.ui.selectStore.SelectStoreActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_store_file_option.*

class MainActivity : AppCompatActivity() {

    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                this@MainActivity,
                "Permission Denied\n$deniedPermissions",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()

        act_main_btn_order_condition.onlyOneClickListener {
            val intent = Intent(this@MainActivity, OrderListActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_tap.onlyOneClickListener {
            val intent = Intent(this@MainActivity, BottomTabActivity::class.java)
            startActivity(intent)
        }

        btn_storage.onlyOneClickListener {
            //val intent = Intent(this, FileStorageActivity::class.java)
            val intent = Intent(this, SelectStoreActivity::class.java)
            startActivity(intent)
        }

        pdf_test.onlyOneClickListener {
            val intent = Intent(this, PdfTestActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_pay.onlyOneClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_login.onlyOneClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_lottie.setOnClickListener {
            val intent = Intent(this, LottieActivity::class.java)
            startActivity(intent)
        }
    }

}



