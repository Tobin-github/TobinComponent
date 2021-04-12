/*
 * Copyright 2018-present KunMinX
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

package com.tobin.lib_resource.lifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStore;

import java.util.HashMap;
import java.util.Map;

public class ProtectedUnPeekLiveData<T> extends LiveData<T> {

  protected boolean isAllowNullValue;

  private final HashMap<Integer, Boolean> observers = new HashMap<>();

  /**
   * 适合在 activity 中使用的 observe UnPeek 方法
   * A Observe UnPeek method which suitable for use in an activity
   *
   * @param activity AppCompatActivity
   * @param observer Observer
   */
  public void observeInActivity(@NonNull AppCompatActivity activity, @NonNull Observer<? super T> observer) {
    LifecycleOwner owner = activity;
    Integer storeId = System.identityHashCode(activity.getViewModelStore());
    observe(storeId, owner, observer);
  }

  /**
   * 适合在 fragment 中使用的 observe UnPeek 方法
   * A Observe UnPeek method which suitable for use in an fragment
   *
   * @param fragment Fragment
   * @param observer Observer
   */
  public void observeInFragment(@NonNull Fragment fragment, @NonNull Observer<? super T> observer) {
    LifecycleOwner owner = fragment.getViewLifecycleOwner();
    Integer storeId = System.identityHashCode(fragment.getViewModelStore());
    observe(storeId, owner, observer);
  }

  /**
   * 通用的 observe UnPeek 方法
   * A universal Observe UnPeek method
   *
   * @param owner LifecycleOwner
   * @param store ViewModelStore
   * @param observer Observer
   */
  public void observeUnPeek(@NonNull LifecycleOwner owner, @NonNull ViewModelStore store, @NonNull Observer<? super T> observer) {
    Integer storeId = System.identityHashCode(store);
    observe(storeId, owner, observer);
  }

  private void observe(@NonNull Integer storeId,
                       @NonNull LifecycleOwner owner,
                       @NonNull Observer<? super T> observer) {

    if (observers.get(storeId) == null) {
      observers.put(storeId, true);
    }

    super.observe(owner, t -> {
      if (!observers.get(storeId)) {
        observers.put(storeId, true);
        if (t != null || isAllowNullValue) {
          observer.onChanged(t);
        }
      }
    });
  }

  /**
   * 重写的 setValue 方法，默认不接收 null
   * 可通过 Builder 配置允许接收
   * 可通过 Builder 配置消息延时清理的时间
   * <p>
   * override setValue, do not receive null by default
   * You can configure to allow receiving through Builder
   * And also, You can configure the delay time of message clearing through Builder
   *
   * @param value T
   */
  @Override
  protected void setValue(T value) {
    if (value != null || isAllowNullValue) {
      for (Map.Entry<Integer, Boolean> entry : observers.entrySet()) {
        entry.setValue(false);
      }
      super.setValue(value);
    }
  }

  protected void clear() {
    super.setValue(null);
  }
}

