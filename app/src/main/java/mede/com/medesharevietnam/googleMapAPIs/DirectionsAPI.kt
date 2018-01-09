package a.mnisdh.com.kotlingooglemap.googleMapAPIs

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Direction
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query




/**
 * Created by daeho on 2018. 1. 8..
 */
internal class DirectionsAPI constructor(){
    private var directionsService: DirectionsService
    private val key = "AIzaSyBDleGfMlieByw5f6yg0aNGCDIY-oOCDMg"

    init {
        this.directionsService = DirectionsService.Factory.create()
    }

    fun getDirection(startLatLng: LatLng, endLatLng: LatLng):Observable<Direction>{
        var origin = latLngToString(startLatLng)
        var destination = latLngToString(endLatLng)

        return directionsService.get(origin, destination, key);
    }

    private fun latLngToString(latLng: LatLng):String{
        return latLng.latitude.toString() + "," + latLng.longitude.toString()
    }

    private interface DirectionsService {
        @GET("json")
        fun get(@Query("origin") origin: String,
                @Query("destination") destination: String,
                @Query("key") key: String): Observable<Direction>

        class Factory{
            companion object {
                fun create(): DirectionsService {
                    val retrofit = Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                            .build()

                    return retrofit.create(DirectionsService::class.java!!)
                }
            }
        }
    }
}

