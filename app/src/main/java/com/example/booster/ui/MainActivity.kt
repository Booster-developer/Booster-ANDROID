package com.example.booster.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.booster.R
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.orderCondition.OrderConditionActivity
import com.example.booster.ui.storeDetail.MapActivity
import com.example.booster.ui.storeList.StoreListActivity
import com.example.booster.ui.storeDetail.StoreDetailActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check();

        act_main_btn_store.setOnClickListener {
            val intent = Intent(this@MainActivity, StoreListActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_store_detail.setOnClickListener {
            val intent = Intent(this@MainActivity, StoreDetailActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_order_condition.setOnClickListener {
            val intent = Intent(this@MainActivity, OrderConditionActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_tap.setOnClickListener {
            val intent = Intent(this@MainActivity, BottomTabActivity::class.java)
            startActivity(intent)
        }

        btn_storage.setOnClickListener {
            val intent = Intent(this, FileStorageActivity::class.java)
            startActivity(intent)
        }

    }

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
}
