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

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public enum LifecycleEvent {
    ATTACH(COUNTER.getAndIncrease()),
    CREATE(COUNTER.getAndIncrease()),
    CREATE_VIEW(COUNTER.getAndIncrease()),
    START(COUNTER.getAndIncrease()),
    RESUME(COUNTER.getAndIncrease()),
    PAUSE(COUNTER.getAndIncrease()),
    STOP(COUNTER.getAndIncrease()),
    DESTROY_VIEW(COUNTER.getAndIncrease()),
    DESTROY(COUNTER.getAndIncrease()),
    DETACH(COUNTER.getAndIncrease()),

    DISPOSE(COUNTER.getAndIncrease()),
    ;

    private static class COUNTER {
        static int i = 0;

        static int getAndIncrease() {
            return i++;
        }
    }
    private final int i;


    LifecycleEvent(int i) {
        this.i = i;
    }

    public int compare(LifecycleEvent other) {
        return i -  other.i;
    }
}
