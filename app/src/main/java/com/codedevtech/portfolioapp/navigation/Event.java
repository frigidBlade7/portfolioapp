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

import androidx.annotation.Nullable;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * [Read more about this.](https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)
 */

public class Event<T> {

    private T content;
    private Boolean consumed = false;

    public Event(T content) {
        this.content = content;
    }

    @Nullable public T consume() {
        if (consumed) return null;

        consumed = true;
        return content;
    }

    public T peek() {
        return content;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;

        final Event<?> other = (Event<?>) obj;

        if (content != other.content) return false;
        return consumed == other.consumed;
    }

    @Override
    public int hashCode() {
        int hash = content.hashCode();
        hash = 31 * hash * consumed.hashCode();
        return hash;
    }
}
