package com.task

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ItemsAdapter.onItemClick {
    var binding:ActivityMainBinding?=null
    var list:ArrayList<CustomModel>?=null
    var totalPrice:Double?=0.0
    var totalQuantity:Int?=0
    var adapter:ItemsAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.activity=this
        init()
    }

    private  fun init()
    {
        list= ArrayList()
        list?.add(CustomModel(1," Product A",22.0,1))
        list?.add(CustomModel(2," Product B",39.5,1))
        list?.add(CustomModel(3," Product C",60.0,1))
        list?.add(CustomModel(4," Product D",90.0,1))
        list?.add(CustomModel(5," Product E",75.0,1))
        setData()


        adapter=ItemsAdapter(this, list!!)
        adapter?.setOnClickListener(this)
        binding!!.rvItems.adapter=adapter

    }

    override fun onItemClickListener(pos: Int, data: CustomModel, i: Int) {
        when(i)
        {
            1->
            {
                showDialog(pos, data)

            }
            2->
            {
                val quantity=data.quantity
                val newquantity=quantity.plus(1)
                val newprice=data.price*newquantity
                list!![pos].quantity=newquantity
                list!![pos].price=newprice

                adapter?.notifyItemChanged(pos)
                setData()

            }
            3->
            {

                val quantity=data.quantity
                if(quantity>1)
                {
                    val newquantity=quantity.minus(1)
                    val newprice=data.price*newquantity

                    list!![pos].quantity=newquantity
                    list!![pos].price=newprice

                    adapter?.notifyItemChanged(pos)
                }
                else
                {
                    list?.removeAt(pos)
                    adapter?.notifyDataSetChanged()


                }
                setData()

            }

        }
    }

    private  fun showDialog(pos: Int, data: CustomModel)
    {
        val dialog = Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        dialog.setContentView(R.layout.dialog_edit_price);


        val etPrice = dialog.findViewById<TextView>(R.id.etPrice)
        val btSave=dialog.findViewById<Button>(R.id.btSave)
        etPrice.text= data.price.toString()

        btSave.setOnClickListener(View.OnClickListener {
            if(!TextUtils.isEmpty(etPrice.text.toString().trim()))
            {

                val price=etPrice.text.toString()
                list!![pos].price=price.toDouble()
                adapter!!.notifyItemChanged(pos)
                dialog.dismiss()
                setData()




            }
            else
            {
                Toast.makeText(this, "Please enter price", Toast.LENGTH_SHORT).show()

            }

        })

        dialog.show()

    }


    private  fun setData()
    {
        totalPrice=0.0
        totalQuantity=0
        for( i in list!!.indices)
        {
            totalPrice=totalPrice?.plus(list!![i].price)
            totalQuantity=totalQuantity?.plus(list!![i].quantity)

        }
        binding!!.tvTotalPrice.text=totalPrice.toString()
        binding!!.tvTotalItems.text=totalQuantity.toString()


    }
}