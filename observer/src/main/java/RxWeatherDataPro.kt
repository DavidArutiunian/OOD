import io.reactivex.subjects.PublishSubject
import java.util.*
import kotlin.collections.HashMap

class RxWeatherDataPro : Observable<WeatherInfo, WeatherInfoType> {
    private var mTemperature = 0.0
    private var mHumidity = 0.0
    private var mPressure = 0.0
    private var mWindInfo = WindInfo(0.0, 0)

    private val mTempSubject = PublishSubject.create<Unit>()
    private val mHumSubject = PublishSubject.create<Unit>()
    private val mPressureSubject = PublishSubject.create<Unit>()
    private val mWindSubject = PublishSubject.create<Unit>()
    private var mAnySubject = PublishSubject.create<Unit>()

    private val subscriptions = HashMap<Int, EnumMap<WeatherInfoType, Boolean>>()

    override fun registerObserver(observer: Observer<WeatherInfo>, priority: Priority?) {
        mAnySubject
            .takeWhile { observerSubscribed(observer, WeatherInfoType.ANY) }
            .subscribe { onSubjectNext(observer) }
    }

    private fun observerSubscribed(observer: Observer<WeatherInfo>, event: WeatherInfoType) =
        subscriptions.getOrDefault(
            observer.hashCode(),
            EnumMap(WeatherInfoType::class.java)
        )
            .getOrDefault(event, false)

    override fun removeObserver(observer: Observer<WeatherInfo>) {
        subscriptions.remove(observer.hashCode())
    }

    override fun listenToEvent(observer: Observer<WeatherInfo>, event: WeatherInfoType) {
        subscriptions[observer.hashCode()]?.set(WeatherInfoType.ANY, false)

        subscriptions.getOrPut(observer.hashCode(), { EnumMap(WeatherInfoType::class.java) })[event] = true

        when (event) {
            WeatherInfoType.TEMPERATURE -> mTempSubject
            WeatherInfoType.HUMIDITY -> mHumSubject
            WeatherInfoType.PRESSURE -> mPressureSubject
            WeatherInfoType.WIND -> mWindSubject
            else -> null
        }
            ?.takeWhile { observerSubscribed(observer, event) }
            ?.subscribe { onSubjectNext(observer) }
    }

    override fun stopListeningEvent(observer: Observer<WeatherInfo>, event: WeatherInfoType) {
        subscriptions[observer.hashCode()]?.remove(event)

        if (!subscriptions.containsKey(observer.hashCode())) {
            subscriptions.getOrPut(observer.hashCode(), { EnumMap(WeatherInfoType::class.java) })[WeatherInfoType.ANY] =
                true
        }
    }

    private fun setMeasurementsImpl(
        temp: Double,
        humidity: Double,
        pressure: Double,
        events: MutableSet<WeatherInfoType>
    ) {
        when {
            changed(mTemperature, temp) -> events.add(WeatherInfoType.TEMPERATURE)
            changed(mHumidity, humidity) -> events.add(WeatherInfoType.HUMIDITY)
            changed(mPressure, pressure) -> events.add(WeatherInfoType.PRESSURE)
        }

        mTemperature = temp
        mHumidity = humidity
        mPressure = pressure

        measurementsChanged(events)
    }

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double, wind: WindInfo) {
        val events = mutableSetOf<WeatherInfoType>()

        when {
            changed(mWindInfo, wind) -> events.add(WeatherInfoType.WIND)
        }

        mWindInfo = wind

        setMeasurementsImpl(temp, humidity, pressure, events)
    }

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double) {
        val events = mutableSetOf<WeatherInfoType>()
        setMeasurementsImpl(temp, humidity, pressure, events)
    }

    private fun changed(prev: Any, curr: Any) = prev != curr

    private fun measurementsChanged(events: Set<WeatherInfoType>) = notifyObservers(events)


    override fun notifyObservers(events: Set<WeatherInfoType>) {
        mAnySubject.onNext(Unit)
        events.forEach {
            when (it) {
                WeatherInfoType.TEMPERATURE -> mTempSubject
                WeatherInfoType.HUMIDITY -> mHumSubject
                WeatherInfoType.PRESSURE -> mPressureSubject
                WeatherInfoType.WIND -> mWindSubject
                else -> null
            }?.onNext(Unit)
        }
    }

    private fun onSubjectNext(observer: Observer<WeatherInfo>) {
        observer.update(getChangedData(), this)
    }

    private fun getTemperature() = mTemperature

    private fun getHumidity() = mHumidity

    private fun getPressure() = mPressure

    private fun getWindInfo() = mWindInfo

    private fun getChangedData() = WeatherInfo(getTemperature(), getHumidity(), getPressure(), getWindInfo())

}