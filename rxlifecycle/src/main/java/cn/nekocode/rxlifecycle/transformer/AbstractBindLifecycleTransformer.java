/*
 * Copyright 2016 nekocode
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
package cn.nekocode.rxlifecycle.transformer;

import android.support.annotation.NonNull;

import cn.nekocode.rxlifecycle.LifecycleEvent;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class AbstractBindLifecycleTransformer {
    private final Observable<LifecycleEvent> mLifecycleObservable;
    private final LifecycleEvent mEvent;


    AbstractBindLifecycleTransformer(
            @NonNull Observable<LifecycleEvent> lifecycleObservable,
            @NonNull LifecycleEvent event) {

        this.mLifecycleObservable = lifecycleObservable;
        this.mEvent = event;
    }

    Completable receiveEventCompletable() {
        return mLifecycleObservable
                .filter(new Predicate<LifecycleEvent>() {
                    @Override
                    public boolean test(LifecycleEvent e) throws Exception {
                        return e.compare(mEvent) >= 0;
                    }
                })
                .take(1)
                .flatMapCompletable(new Function<LifecycleEvent, CompletableSource>() {
                    @Override
                    public CompletableSource apply(LifecycleEvent event) throws Exception {
                        return Completable.complete();
                    }
                });
    }
}
