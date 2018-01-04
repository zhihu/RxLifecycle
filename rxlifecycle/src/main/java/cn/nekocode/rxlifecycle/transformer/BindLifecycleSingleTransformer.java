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
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class BindLifecycleSingleTransformer<T> extends AbstractBindLifecycleTransformer
        implements SingleTransformer<T, T> {

    public BindLifecycleSingleTransformer(
            @NonNull Observable<LifecycleEvent> lifecycleObservable,
            @NonNull LifecycleEvent event) {

        super(lifecycleObservable, event);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return new BindLifecycleSingle(upstream);
    }


    private class BindLifecycleSingle extends Single<T> {
        private final SingleSource<T> mUpstream;


        private BindLifecycleSingle(SingleSource<T> upstream) {
            this.mUpstream = upstream;
        }

        @Override
        protected void subscribeActual(final SingleObserver<? super T> downstream) {
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

            mUpstream.subscribe(new SingleObserver<T>() {
                @Override
                public void onSubscribe(Disposable d) {
                    frc.setResource(1, d);
                }

                @Override
                public void onSuccess(T t) {
                    frc.dispose();
                    downstream.onSuccess(t);
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