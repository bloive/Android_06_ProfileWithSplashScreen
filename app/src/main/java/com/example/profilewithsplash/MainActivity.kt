package com.example.profilewithsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.profilewithsplash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE = 123
    }

    private lateinit var binding: ActivityMainBinding
    private var name = "Elene"
    private var surname = "Bokuchava"
    private var email = "elene@bokuchava.com"
    private var year =  "1993"
    private var gender = "female"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProfileWithSplash)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setText()
        binding.buttonEditProfile.setOnClickListener {
            startEditProfile()
        }
    }

    private fun setText() {
        binding.nameSurname.text = "$name $surname"
        binding.email.text = email
        binding.yearGender.text = "Born in $year, $gender"
    }

    private fun startEditProfile() {
        val intent = Intent(this, ProfileDetailsActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("surname", surname)
        intent.putExtra("email", email)
        intent.putExtra("year", year)
        intent.putExtra("gender", gender)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            name = data?.extras?.getString("name", "").toString()
            surname = data?.extras?.getString("surname", "").toString()
            email = data?.extras?.getString("email", "").toString()
            year = data?.extras?.getString("year", "").toString()
            gender = data?.extras?.getString("gender", "").toString()
            setText()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}