package com.example.itarchitecture1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.itarchitecture1.databinding.FragmentSecondBinding
import com.example.remote_suisei_service.SuiseiInterface
import java.lang.Error

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var suiseiService: SuiseiInterface? = null
    private var isBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, service: IBinder?) {
            suiseiService = SuiseiInterface.Stub.asInterface(service)
            Log.d("suisei in second fragment", "service suisei service is connected")
            isBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            Toast.makeText(context, "service suisei service is disconnected in second fragment", Toast.LENGTH_SHORT).show()
            Log.d("suisei in second fragment", "suisei service disconnected")
            suiseiService = null
        }
    }

    private fun registerSuiseiWordByService(suiseiService: SuiseiInterface?, text: String){
        try {
            suiseiService?.registerSuiseiWords(text)
        } catch (e: Error) {
            Log.d("suiseiService error", e.toString())
        }
    }

    private fun bindSuiseiService(){
        Log.d("suisei in second fragment", "listener activated")
        val intent = Intent().also {
            it.action = "com.example.remote_suisei_service.SUISEI_SERVER"
            it.`package` = "com.example.remote_suisei_service"
        }
        context?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSuiseiTextButton.setOnClickListener{
            bindSuiseiService()
            val inputTextView = view.findViewById<EditText>(R.id.input_suisei_text)
            val inputText = inputTextView.text.toString()
            Toast.makeText(context, inputText, Toast.LENGTH_SHORT).show()
            registerSuiseiWordByService(suiseiService, inputText)
            Log.d("suisei in second activity", inputText)
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onStop() {
//        super.onStop()
//        if (isBound){
//            context?.unbindService(connection)
//        }
//        isBound = false
//    }
}