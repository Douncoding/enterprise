package com.douncoding.enterprise.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.douncoding.enterprise.data.entity.User;
import com.douncoding.enterprise.data.exception.NetworkConnectionException;

import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * {@link RestApi} implementation for retrieving data from the network(cloud).
 *
 * 무슨느낌인지 감잡았어... 쉬워
 * 왜 예외클래스를 직접생성해야 하는지 쉬워 생각해봐
 * Retrofit2 적용후 수정되어야 하기 떄문에 학습목적으로 하나만 작성한다.
 */
public class RestApiImpl implements RestApi {
    private final Context context;

    public RestApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("");
        }

        this.context = context;
    }

    @Override
    public Observable<List<User>> userList() {
        return Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        String response = getUserListFromApi();
                        if (response != null) {
                            subscriber.onNext(null);
                        } else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }
        });
    }

    @Override
    public Observable<User> userById() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {

            }
        });
    }

    private String getUserListFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST).requestSyncCall();
    }

    private boolean isThereInternetConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
