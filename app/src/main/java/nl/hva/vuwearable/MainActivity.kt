package nl.hva.vuwearable

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scichart.charting.visuals.SciChartSurface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.hva.vuwearable.databinding.ActivityMainBinding
import nl.hva.vuwearable.udp.UDPConnection
import nl.hva.vuwearable.ui.chart.scichart.ChartViewModel
import nl.hva.vuwearable.ui.login.LoginViewModel
import nl.hva.vuwearable.ui.udp.UDPViewModel
import nl.hva.vuwearable.workmanager.BackgroundWorker
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Bunyamin Duduk
 * @author Lorenzo Bindemann
 * @author Callum Svadkovski
 * @author Hugo Zuidema
 * @author Casey Kruijer
 * @author Kaan Uğur
 * @author Redouan bouziza
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // Login viewmodel is public to use it also in other classes to check the login status
    val loginViewModel: LoginViewModel by viewModels()
    private val chartViewModel: ChartViewModel by viewModels()
    private val udpViewModel: UDPViewModel by viewModels()
    private lateinit var navController: NavController

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
        private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(BackgroundWorker::class.java, 15, TimeUnit.MINUTES)
                .build()

        setupAppBar()
        checkLocationPermission()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "Background notifications",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )

        navView.setupWithNavController(navController)

        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setIcon(R.drawable.topappbarlogo);

        // Android does not allow to use a UDP socket on the main thread,
        // so we need to use it on a different thread
        Thread(
            UDPConnection(
                this.applicationContext,
                3,
                3,
                setConnectedCallback = { isConnected, isReceivingData ->
                    // Update the view model on the main thread
                    CoroutineScope(Dispatchers.Main).launch {
                        udpViewModel.setIsReceivingData(isReceivingData)
                        udpViewModel.setIsConnected(isConnected)
                    }
                },
                setASectionMeasurement = {
                    CoroutineScope(Dispatchers.Main).launch {
                        chartViewModel.setASectionMeasurement(TreeMap(it))
                    }
                })
        ).start()

        try {
            SciChartSurface.setRuntimeLicenseKey(BuildConfig.SCI_CHART_KEY)
        } catch (e: Exception) {
            Log.e("SciChart", e.toString())
        }

        initializeBottomNavbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_button -> showDialog()
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAppBar() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // This list is made to not show any back button
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_chart,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * This function is made to show a login or logout dialog
     * @author Lorenzo Bindemann
     */
    private fun showDialog() {
        if (loginViewModel.isLoggedIn.value == false) {
            // create login pop up
            val dialogLayout = layoutInflater.inflate(R.layout.login_dialog, null)
            val builder = AlertDialog.Builder(this).setView(dialogLayout).show()

            // set login function on button click
            dialogLayout.findViewById<Button>(R.id.login_button).setOnClickListener {
                val inputCode =
                    dialogLayout.findViewById<EditText>(
                        R.id.input_password
                    ).text.toString()
                loginViewModel.checkInput(inputCode, this@MainActivity)
                // check if login is successfully
                if (loginViewModel.isLoggedIn.value == true) {
                    builder.hide()
                } else {
                    // login is unsuccessfully
                    builder.findViewById<EditText>(R.id.input_password).setTextColor(Color.RED)
                    builder.findViewById<TextView>(R.id.wrong_password).visibility = View.VISIBLE
                }
            }
        } else {
            // create logout pop up
            val dialogLayout = layoutInflater.inflate(R.layout.logout_dialog, null)
            val builder = android.app.AlertDialog.Builder(this).setView(dialogLayout).show()

            // set logout function on button click
            dialogLayout.findViewById<Button>(R.id.logout_button).setOnClickListener {
                builder.hide()
                loginViewModel.setIsLoggedIn(false)
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_dashboard)
                Toast.makeText(this, getString(R.string.logout_successful), Toast.LENGTH_LONG)
                    .show()
            }
            // set cancel function on button click
            dialogLayout.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                builder.hide()
            }
        }
        setupAppBar()
    }

    /**
     * Function which checks if the app has access to the user's location to determine the network name.
     * If it does not have access, it will show an explanation as to why it needs access
     * and then prompt the user with the permission prompt.
     * @author Hugo Zuidema
     */
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Show an explanation to the user. After the user
            // sees the explanation, request the permission.
            AlertDialog.Builder(this)
                .setTitle(R.string.location_prompt_title)
                .setMessage(R.string.location_prompt_explanation)
                .setPositiveButton(
                    R.string.dialog_ok_button
                ) { _, _ ->
                    // Prompt the user once explanation has been shown
                    requestLocationPermission()
                }
                .create()
                .show()
        }
    }

    /**
     * Function which opens the Android location permission prompt
     * @author Hugo Zuidema
     */
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    /**
     * Listener which listens to the result of the permission prompt.
     * If the permission was granted it shows a short message to the user. If it was denied it alerts
     * the user that some functionalities might not work and allows the user to either go to the settings
     * of the app or proceed.
     * @author Hugo Zuidema
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this, R.string.location_accepted, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // permission denied
                    AlertDialog.Builder(this)
                        .setTitle(R.string.location_denied)
                        .setMessage(R.string.location_denied_explanation)
                        .setPositiveButton(
                            R.string.location_denied_change_settings_btn
                        ) { _, _ ->
                            // Check if we are in a state where the user has denied the permission and
                            // selected: Don't ask again
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                            ) {
                                // open the settings of the application
                                startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", this.packageName, null),
                                    ),
                                )
                            }
                        }
                        .setNegativeButton(R.string.location_denied_proceed_btn) {_, _  -> }
                        .create()
                        .show()
                }
                return
            }
        }
    }

    /**
     * Initialize a click listener on the bottom navigation items in order to navigate correctly
     */
    private fun initializeBottomNavbar() {
        // Find the bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.nav_view)

        // Set a click listener on the selected item
        bottomNav.setOnItemSelectedListener {
            // Based on the item in the bottom navigation, navigate to it
            when (it.itemId) {
                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.navigation_dashboard)
                    true
                }
                R.id.navigation_chart -> {
                    navController.navigate(R.id.navigation_chart)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
}