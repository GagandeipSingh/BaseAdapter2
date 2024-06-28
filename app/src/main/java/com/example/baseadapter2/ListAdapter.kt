package com.example.baseadapter2

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.example.baseadapter2.databinding.UpdateviewBinding

class ListAdapter(private var context : Context, private var list:ArrayList<Student>) : BaseAdapter() {
    private lateinit var customBinding: UpdateviewBinding
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        if(view == null) view = LayoutInflater.from(parent?.context).inflate(R.layout.customview,parent,false)
        view?.findViewById<TextView>(R.id.Name)?.text = list[position].name
        view?.findViewById<TextView>(R.id.rollNumber)?.text = list[position].rollNo
        view?.findViewById<TextView>(R.id.Subject)?.text = list[position].subject
        val delBtn = view?.findViewById<Button>(R.id.delete)
        val updateBtn = view?.findViewById<Button>(R.id.update)

        delBtn?.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Confirmation!")
            alertDialog.setMessage("Do you want to delete this..")
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("Yes"){_,_->
                list.removeAt(position)
                notifyDataSetChanged()
            }
            alertDialog.setNegativeButton("No"){_,_->
            }
            alertDialog.show()
        }

        updateBtn?.setOnClickListener {
            val layoutInflater = LayoutInflater.from(context)
            customBinding = UpdateviewBinding.inflate(layoutInflater)
            val customUpdate = Dialog(context)
            customUpdate.setContentView(customBinding.root)
            customUpdate.show()
            customBinding.etName.setText(list[position].name)
            customBinding.etRollNo.setText(list[position].rollNo)
            customBinding.etSubject.setText(list[position].subject)

            customBinding.positiveButton.setOnClickListener {
                if (customBinding.etName.text.trim().isEmpty()) {
                    customBinding.textInputLayout1.error = "Enter Name.."
                } else if (customBinding.etRollNo.text.trim().isEmpty()) {
                    customBinding.textInputLayout2.error = "Enter Roll No."
                } else if (customBinding.etSubject.text.trim().isEmpty()) {
                    customBinding.textInputLayout3.error = "Enter Subject.."
                } else {
                    list[position] = Student(
                        customBinding.etName.text.toString().trim(),
                        customBinding.etRollNo.text.toString().trim(),
                        customBinding.etSubject.text.toString().trim()
                    )
                    notifyDataSetChanged()
                    customUpdate.dismiss()
                }
            }
            customBinding.etName.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout1.isErrorEnabled = false
            }
            customBinding.etRollNo.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout2.isErrorEnabled = false
            }
            customBinding.etSubject.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout3.isErrorEnabled = false
            }
        }
        return view!!
    }
}