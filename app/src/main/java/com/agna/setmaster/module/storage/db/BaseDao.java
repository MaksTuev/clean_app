package com.agna.setmaster.module.storage.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.agna.setmaster.app.log.LogServerUtil;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 *
 */
public abstract class BaseDao<T, ID> extends BaseDaoImpl<T, ID> {

    public BaseDao(Class<T> dataClass) throws SQLException {
        super(dataClass);
    }

    public BaseDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public BaseDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public Observable<T> getAsync(final ID id) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onStart();
                T result;
                try {
                    result = getData(id);
                } catch (SQLException e) {
                    Timber.e(e, "Error occurred when call " + this.getClass().getSimpleName() + "#getAsync()");
                    LogServerUtil.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<List<T>> getAllAsync() {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                subscriber.onStart();
                List<T> result;
                try {
                    result = queryForAll();
                } catch (SQLException e) {
                    Timber.e(e, "Error occurred when call " + this.getClass().getSimpleName() + "#getAsync()");
                    LogServerUtil.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<T> saveAsync(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onStart();
                T newData;
                try {
                    newData = saveData(data);
                } catch (SQLException e) {
                    Timber.e(e, "Error occurred when call " + this.getClass().getSimpleName() + "#saveAsync()" + " data: " + data);
                    LogServerUtil.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(newData);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Integer> deleteAsync(ID id) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onStart();
                Integer result;
                try {
                    result = deleteById(id);
                } catch (SQLException e) {
                    Timber.e(e, "Error occurred when call "+ this.getClass().getSimpleName() +"#deleteFirstAsync()");
                    LogServerUtil.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public T get(final ID id) {
        T result;
        try {
            result = getData(id);
        } catch (SQLException e) {
            Timber.e(e, "Error occurred when call " + this.getClass().getSimpleName() + "#get()" + " id: " + id);
            LogServerUtil.logError(e);
            return null;
        }
        return result;
    }

    public T save(final T data) {
        T newData;
        try {
            newData = saveData(data);
        } catch (SQLException e) {
            Timber.e(e, "Error occurred when call " + this.getClass().getSimpleName() + "#save()" + " data: " + data);
            LogServerUtil.logError(e);
            return null;
        }
        return newData;
    }

    public boolean deleteFirst() {
        try {
            return deleteFirstData();
        } catch (SQLException e) {
            Timber.e(e, "Error occurred when call "+ this.getClass().getSimpleName() +"#deleteFirst()");
            LogServerUtil.logError(e);
            return false;
        }
    }

    protected abstract T getData(ID id) throws SQLException;

    protected T saveData(T data) throws SQLException {
        createOrUpdate(data);
        return data;
    }

    protected boolean deleteFirstData() throws SQLException {
        return delete(queryForFirst(queryBuilder().limit(1L).prepare())) == 1;
    }
}