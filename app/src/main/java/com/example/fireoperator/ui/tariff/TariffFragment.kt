package com.example.fireoperator.ui.tariff

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fireoperator.MyVariables
import com.example.fireoperator.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tariff.*
import kotlinx.android.synthetic.main.nav_header_main.*


class TariffFragment : Fragment(), TariffAdapter.Listener {

    private val adapter = TariffAdapter(this)
    private val key = "tariffs"
    private val database = FirebaseDatabase.getInstance().getReference(key)
    val listData: ArrayList<Tariff> = ArrayList()
    private lateinit var user: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_tariff, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = MyVariables.getUser()

        init()
    }

    private fun init() {
        trc.layoutManager = LinearLayoutManager(requireContext())
        trc.adapter = adapter
        listData.clear()
        readData()
        tBtn.setOnClickListener {
            readData()
        }
    }

    private fun readData() {

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (s in snapshot.children) {
                        val tariff = s.getValue(Tariff::class.java)
                        listData.add(tariff!!)
                        Log.d("FireRead",
                            "name: ${tariff.name}, description: ${tariff.description}, cost: ${tariff.cost}")
                    }
                    Toast.makeText(requireContext(), "Данные загружены", Toast.LENGTH_SHORT).show()
                    addInRecyclerView()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FireRead", "Failed to read value.", error.toException())
            }

        })
    }

    private fun addInRecyclerView() {
        var i = 0
        while (i < listData.size) {
            val tariff = Tariff(listData[i].id, listData[i].name, listData[i].description, listData[i].cost)
            adapter.addTariff(tariff)
            i++
        }
    }

    fun writeTariff(id: String, name: String, description: String, cost: Double) {
        val newTariff = Tariff(id, name, description, cost)

        database.child(id).setValue(newTariff)
    }

    override fun onClick(tariff: Tariff) {
        //Toast.makeText(requireContext(), "Нажатие на: ${tariff.name}", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Смена тарифа")
        builder.setMessage("При нажатии на кнопку \"Да\" вы поменяете свой текущий тариф на выбранный, вы уверены?")
        builder.setNegativeButton("Отмена") { dialogInterface, i ->
//            Toast.makeText(requireContext(), "Отмена", Toast.LENGTH_SHORT).show()

        }
        builder.setPositiveButton("Да") { dialogInterface, i ->
//            Toast.makeText(requireContext(), "Да", Toast.LENGTH_SHORT).show()
            changeTariff(tariff)
        }
        builder.show()
    }

    private fun changeTariff(tariff: Tariff) {

        val newTariff: MutableMap<String, Any> = HashMap()
        newTariff["tariff"] = tariff.id.toString()

        val changeDatabase = FirebaseDatabase.getInstance().getReference("users").child(user)

        changeDatabase.updateChildren(newTariff
        ) { databaseError: DatabaseError?, databaseReference: DatabaseReference? ->
            if (databaseError == null) {
                Toast.makeText(requireContext(), "Успешная смена тарифа", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Ошибка смены тарифа", Toast.LENGTH_SHORT).show()
            }
        }
    }
}