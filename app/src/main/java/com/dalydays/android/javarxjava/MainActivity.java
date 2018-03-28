package com.dalydays.android.javarxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    // this keeps track of the observable subscriptions
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // the observable emits (pushes) data to the subscriber
        Observable<String> myStrings = Observable.just("alpha", "beta", "gamma", "delta", "epsilon");
        // observe the observable - print each value as it's emitted
        disposables.add(myStrings.subscribe(System.out::println));
        // map the emitted value from the string to its length, then print that
        disposables.add(myStrings.map(String::length).subscribe(System.out::println));

        // this observable emits a value every second, starting at 0 and incrementing by 1 each time
        Observable<Long> secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
        // print the value emitted from the observable
        disposables.add(secondIntervals.subscribe(System.out::println));

        // wait 5 seconds while the secondIntervals observable emits values (should emit 5 values, once per second)
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Done emitting values, quitting now", Toast.LENGTH_LONG).show();

        // quit our app
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // make sure we dispose of all disposables before the app is destroyed so we don't leave things hanging
        disposables.dispose();
    }
}
