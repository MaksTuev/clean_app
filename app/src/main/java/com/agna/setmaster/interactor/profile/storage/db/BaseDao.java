/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.interactor.profile.storage.db;

import com.agna.setmaster.app.log.RemoteLogger;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
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
                    RemoteLogger.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
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
                    RemoteLogger.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
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
                    RemoteLogger.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(newData);
                subscriber.onCompleted();
            }
        });
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
                    RemoteLogger.logError(e);
                    subscriber.onError(e);
                    return;
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    public T get(final ID id) {
        T result;
        try {
            result = getData(id);
        } catch (SQLException e) {
            Timber.e(e, "Error occurred when call " + this.getClass().getSimpleName() + "#get()" + " id: " + id);
            RemoteLogger.logError(e);
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
            RemoteLogger.logError(e);
            return null;
        }
        return newData;
    }

    public boolean deleteFirst() {
        try {
            return deleteFirstData();
        } catch (SQLException e) {
            Timber.e(e, "Error occurred when call "+ this.getClass().getSimpleName() +"#deleteFirst()");
            RemoteLogger.logError(e);
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