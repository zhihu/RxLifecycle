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
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.BehaviorProcessor;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class BindLifecycleSingleTransformer<T> implements SingleTransformer<T, T> {
    private final BehaviorProcessor<Integer> lifecycleBehavior;

    private BindLifecycleSingleTransformer() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public BindLifecycleSingleTransformer(@NonNull BehaviorProcessor<Integer> lifecycleBehavior) {
        this.lifecycleBehavior = lifecycleBehavior;
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.takeUntil(
                lifecycleBehavior.skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(@LifecycleEvent.Int Integer event) throws Exception {
                        return event != LifecycleEvent.ON_DESTROY_VIEW &&
                                event != LifecycleEvent.ON_DESTROY &&
                                event != LifecycleEvent.ON_DETACH;
                    }
                })
        );
    }
}