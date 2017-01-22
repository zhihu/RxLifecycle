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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class BindingFragment extends Fragment {
    private final LifecyclePublisher lifecyclePublisher = new LifecyclePublisher();

    public BindingFragment() {
    }

    public LifecyclePublisher getLifecyclePublisher() {
        return lifecyclePublisher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecyclePublisher.onAttach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecyclePublisher.onAttach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecyclePublisher.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lifecyclePublisher.onCreateView();
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecyclePublisher.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecyclePublisher.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecyclePublisher.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecyclePublisher.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifecyclePublisher.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecyclePublisher.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifecyclePublisher.onDetach();
    }
}
