package exmple.com.leafblightdetection

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.MediaStore
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.infideap.drawerbehavior.AdvanceDrawerLayout
import kotlinx.android.synthetic.main.app_bar_main3.*
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mCategorization: Categorization
    private lateinit var mBitmap: Bitmap
    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2


    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tflite"
    private val mLabelPath = "plant_labels.txt"

    /*

    package exmple.com.leafblightdetection

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class Categorization(assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int) {
    private val GVN_INP_SZ: Int = inputSize
    private val PHOTO_SDEVIATE = 255.0f
    private val GREAT_OUTCOME_MXX = 3
    private var PITNR: Interpreter
    private var ROW_LINE: List<String>
    private val IMAGE_PXL_SZ: Int = 3
    private val PHOTO_MEN = 0
    private val POINT_THRHLDD = 0.4f

    /*


    final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mIvPosition
            .getLayoutParams();
        int start = (int) (MARGIN
            + (leftProgress/*mVideoView.getCurrentPosition()*/ - scrollPos) * averagePxMs);
        int end = (int) (MARGIN + (rightProgress - scrollPos) * averagePxMs);
        animator = ValueAnimator
            .ofInt(start, end)
            .setDuration(
                (rightProgress - scrollPos) - (leftProgress/*mVideoView.getCurrentPosition()*/
                    - scrollPos));
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.leftMargin = (int) animation.getAnimatedValue();
                mIvPosition.setLayoutParams(params);



     */


    data class Categorizationn(
        var id: String = "",
        var title: String = "",
        var confidence: Float = 0F
    )  {
        override fun toString(): String {
            return "Title = $title, Confidence = $confidence)"
        }
    }

    init {
        PITNR = Interpreter(loadModelFile(assetManager, modelPath))
        ROW_LINE = loadLabelList(assetManager, labelPath)
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }

    }

    fun recognizeImage(bitmap: Bitmap): List<Categorization.Categorizationn> {
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, GVN_INP_SZ, GVN_INP_SZ, false)
        val byteBuffer = convertBitmapToByteBuffer(scaledBitmap)
        val result = Array(1) { FloatArray(ROW_LINE.size) }
        PITNR.run(byteBuffer, result)
        return getSortedResult(result)
    }



    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * GVN_INP_SZ * GVN_INP_SZ * IMAGE_PXL_SZ)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(GVN_INP_SZ * GVN_INP_SZ)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until GVN_INP_SZ) {
            for (j in 0 until GVN_INP_SZ) {
                val `val` = intValues[pixel++]

                byteBuffer.putFloat((((`val`.shr(16)  and 0xFF) - PHOTO_MEN) / PHOTO_SDEVIATE))
                byteBuffer.putFloat((((`val`.shr(8) and 0xFF) - PHOTO_MEN) / PHOTO_SDEVIATE))
                byteBuffer.putFloat((((`val` and 0xFF) - PHOTO_MEN) / PHOTO_SDEVIATE))
            }
        }
        return byteBuffer
    }


    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Categorization.Categorizationn> {
        Log.d("Classifier", "List Size:(%d, %d, %d)".format(labelProbArray.size,labelProbArray[0].size,ROW_LINE.size))

        val pq = PriorityQueue(
            GREAT_OUTCOME_MXX,
            Comparator<Categorization.Categorizationn> {
                    (_, _, confidence1), (_, _, confidence2)
                -> java.lang.Float.compare(confidence1, confidence2) * -1
            })

        for (i in ROW_LINE.indices) {
            val confidence = labelProbArray[0][i]
            if (confidence >= POINT_THRHLDD) {
                pq.add(Categorization.Categorizationn("" + i,
                    if (ROW_LINE.size > i) ROW_LINE[i] else "Unknown", confidence)
                )
            }
        }
        Log.d("Classifier", "pqsize:(%d)".format(pq.size))

        val recognitions = ArrayList<Categorization.Categorizationn>()
        val recognitionsSize = Math.min(pq.size, GREAT_OUTCOME_MXX)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }
        return recognitions
    }

}



     */



    private val mSamplePath = "automn.jpg"
    lateinit var toolbar: Toolbar
    lateinit var drawer: AdvanceDrawerLayout
    lateinit var navigationView: NavigationView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView =
            findViewById<View>(R.id.nav_view) as NavigationView
       toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

       drawer =
            findViewById<View>(R.id.drawer_layout) as AdvanceDrawerLayout
        /*val toggle =
            ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        */
        //drawer.setViewRotation(GravityCompat.START, 15); // set degree of Y-rotation ( value : 0 -> 45)
        //drawer.setViewRotation(GravityCompat.START, 15); // set degree of Y-rotation ( value : 0 -> 45)
        drawer.setViewScale(GravityCompat.START, 0.9f) //set height scale for main view (0f to 1f)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, 0, 0
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        drawer.setViewElevation(
            GravityCompat.START,
            20f
        ) //set main view elevation when drawer open (dimension)

        drawer.setViewScrimColor(
            GravityCompat.START,
            Color.TRANSPARENT
        ) //set drawer overlay coloe (color)

        drawer.setDrawerElevation(GravityCompat.START, 20f) //set drawer elevation (dimension)

        drawer.setContrastThreshold(3f) //set maximum of contrast ratio between white text and background color.

        drawer.setRadius(GravityCompat.START, 25f) //set end container's corner radius (dimension)

        drawer.useCustomBehavior(GravityCompat.START) //assign custom behavior for "Left" drawer

        drawer.useCustomBehavior(GravityCompat.END) //assign custom behavior for "Right" drawer

        //Set Name for user
        //Set Name for user
        val headerView = navigationView.getHeaderView(0)




        mCategorization = Categorization(assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            mPhotoImageView.setImageBitmap(mBitmap)
        }
        bottom_nav.setActiveItem(0);
        val bottomnav: SmoothBottomBar= findViewById<SmoothBottomBar>(R.id.bottom_nav)
        bottomnav.setOnItemSelectedListener(object: OnItemSelectedListener {
            override fun onItemSelect(pos: Int): Boolean {
                    when(pos){
                        1 -> {
                            val intent = Intent(this@MainActivity,Common_Remdies::class.java)
                            startActivity(intent)
                            return true
                        }
                        2 -> {
                            val intent = Intent(this@MainActivity,About_us::class.java)
                            startActivity(intent)
                            return true
                        }
                    }

             return false
            }
        })
        //private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            //when (item.itemId) {
                //R.id.navigation_songs -> {


        //      val intent = Intent(this@MainActivity,Common_Remdies::class.java)
                    //        startActivity(intent)




        mCameraButton.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }

        mGalleryButton.setOnClickListener {
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
        }
        mDetectButton.setOnClickListener {
                val results = mCategorization.recognizeImage(mBitmap).firstOrNull()
                mResultTextView.text= results?.title+"\n Confidence:"+results?.confidence

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == mCameraRequestCode){
            //Considérons le cas de la caméra annulée
            if(resultCode == Activity.RESULT_OK && data != null) {
                mBitmap = data.extras!!.get("data") as Bitmap
                mBitmap = scaleImage(mBitmap)
                val toast = Toast.makeText(this, ("Image crop to: w= ${mBitmap.width} h= ${mBitmap.height}"), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()
                mPhotoImageView.setImageBitmap(mBitmap)
                mResultTextView.text= "Your photo image set now."
            } else {
                Toast.makeText(this, "Camera cancel..", Toast.LENGTH_LONG).show()
            }
        } else if(requestCode == mGalleryRequestCode) {
            if (data != null) {
                val uri = data.data

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                println("Success!!!")
                mBitmap = scaleImage(mBitmap)
                mPhotoImageView.setImageBitmap(mBitmap)

            }
        } else {
            Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_LONG).show()

        }
    }


    fun scaleImage(bitmap: Bitmap?): Bitmap {
        val orignalWidth = bitmap!!.width
        val originalHeight = bitmap.height
        val scaleWidth = mInputSize.toFloat() / orignalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, orignalWidth, originalHeight, matrix, true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
val drawer =
            findViewById<View>(R.id.drawer_layout) as AdvanceDrawerLayout
        when (item.itemId) {

            R.id.about -> {

                val intent = Intent(this@MainActivity,About_us::class.java)
                startActivity(intent)
            }
            R.id.remdy -> {

                val intent = Intent(this@MainActivity,Common_Remdies::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true

    }
    override fun onBackPressed() {
        val alertDialogBuilder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit Application?")
        alertDialogBuilder
            .setMessage("Click yes to exit!")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    moveTaskToBack(true)
                    Process.killProcess(Process.myPid())
                    System.exit(1)
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alertDialog: android.app.AlertDialog? = alertDialogBuilder.create()
        alertDialog?.show()
    }

}

