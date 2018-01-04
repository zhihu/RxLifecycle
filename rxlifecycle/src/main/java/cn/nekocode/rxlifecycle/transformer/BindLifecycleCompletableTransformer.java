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
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class BindLifecycleCompletableTransformer<T> extends AbstractBindLifecycleTransformer
        implements CompletableTransformer {

    public BindLifecycleCompletableTransformer(
            @NonNull Observable<LifecycleEvent> lifecycleObservable,
            @NonNull LifecycleEvent event) {

        super(lifecycleObservable, event);
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return new BindLifecycleCompletable(upstream);
    }


    private class BindLifecycleCompletable extends Completable {
        private final CompletableSource mUpstream;


        private BindLifecycleCompletable(CompletableSource upstream) {
            this.mUpstream = upstream;
        }

        @Override
        protected void subscribeActual(final CompletableObserver downstream) {
            final ArrayCompositeDisposable frc = new ArrayCompositeDisposable(2);

            downstream.onSubscribe(frc);

            receiveEventCompletable()
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            frc.setResource(0, d);
                        }

                        @Override
                        public void onComplete() {
                            frc.dispose();
                        }

                        @Override
                        public void onError(Throwable e) {
                            frc.dispose();
                        }
                    });

            mUpstream.subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                    frc.setResource(1, d);
                }

                @Override
                public void onComplete() {
                    frc.dispose();
                    downstream.onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    frc.dispose();
                    downstream.onError(e);
                }
            });
        }
    }
}