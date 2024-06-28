package com.example.baseadapter2

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class ListAdapter(private var list:ArrayList<Student>,private var alertDialog: AlertDialog.Builder,private var customUpdate:Dialog ) : BaseAdapter() {
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
        val etName = customUpdate.findViewById<EditText>(R.id.etName)
        val etNameL = customUpdate.findViewById<TextInputLayout>(R.id.textInputLayout1)
        val etRollN = customUpdate.findViewById<EditText>(R.id.etRollNo)
        val etRollL = customUpdate.findViewById<TextInputLayout>(R.id.textInputLayout2)
        val etSubject = customUpdate.findViewById<EditText>(R.id.etSubject)
        val etSubjectL = customUpdate.findViewById<TextInputLayout>(R.id.textInputLayout3)
        val positiveBtn =  customUpdate.findViewById<TextView>(R.id.positiveButton)
        delBtn?.setOnClickListener {
            alertDialog.setPositiveButton("Yes"){_,_->
                list.removeAt(position)
                notifyDataSetChanged()
            }
            alertDialog.setNegativeButton("No"){_,_->
            }
            alertDialog.show()
        }
        updateBtn?.setOnClickListener {
            customUpdate.show()
            etName?.setText(list[position].name)
            etRollN.setText(list[position].rollNo)
            etSubject?.setText(list[position].subject)
        }
            positiveBtn.setOnClickListener {
                if (etName.text.trim().isEmpty()) {
                    etNameL.error = "Enter Name.."
                }
                else if(etRollN.text.trim().isEmpty()){
                    etRollL.error = "Enter Roll No."
                }
                else if (etSubject?.text?.trim()?.isEmpty()!!) {
                    etSubjectL.error = "Enter Subject.."
                } else {
                    list[position] = Student(
                        etName.text.toString().trim(),
                        etRollN.text.toString().trim(),
                        etSubject.text.toString().trim()
                    )
                    notifyDataSetChanged()
                    customUpdate.dismiss()
                    etName.clearFocus()
                    etRollN.clearFocus()
                    etSubject.clearFocus()
                }
            }
                etName.doOnTextChanged { _, _, _, _ ->
                    etNameL.isErrorEnabled = false
                }
                etRollN.doOnTextChanged { _, _, _, _ ->
                    etRollL.isErrorEnabled = false
                }
                etSubject.doOnTextChanged { _, _, _, _ ->
                    etSubjectL.isErrorEnabled = false
                }

        return view!!
    }
}