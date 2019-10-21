package com.codedevtech.portfolioapp.navigation;

/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.lifecycle.Observer;

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been consumed.
 * <p>
 * [onEventConsumed] is *only* called if the [Event]'s contents has not been consumed.
 */
public class EventObserver<T> implements Observer<Event<T>> {

    private EventListener<T> eventListener;

    public EventObserver(EventListener<T> eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void onChanged(Event<T> event) {
        T consumedValue = event.consume();
        eventListener.onEvent(consumedValue);
    }

}
