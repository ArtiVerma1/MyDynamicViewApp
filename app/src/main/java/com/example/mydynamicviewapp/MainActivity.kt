package com.example.mydynamicviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydynamicviewapp.databinding.ActivityMainBinding
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Retrofit
import javax.inject.Inject
import android.widget.ArrayAdapter




class MainActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var retrofit: Retrofit
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
public    var profilecon : LinearLayoutCompat?=null
    public    var profilecom_con : LinearLayoutCompat?=null
    public    var profilesup_con : LinearLayoutCompat?=null
    public    var profileseo_con : LinearLayoutCompat?=null
    public    var profileadd_con : LinearLayoutCompat?=null
    var text:TextView?=null
    var textcom:TextView?=null
    var textsup:TextView?=null
    var textseo:TextView?=null
    var textadd:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).getNetComponent()?.inject(this)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.activity_main)
        profilecon = _binding!!.container
        profilecom_con = _binding!!.containerCom
        profilesup_con = _binding!!.containerSupp
        profileseo_con = _binding!!.containerSeo
        profileadd_con = _binding!!.containerAdd
        text=_binding!!.textgneralinfo
        textcom=_binding!!.textcominfo
        textsup=_binding!!.textsupinfo
        textseo=_binding!!.textseoinfo
        textadd=_binding!!.textaddinfo
        var factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return  HomeViewModel(retrofit) as T
            }
        }
        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)
