/*
 * Copyright 2016 Adam Speakman
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

package speakman.whatsshakingnz.analytics;

import timber.log.Timber;

/**
 * Created by Adam on 2/14/2016.
 */
public class Forest {
    public static Timber.Tree uproot() {
        return new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                // TODO Add Crashlytics logging
            }
        };
    }
}