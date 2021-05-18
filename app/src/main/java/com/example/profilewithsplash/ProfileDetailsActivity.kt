package com.example.profilewithsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.core.text.isDigitsOnly
import com.example.profilewithsplash.databinding.ActivityProfileDetailsBinding
import java.util.*

class ProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsBinding
    private lateinit var name: String
    private lateinit var surname: String
    private lateinit var email: String
    private lateinit var year: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ProfileWithSplash)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        name = intent.extras?.getString("name").toString()
        surname = intent.extras?.getString("surname").toString()
        email = intent.extras?.getString("email").toString().toLowerCase(Locale.ROOT)
        year = intent.extras?.getString("year").toString()
        gender = intent.extras?.getString("gender").toString()

        binding.editTextName.setText(name)
        binding.editTextSurname.setText(surname)
        binding.editTextEmail.setText(email)
        binding.editTextYear.setText(year)

        when {
            gender.equals(binding.radioMale.text.toString(), ignoreCase = true) ->
                binding.radioGender.check(R.id.radioMale)
            gender.equals(binding.radioFemale.text.toString(), ignoreCase = true) ->
                binding.radioGender.check(R.id.radioFemale)
        }

        binding.buttonSave.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        if (validateInput()) {
            when {
                binding.radioMale.isChecked -> gender = binding.radioMale.text.toString()
                        .toLowerCase(Locale.ROOT)
                binding.radioFemale.isChecked -> gender = binding.radioFemale.text.toString()
                        .toLowerCase(Locale.ROOT)
            }

            val intent = Intent()
            intent.putExtra("name", name)
            intent.putExtra("surname", surname)
            intent.putExtra("email", email)
            intent.putExtra("year", year)
            intent.putExtra("gender", gender)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun validateInput(): Boolean {
        var res = true
        name = binding.editTextName.text.toString()
        surname = binding.editTextSurname.text.toString()
        email = binding.editTextEmail.text.toString()
        year = binding.editTextYear.text.toString()

        when {
            name.isEmpty() -> {
                binding.editTextName.error = resources.getString(R.string.error_empty); res = false
            }
            name.all { !it.isLetter() } -> {
                binding.editTextName.error = resources.getString(R.string.error_letters); res = false
            }
            surname.isEmpty() -> {
                binding.editTextSurname.error = resources.getString(R.string.error_empty); res = false
            }
            surname.all { !it.isLetter() } -> {
                binding.editTextSurname.error = resources.getString(R.string.error_letters); res = false
            }
            email.isEmpty() -> {
                binding.editTextEmail.error = resources.getString(R.string.error_empty); res = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.editTextEmail.error = resources.getString(R.string.error_email); res = false
            }
            year.isEmpty() -> {
                binding.editTextYear.error = resources.getString(R.string.error_empty); res = false
            }
            !year.isDigitsOnly() || year.toIntOrNull()!! < 1900 || year.toIntOrNull()!! > 2000 -> {
                binding.editTextYear.error = resources.getString(R.string.error_year); res = false
            }
        }
        return res
    }
}