package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Recipe.RecipeViewModel
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.Transaction
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Data.Transaction.TransactionViewModel
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity.Companion.nowSeconds
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity.Companion.removeAlarm
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity.Companion.setAlarm
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util.PrefUtil
import kotlinx.android.synthetic.main.recipe_add_fragment.*
import kotlinx.android.synthetic.main.timer_fragment.*
import java.lang.NumberFormatException

class TimerFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel

    private var transaction_id : Int = 0
    private var transaction_chef_id : Int = 0
    private lateinit var transaction_chef_name : String
    private var transaction_meal_id : Int = 0
    private lateinit var transaction_meal_name : String
    private lateinit var transaction_time : String

    private lateinit var timer: CountDownTimer
    private var timerSeconds: Long = 0
    private var timerStarted: Boolean = false
    private var timerSecondsRemaining: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.timer_fragment, container, false)

        transaction_id = this.requireArguments().getInt("transaction_id")
        transaction_chef_id = this.requireArguments().getInt("transaction_chef_id")
        transaction_meal_id = this.requireArguments().getInt("transaction_meal_id")
        transaction_chef_name = this.requireArguments().getString("transaction_chef_name").toString()
        transaction_meal_name = this.requireArguments().getString("transaction_meal_name").toString()
        transaction_time = this.requireArguments().getString("transaction_time").toString()

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        val btnPlayTimer = view.findViewById<Button>(R.id.play)
        btnPlayTimer.setOnClickListener() {
            if(!timerStarted) {
                try {
                    val total_time = view.findViewById<EditText>(R.id.edt_time).text.toString()
                    PrefUtil.setTimerLength(total_time.toInt(), context as MainActivity)
                    setNewTimerLength()
                    startTimer()
                    timerStarted = true
                }
                catch (ex : NumberFormatException){
                    Toast.makeText(
                        context as MainActivity, "minute and second must be a number!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        val btnStopTimer = view.findViewById<Button>(R.id.stop)
        btnStopTimer.setOnClickListener() {
            timer.cancel()
            PrefUtil.setTimerLength(0, context as MainActivity)
            onTimerFinished()
        }

        val btnFinishCooking = view.findViewById<Button>(R.id.finish)
        btnFinishCooking.setOnClickListener() {
            val updatedTransaction = Transaction(
                transaction_id,
                transaction_chef_id,
                transaction_chef_name,
                transaction_meal_id,
                transaction_meal_name,
                transaction_time,
                true
            )
            transactionViewModel.updateTransaction(updatedTransaction)
            (context as MainActivity).loadFragment(RecipeListFragment.newInstance(), false)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm((context as MainActivity))
    }

    override fun onPause() {
        super.onPause()

        if (timerStarted){
            timer.cancel()
            val wakeUpTime = setAlarm((context as MainActivity), nowSeconds, timerSecondsRemaining)
        }

        PrefUtil.setPreviousTimerLength(timerSeconds, (context as MainActivity))
        PrefUtil.setRemainingTimerLength(timerSecondsRemaining, (context as MainActivity))
        PrefUtil.setTimerState(timerStarted, (context as MainActivity))
    }

    private fun initTimer(){
        timerStarted = PrefUtil.getTimerState((context as MainActivity))

        if(!timerStarted){
            setNewTimerLength()
            timerSecondsRemaining = timerSeconds
        }
        else{
            timerSeconds = PrefUtil.getPreviousTimerLength((context as MainActivity))
            timerSecondsRemaining = PrefUtil.getRemainingTimerLength((context as MainActivity))
        }

        val alarmSetTime = PrefUtil.getAlarmSetTime(context as MainActivity)
        if(alarmSetTime >0){
            timerSecondsRemaining -= nowSeconds - alarmSetTime
        }

        if(timerSecondsRemaining <= 0){
            onTimerFinished()
        }
        else if(timerStarted){
            startTimer()
        }
        updateCountdown()
    }

    private fun startTimer(){
        if (timerSecondsRemaining <= 0){
            timerSecondsRemaining = timerSeconds
        }
        timer = object : CountDownTimer(timerSecondsRemaining * 1000, 1000){
            override fun onFinish() {
                onTimerFinished()
            }

            override fun onTick(millisUntilFinished: Long) {
                timerSecondsRemaining = millisUntilFinished / 1000
                updateCountdown()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLength(context as MainActivity)
        timerSeconds = (lengthInMinutes * 60L)
    }

    private fun updateCountdown(){
        val minutesRemaining = timerSecondsRemaining / 60
        val minutesRemainingStr = minutesRemaining.toString()
        val secondsInMinuteRemaining = timerSecondsRemaining - minutesRemaining * 60
        val secondStr = secondsInMinuteRemaining.toString()
        time.text = "$minutesRemainingStr:${
        if (secondStr.length == 2) secondStr
        else "0" + secondStr
        }"
    }

    private fun onTimerFinished(){
        timerStarted = false

        setNewTimerLength()

        PrefUtil.setRemainingTimerLength(timerSeconds, (context as MainActivity))
        timerSecondsRemaining = timerSeconds
        updateCountdown()
    }

    companion object{
        @JvmStatic
        fun newInstance() =
            TimerFragment()
    }
}