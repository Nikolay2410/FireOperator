package com.example.fireoperator.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fireoperator.MyVariables
import com.example.fireoperator.R
import com.example.fireoperator.ui.tariff.Tariff
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var databaseUser: DatabaseReference
    private lateinit var databaseTariff: DatabaseReference
    private lateinit var databaseBalance: DatabaseReference
    private lateinit var user : String

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
//        Toast.makeText(requireContext(), user, Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        user = MyVariables.getUser()
        super.onViewCreated(view, savedInstanceState)

//        Toast.makeText(requireContext(), "User is $user", Toast.LENGTH_SHORT).show()

        databaseUser = FirebaseDatabase.getInstance().getReference("users").child(user).child("tariff")
        databaseBalance = FirebaseDatabase.getInstance().getReference("users").child(user).child("balance")
//        balance_txt.text = "1566666634.5"
        readTariff()

        mainBtn.setOnClickListener {
            user = MyVariables.getUser()
            databaseUser = FirebaseDatabase.getInstance().getReference("users").child(user).child("tariff")
            databaseBalance = FirebaseDatabase.getInstance().getReference("users").child(user).child("balance")
            readTariff()
        }
    }

    private fun readTariff() {

        databaseUser.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val nameTariff = snapshot.getValue<String>()
                    Log.d("ReadTariff", "Value is: $nameTariff")

                    databaseTariff = FirebaseDatabase.getInstance().getReference("tariffs").child(nameTariff.toString())
                    databaseTariff.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshotTariff: DataSnapshot) {
                            if (snapshotTariff.exists()) {

                                val myTariff = snapshotTariff.getValue<Tariff>()

                                if (myTariff != null) {
                                    if (myTariff.name != null && tariff_name != null && tariff_name.text != null) {
                                        tariff_name.text = myTariff.name
                                    }
                                    if (myTariff.description != null && tariff_description != null && tariff_description.text != null) {
                                        tariff_description.text = myTariff.description
                                    }
                                    if (myTariff.cost != null && tariff_cost != null && tariff_description.text != null) {
                                        tariff_cost.text = myTariff.cost.toString()
                                    }
                                }
//                                Toast.makeText(requireContext(), "Тариф получен", Toast.LENGTH_SHORT).show()
                                if (progressBarTariff != null) {
                                    progressBarTariff.visibility = View.GONE
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                    databaseBalance.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                if (balance_txt != null && balance_txt.text != null) {
                                    balance_txt.text = snapshot.getValue<Double>().toString()
                                }
                                Log.d("ReadTariff", "Value is: $snapshot.getValue<Double>().toString()")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FireRead", "Failed to read value.", error.toException())
            }

        })

    }
}