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

import android.support.annotation.IntDef;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class LifecycleEvent {
    public static final int ON_ATTACH = 0;
    public static final int ON_CREATE = ON_ATTACH + 1;
    public static final int ON_CREATE_VIEW = ON_CREATE + 1;
    public static final int ON_START = ON_CREATE_VIEW + 1;
    public static final int ON_RESUME = ON_START + 1;
    public static final int ON_PAUSE = ON_RESUME + 1;
    public static final int ON_STOP = ON_PAUSE + 1;
    public static final int ON_DESTROY_VIEW = ON_STOP + 1;
    public static final int ON_DESTROY = ON_DESTROY_VIEW + 1;
    public static final int ON_DETACH = ON_DESTROY + 1;

    @IntDef({ON_ATTACH, ON_CREATE, ON_CREATE_VIEW,
            ON_START, ON_RESUME,
            ON_PAUSE, ON_STOP,
            ON_DESTROY_VIEW, ON_DESTROY, ON_DETACH})
    public @interface Int {
    }
}
