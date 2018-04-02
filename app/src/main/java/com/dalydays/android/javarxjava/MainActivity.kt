package com.dalydays.android.javarxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // this keeps track of the observable subscriptions
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_example1.setOnClickListener { runExample1Observables() }

        btn_quit.setOnClickListener { finish() }
    }

    private fun runExample1Observables() {
        Log.i(TAG, "Running example 1")

        // the observable emits (pushes) data to the subscriber
        val myStrings = Observable.just("alpha", "beta", "gamma", "delta", "epsilon")
        // observe the observable - print each value as it's emitted
        disposables.add(myStrings.subscribe { println(it) } )
        // map the emitted value from the string to its length, then print that
        disposables.add(myStrings.map { it.length } .subscribe { println(it) } )

        // this observable emits a value every second, starting at 0 and incrementing by 1 each time
        val secondIntervals = Observable.interval(1, TimeUnit.SECONDS)
        // print the value emitted from the observable
        disposables.add(secondIntervals.subscribe { println(it) } )
    }

    override fun onDestroy() {
        super.onDestroy()
        // show the number of disposables
        Toast.makeText(this, "Disposed of ${disposables.size()} disposables", Toast.LENGTH_LONG).show()
        // make sure we dispose of all disposables before the app is destroyed so we don't leave things hanging
        disposables.dispose()
    }

    companion object {
        private val TAG = "RxJavaExamples"
    }
}