//        homeViewModel =

        homeViewModel.getUser()?.observe(this, Observer { serviceSetterGetter ->
            val dataResponse=serviceSetterGetter.toString()
            Log.d("TAG", "onResponse: "+dataResponse)
            val vandor= JSONObject(dataResponse)
            val data=vandor.getJSONObject("data")
            Log.d("data","data:"+data)
            if(data.getString("success").equals("true")){
                val vendor_attr=data.getJSONObject("vendor_attributes")
                val generalinfo=vendor_attr.getJSONArray("General Information")
                Log.i("MageNative","generalinfo :"+generalinfo)
                text?.setText("General Information")
                for (i in 0 until generalinfo.length())
                {
                    val type=generalinfo.getJSONObject(i).getString("type")

                    if (type.equals("text")){
val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.edittextvendor,null,false)
                        view.findViewById<EditText>(R.id.editTextName).setText(
                            generalinfo.getJSONObject(i).getString("saved_value") )
        view.findViewById<TextView>(R.id.textfield).setText(
            generalinfo.getJSONObject(i).getString("field_name"))
                                profilecon?.addView(view)
                    }
                    if (type.equals("image")){
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.imageviewvendor,null,false)
                 val img=       view.findViewById<ImageView>(R.id.imageView)
                        Glide.with(this@MainActivity)
                            .load(generalinfo.getJSONObject(i).getString("saved_value"))
                            .into(img)
                        view.findViewById<TextView>(R.id.textimg).setText(
                            generalinfo.getJSONObject(i).getString("field_name"))
                        profilecon?.addView(view)
                    }
                    if (type.equals("select")){
                        val option_gender=generalinfo.getJSONObject(i).getJSONArray("options")
                        Log.i("MageNative","option_gender :"+option_gender)
                        val array= ArrayList<String>()
                        for (i in 0 until option_gender.length()) {
                        array.add(option_gender.getJSONObject(i).getString("label"))
                        }
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.spinnervendor,null,false)
                        val spin=       view.findViewById<Spinner>(R.id.spinner)
                    val    adapter = ArrayAdapter<String>(
                            applicationContext,
                            android.R.layout.simple_spinner_item, array
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spin.setAdapter(adapter)
                        view.findViewById<TextView>(R.id.textselect).setText(
                            generalinfo.getJSONObject(i).getString("field_name"))
                        profilecon?.addView(view)
                    }
                }
                val cominfo=vendor_attr.getJSONArray("Company Information")
textcom?.setText("Company Information")
                Log.i("MageNative","cominfo :"+cominfo)
                for (i in 0 until cominfo.length()) {
                    val type=cominfo.getJSONObject(i).getString("type")
                    if (type.equals("text")){
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.edittextvendor,null,false)
                        view.findViewById<EditText>(R.id.editTextName).setText(
                            cominfo.getJSONObject(i).getString("saved_value") )
                        view.findViewById<TextView>(R.id.textfield).setText(
                            cominfo.getJSONObject(i).getString("field_name"))
                        profilecom_con?.addView(view)
                    }
                    if (type.equals("image")){
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.imageviewvendor,null,false)
                        val img=       view.findViewById<ImageView>(R.id.imageView)
                        Glide.with(this@MainActivity)
                            .load(cominfo.getJSONObject(i).getString("saved_value"))
                            .into(img)
                        view.findViewById<TextView>(R.id.textimg).setText(
                            cominfo.getJSONObject(i).getString("field_name"))
                        profilecom_con?.addView(view)
                    }
                    if (type.equals("textarea")){
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.edittextvendor,null,false)
                        view.findViewById<EditText>(R.id.editTextName).setText(
                            cominfo.getJSONObject(i).getString("saved_value") )
                        view.findViewById<TextView>(R.id.textfield).setText(
                            cominfo.getJSONObject(i).getString("field_name"))
                        profilecom_con?.addView(view)
                    }
                }
                val supportinfo =
                    vendor_attr.getJSONArray("Support Information")
                Log.i("MageNative", "supportinfo :" + supportinfo)
                textsup?.setText("Support Information")
                for (i in 0 until supportinfo.length()) {
                    val type = supportinfo.getJSONObject(i).getString("type")
                    if (type.equals("text")) {
                        val view = LayoutInflater.from(this@MainActivity)
                            .inflate(R.layout.edittextvendor, null, false)
                        view.findViewById<EditText>(R.id.editTextName).setText(
                            supportinfo.getJSONObject(i).getString("saved_value")
                        )
                        view.findViewById<TextView>(R.id.textfield).setText(
                            supportinfo.getJSONObject(i).getString("field_name")
                        )
                        profilesup_con?.addView(view)
                    }
                }
                val seoinfo =
                    vendor_attr.getJSONArray("SEO Information")
                Log.i("MageNative", "seoinfo :" + seoinfo)
                textseo?.setText("SEO Information")
                for (i in 0 until seoinfo.length()) {
                    val type = seoinfo.getJSONObject(i).getString("type")
                    if (type.equals("textarea")){
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.textareavendor,null,false)
                        view.findViewById<EditText>(R.id.textmul).setText(
                            seoinfo.getJSONObject(i).getString("saved_value") )
                        view.findViewById<TextView>(R.id.textfield).setText(
                            seoinfo.getJSONObject(i).getString("field_name"))
                        profileseo_con?.addView(view)
                    }
                }
                val addinfo =
                    vendor_attr.getJSONArray("Address Information")
                Log.i("MageNative", "addinfo :" + addinfo)
                textadd?.setText("Address Information")
                for (i in 0 until addinfo.length()) {
                    val type = addinfo.getJSONObject(i).getString("type")
                    if (type.equals("text")){
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.edittextvendor,null,false)
                        view.findViewById<EditText>(R.id.editTextName).setText(
                            addinfo.getJSONObject(i).getString("saved_value") )
                        view.findViewById<TextView>(R.id.textfield).setText(
                            addinfo.getJSONObject(i).getString("field_name"))
                        profileadd_con?.addView(view)
                    }
                    if (type.equals("select")){
                        val option_state=addinfo.getJSONObject(i).getJSONArray("options")
                        Log.i("MageNative","option_state :"+option_state)
                        val array= ArrayList<String>()
                        for (i in 0 until option_state.length()) {
                            array.add(option_state.getJSONObject(i).getString("label"))
                        }
                        val view=LayoutInflater.from(this@MainActivity).inflate(R.layout.spinnervendor,null,false)
                        val spin=       view.findViewById<Spinner>(R.id.spinner)
                        val    adapter = ArrayAdapter<String>(
                            applicationContext,
                            android.R.layout.simple_spinner_item, array
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spin.setAdapter(adapter)
                            //spin.setSelection(array[1].toInt())
                        view.findViewById<TextView>(R.id.textselect).setText(
                            addinfo.getJSONObject(i).getString("field_name"))
                        profileadd_con?.addView(view)
                    }
                }
            }

           // usermodel = serviceSetterGetter

        })
    }
}