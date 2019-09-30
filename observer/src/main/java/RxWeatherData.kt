import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class RxWeatherData  {
    private val mTempSubject = PublishSubject.create<Double>()
    private val mHumSubject = PublishSubject.create<Double>()
    private val mPressureSubject = PublishSubject.create<Double>()
    private val mWindSubject = PublishSubject.create<WindInfo>()

    fun changeTemperature(temp: Double) {
        mTempSubject.onNext(temp)
    }

    fun changeHumidity(hum: Double) {
        mHumSubject.onNext(hum)
    }

    fun changePressure(pressure: Double) {
        mPressureSubject.onNext(pressure)
    }

    fun changeWind(wind: WindInfo) {
        mWindSubject.onNext(wind)
    }

    fun doOnTemperatureChange(onNext: (temp: Double) -> Unit): Disposable {
        return mTempSubject.subscribe(onNext)
    }

    fun doOnHumidityChange(onNext: (hum: Double) -> Unit): Disposable {
        return mHumSubject.subscribe(onNext)
    }

    fun doOnPressureChange(onNext: (pressure: Double) -> Unit): Disposable {
        return mPressureSubject.subscribe(onNext)
    }

    fun doOnWindChange(onNext: (wind: WindInfo) -> Unit): Disposable {
        return mWindSubject.subscribe(onNext)
    }
}
