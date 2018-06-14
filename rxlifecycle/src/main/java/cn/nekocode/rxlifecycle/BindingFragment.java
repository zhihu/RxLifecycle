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
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import io.reactivex.subjects.BehaviorSubject;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class BindingFragment extends Fragment {
    private final BehaviorSubject<LifecycleEvent> mLifecycleBehavior = BehaviorSubject.create();


    public BindingFragment() {
    }

    public BehaviorSubject<LifecycleEvent> getLifecycleBehavior() {
        return mLifecycleBehavior;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLifecycleBehavior.onNext(LifecycleEvent.ATTACH);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLifecycleBehavior.onNext(LifecycleEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleBehavior.onNext(LifecycleEvent.CREATE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycleBehavior.onNext(LifecycleEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleBehavior.onNext(LifecycleEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycleBehavior.onNext(LifecycleEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycleBehavior.onNext(LifecycleEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLifecycleBehavior.onNext(LifecycleEvent.STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLifecycleBehavior.onNext(LifecycleEvent.DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLifecycleBehavior.onNext(LifecycleEvent.DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLifecycleBehavior.onNext(LifecycleEvent.DETACH);
    }
}
