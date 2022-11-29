package com.tdi.holyquran.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.tazkiyatech.quran.sdk.database.HifdhTips
import com.tazkiyatech.quran.sdk.database.QuranDatabase
import com.tazkiyatech.quran.sdk.database.QuranQuotes
import com.tdi.holyquran.R
import com.tdi.holyquran.data.Prefs
import com.tdi.holyquran.data.adapter.PrayerTimeRecyclerAdapter
import com.tdi.holyquran.data.model.MuslimSalatDailyResponse
import com.tdi.holyquran.data.model.ScheduleOfPray
import com.tdi.holyquran.data.model.Status
import com.tdi.holyquran.data.util.changeNavigation
import com.tdi.holyquran.data.util.getCurrentDateNormalFormat
import com.tdi.holyquran.data.util.getCurrentDayFormat
import com.tdi.holyquran.databinding.FragmentHomeBinding
import com.tdi.holyquran.di.AzanReceiver
import com.tdi.holyquran.ui.base.BaseFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import com.pixplicity.easyprefs.library.Prefs as prefs


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    val tAg = "HomeFragment"

    private lateinit var city: String

    private lateinit var toolbar: Toolbar
    private lateinit var tvTime: TextView
    private lateinit var tvAgo: TextView
    private lateinit var tvPraySeeAll: TextView
    //private var azanManager: MediaPlayer? = null


    override var getLayoutId: Int = R.layout.fragment_home
    override var getViewModel: Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = mViewBinding.toolbar
        tvTime = mViewBinding.tvHomeSalatTime
        tvAgo = mViewBinding.tvHomeSalatAgo
        tvPraySeeAll = mViewBinding.tvHomePrayerSeeAll
        prefs.Builder()
            .setContext(requireContext())
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(requireActivity().packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        requestNotificationPerm()
        requestLocation()
        getCurrentLocation()
        getCurrentDate()
        showRandTips()


        //azanManager = getSystemService(AppCompatActivity.ALARM_SERVICE) as MediaPlayer


        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
//                fragmentManager?.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                requireActivity().finishAffinity()
//                exitProcess(0)
                return@OnKeyListener true
            }
            false
        })

        tvPraySeeAll.setOnClickListener {
            onPrayerSeeAllClickListener(it)
        }

        mViewBinding.tvHomeBtnQuran.setOnClickListener {
            onQuranClickListener(it)
        }

        mViewBinding.srlHome.setOnRefreshListener {
            getCurrentLocation()
        }

        tv_qibla_btn.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToQiblaFragment2()
                it.changeNavigation(action)
        }
    }

    private fun requestNotificationPerm() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    this@HomeFragment.requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@HomeFragment.requireActivity(),
                    arrayOf(
                        Manifest.permission.SCHEDULE_EXACT_ALARM,
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    0
                )
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationRequest: LocationRequest.Builder = LocationRequest.Builder(5000L)

        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        locationRequest.setIntervalMillis(6000L)
        locationRequest.setMinUpdateIntervalMillis(5000L)


        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {}
        }
        checkPerm()
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(locationRequest.build(),locationCallback,null)
    }

    private fun onQuranClickListener(v: View) {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationQuran()
        v.changeNavigation(action)
    }

    private fun onPrayerSeeAllClickListener(v: View) {
        val action = HomeFragmentDirections.actionNavigationHomeToSchedulePrayerFragment(city)
        action.city = city
        v.changeNavigation(action)
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentDate() {
        mViewBinding.tvHomeDay.text = getCurrentDayFormat
        mViewBinding.tvHomeDate.text = getCurrentDateNormalFormat
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (isLocationEnabled()) {
            LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation
                .addOnSuccessListener {
                    it?.let { location ->
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        city = addresses?.get(0)?.adminArea.toString()
                        toolbar.title = city
                        subscribeSchedule(city)
                        Prefs.userCity = city

//                        Toast.makeText(activity, city, Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }
                    Toast.makeText(activity, "Location GPS not enabled", Toast.LENGTH_SHORT).show()
                }
        } else {
            requestLocationEnable()
        }
    }

    private fun subscribeSchedule(subLocality: String) {
        mViewModel.getDailySchedule(subLocality)
            .observe(viewLifecycleOwner) {
                it?.let {
                    when (it.status) {
                        Status.StatusType.ERROR -> {
                            Toast.makeText(activity, "Error : ${it.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        Status.StatusType.LOADING -> {
                            mViewBinding.srlHome.isRefreshing = true
                        }
                        Status.StatusType.SUCCESS -> {
                            mViewBinding.srlHome.isRefreshing = false
                            onScheduleSuccess(it.data!!)
                        }
                    }
                }
            }
    }

    private fun onScheduleSuccess(data: MuslimSalatDailyResponse) {
        mViewModel.getTimePray(data).observe(viewLifecycleOwner) {

            val text  = it[1].removeRange(9,13)+" Left"
            tvTime.text = it[0]
            tvAgo.text = text

//            startAzan()
//            val filter = IntentFilter("com.tdi.holyquran.UPDATE_AZAN")
//            val AzanReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//                override fun onReceive(context: Context?, intent: Intent?) {
////                    if (text == "00:00:00 Left") {
////                        val mp = MediaPlayer.create(requireContext(), R.raw.azan20)
////                        mp.start()
////                    }
//                }
//            }
            //startActivity(requireActivity().registerReceiver(AzanReceiver,filter))

        }
        showPrayerSchedule(data.scheduleOfPrays)
    }


//    private fun startAzan():PendingIntent {
//        val intent = Intent(requireContext(),AzanReceiver::class.java)
//        intent.action = AzanReceiver.ACTION
//        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT}
//        else {
//            PendingIntent.FLAG_UPDATE_CURRENT
//        }
//        startActivity(intent)
//        return PendingIntent.getBroadcast(requireContext(),0,intent,flags)
//    }

//    fun startMediaPlayer() {
//        val mp = MediaPlayer.create(context, R.raw.azan20)
//        mp.isLooping = false
//        mp.start()
//    }

    private fun showPrayerSchedule(scheduleOfPrays: List<ScheduleOfPray>) {
        mViewBinding.rvHomePrayerSchedule.layoutManager = LinearLayoutManager(activity)
        mViewBinding.rvHomePrayerSchedule.adapter = PrayerTimeRecyclerAdapter(scheduleOfPrays)
    }

    fun onPermissionSuccess() {
        getCurrentLocation()
    }

    private fun isLocationEnabled(): Boolean {
        val lm: LocationManager =
            activity?.getSystemService(DaggerAppCompatActivity.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestLocationEnable() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Location Enabled")
            .setMessage("GPS Network not enabled")
            .setPositiveButton("Ok") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun checkPerm(){
        if (ActivityCompat.checkSelfPermission(this@HomeFragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@HomeFragment.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@HomeFragment.requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),0)
        }
    }

    private fun showRandTips(){

        val quranDatabase = QuranDatabase(requireContext())
        quranDatabase.openDatabase()

        val quranQuotes = QuranQuotes(requireContext().resources)
        val quranQuote = quranQuotes.quoteOfTheDay.removeRange(0,1)

        val hifdhTips = HifdhTips(requireContext().resources)
        val hifdhTip = hifdhTips.tipOfTheDay.removeRange(0,11)

        tv_memorizetips.text = "$quranQuote\n\nMemorize Quran: $hifdhTip"
        quranDatabase.closeDatabase()

    }
}
