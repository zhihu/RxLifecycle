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
package cn.nekocode.rxlifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.processors.BehaviorProcessor;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class BindingFragment extends Fragment {
    private BehaviorProcessor<Integer> lifecycleBehavior = BehaviorProcessor.create();

    public BindingFragment() {
    }

    public BehaviorProcessor<Integer> getLifecycleBehavior() {
        return lifecycleBehavior;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleBehavior.onNext(LifecycleEvent.ON_ATTACH);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecycleBehavior.onNext(LifecycleEvent.ON_ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleBehavior.onNext(LifecycleEvent.ON_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lifecycleBehavior.onNext(LifecycleEvent.ON_CREATE_VIEW);
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleBehavior.onNext(LifecycleEvent.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleBehavior.onNext(LifecycleEvent.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycleBehavior.onNext(LifecycleEvent.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycleBehavior.onNext(LifecycleEvent.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifecycleBehavior.onNext(LifecycleEvent.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleBehavior.onNext(LifecycleEvent.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifecycleBehavior.onNext(LifecycleEvent.ON_DETACH);
    }
}
