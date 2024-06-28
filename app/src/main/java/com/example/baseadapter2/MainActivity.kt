package com.example.baseadapter2

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.baseadapter2.databinding.ActivityMainBinding
import com.example.baseadapter2.databinding.AdddialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var addBinding : AdddialogBinding
    private lateinit var arrayList : ArrayList<Student>
    private lateinit var listAdapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        arrayList = arrayListOf(Student("Robin","1","Maths"))
        arrayList.add(Student("Ram","2","English"))
        addBinding = AdddialogBinding.inflate(layoutInflater)
        val addDialog = Dialog(this)
        addDialog.setContentView(addBinding.root)
        binding.FAB.setOnClickListener {
            addBinding.textInputLayout1.isErrorEnabled = false
            addBinding.etName.text = null
            addBinding.etName.clearFocus()
            addBinding.textInputLayout2.isErrorEnabled = false
            addBinding.etRoll.text = null
            addBinding.etRoll.clearFocus()
            addBinding.textInputLayout3.isErrorEnabled = false
            addBinding.etSubject.text = null
            addBinding.etSubject.clearFocus()
            addBinding.positiveButton.setOnClickListener {
                if(addBinding.etName.text?.trim()?.isEmpty()!!){
                    addBinding.textInputLayout1.error = "Enter Name.."
                }
                else if(addBinding.etRoll.text?.trim()?.isEmpty()!!){
                    addBinding.textInputLayout2.error = "Enter Roll Number.."
                }
                else if(addBinding.etSubject.text?.trim()?.isEmpty()!!){
                    addBinding.textInputLayout3.error = "Enter Subject.."
                }
                else{
                    arrayList.add(Student(addBinding.etName.text?.trim().toString(),
                        addBinding.etRoll.text?.trim().toString(),addBinding.etSubject.text?.trim().toString()))
                    listAdapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity,"New Item Added..", Toast.LENGTH_SHORT).show()
                    addDialog.dismiss()
                }
                addBinding.etName.doOnTextChanged { _, _, _, _ ->
                    addBinding.textInputLayout1.isErrorEnabled = false
                }
                addBinding.etRoll.doOnTextChanged { _, _, _, _ ->
                    addBinding.textInputLayout2.isErrorEnabled = false
                }
                addBinding.etSubject.doOnTextChanged { _, _, _, _ ->
                    addBinding.textInputLayout3.isErrorEnabled = false
                }
            }
            addDialog.show()
            val window = addDialog.window
            window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        }
            val customUpdate = Dialog(this)
            customUpdate.setContentView(R.layout.updateview)
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Confirmation!")
            alertDialog.setMessage("Do you want to delete this..")
            alertDialog.setCancelable(false)

        listAdapter = ListAdapter(arrayList,alertDialog,customUpdate)
        binding.listView.adapter = listAdapter
    }
}