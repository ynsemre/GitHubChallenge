package com.vngrs.githubchallange

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * RxJava observables produce results on the Schedulers.io() thread to avoid blocking
 * the main thread, and the results are observed on the AndroidSchedulers.mainThread().
 * Therefore, trying to write unit tests for the various RxJava calls might be cause
 * "Method getMainLooper in android.os.Looper not mocked" runtime exception. To fix this,
 * this TestRule uses RxAndroidPlugins hooks to test code to use different schedulers
 * from the ones in the production code.
 */
class RxImmediateSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)

            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}