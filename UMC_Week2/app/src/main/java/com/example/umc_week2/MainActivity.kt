package com.example.umc_week2
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.umc_week2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기본 프래그먼트 설정
        if (savedInstanceState == null) {
            replaceFragment(hikingFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.hiking -> {
                    replaceFragment(hikingFragment())
                    true
                }
                R.id.gym -> {
                    replaceFragment(GymFragment())
                    true
                }
                R.id.running -> {
                    replaceFragment(runningFragment())
                    true
                }
                R.id.yoga -> {
                    replaceFragment(yogaFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        // 애니메이션 설정
        transaction.setCustomAnimations(
            R.anim.slide,
            R.anim.slide_out
        )

        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
