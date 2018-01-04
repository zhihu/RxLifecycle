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

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import cn.nekocode.rxlifecycle.LifecycleEvent;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class BindLifecycleFlowableTransformer<T> extends AbstractBindLifecycleTransformer
        implements FlowableTransformer<T, T> {

    public BindLifecycleFlowableTransformer(
            @NonNull Observable<LifecycleEvent> lifecycleObservable,
            @NonNull LifecycleEvent event) {

        super(lifecycleObservable, event);
    }

    @Override
    public Publisher<T> apply(final Flowable<T> upstream) {
        return new BindLifecycleFlowable(upstream);
    }


    private class BindLifecycleFlowable extends Flowable<T> {
        private final Publisher<T> mUpstream;


        private BindLifecycleFlowable(Publisher<T> upstream) {
            this.mUpstream = upstream;
        }

        @Override
        protected void subscribeActual(final Subscriber<? super T> downstream) {
            final MainSubscriber mainSubscriber = new MainSubscriber(downstream);

            downstream.onSubscribe(mainSubscriber);

            receiveEventCompletable().subscribe(mainSubscriber.other);
            mUpstream.subscribe(mainSubscriber);
        }


        final class MainSubscriber extends AtomicInteger implements Subscriber<T>, Subscription {
            private static final long serialVersionUID = 919611990130321642L;
            final Subscriber<? super T> actual;
            final OtherObserver other;
            final AtomicReference<Subscription> s;
            final AtomicLong requested;
            final AtomicThrowable error;


            MainSubscriber(Subscriber<? super T> actual) {
                this.actual = actual;
                this.requested = new AtomicLong();
                this.s = new AtomicReference<>();
                this.other = new OtherObserver();
                this.error = new AtomicThrowable();
            }

            @Override
            public void onSubscribe(Subscription s) {
                SubscriptionHelper.deferredSetOnce(this.s, requested, s);
            }

            @Override
            public void onNext(T t) {
                HalfSerializer.onNext(actual, t, this, error);
            }

            @Override
            public void onError(Throwable t) {
                DisposableHelper.dispose(other);
                HalfSerializer.onError(actual, t, this, error);
            }

            @Override
            public void onComplete() {
                DisposableHelper.dispose(other);
                HalfSerializer.onComplete(actual, this, error);
            }

            @Override
            public void request(long n) {
                SubscriptionHelper.deferredRequest(s, requested, n);
            }

            @Override
            public void cancel() {
                SubscriptionHelper.cancel(s);
                DisposableHelper.dispose(other);
            }

            final class OtherObserver extends AtomicReference<Disposable> implements CompletableObserver {
                private static final long serialVersionUID = -6684536082750051972L;


                @Override
                public void onSubscribe(Disposable d) {
                    DisposableHelper.setOnce(this, d);
                }

                @Override
                public void onError(Throwable e) {
                    DisposableHelper.dispose(this);
                    SubscriptionHelper.cancel(s);
                }

                @Override
                public void onComplete() {
                    DisposableHelper.dispose(this);
                    SubscriptionHelper.cancel(s);
                }
            }
        }
    }
}