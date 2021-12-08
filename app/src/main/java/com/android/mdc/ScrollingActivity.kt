package com.android.mdc

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.mdc.databinding.ActivityScrollingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            if(findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }*/

        binding.fab.setOnClickListener{
            if(binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .show()
        }

        binding.content.btnSkip?.setOnClickListener {
            binding.content.cvAd?.visibility = View.GONE
        }

        binding.content.btnBuy?.setOnClickListener {
            Snackbar.make(it, R.string.card_buying, Snackbar.LENGTH_LONG).setAnchorView(binding.fab)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_LONG).show()
                }.show()
        }

        val url1 = "https://pruebas-oilcreto.000webhostapp.com/postman/images/img1.jpg"

        cargarImagen(url1)

        Glide.with(this)
            .load("https://pruebas-oilcreto.000webhostapp.com/postman/images/img2.jpg")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .into(binding.content.imgCover2!!)

        binding.content.cbEnablePass?.setOnClickListener {
            binding.content.tilPassword?.isEnabled = !binding.content.tilPassword?.isEnabled!!
        }

        binding.content.etURL?.onFocusChangeListener = View.OnFocusChangeListener { v, focused ->

            var errorStr:String? = null
            val url = binding.content.etURL?.text.toString()

            if(!focused){
                if (url.isEmpty()){
                     errorStr = getString(R.string.card_required)
                }else if(URLUtil.isValidUrl(url)){
                    cargarImagen(url)
                }else{
                    errorStr = getString(R.string.card_invalid_url)
                }
            }

            binding.content.tilURL?.error = errorStr
        }

        binding.content.toggleButton?.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.content.root.setBackgroundColor(
                if(isChecked){
                    when(checkedId){
                        R.id.btnRed -> Color.RED
                        R.id.btnBlue -> Color.BLUE
                        R.id.btnGreen -> Color.GREEN
                        else -> Color.BLACK
                    }
                }else{
                    Color.WHITE
                }

            )
        }
    }

    private fun cargarImagen(url:String){
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .into(binding.content.imgCover!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}