/*
 * Copyright 2015 Adam Speakman
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

package speakman.whatsshakingnz.network.geonet;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Created by Adam on 15-05-31.
 */
public class GeonetResponse {

    public GeonetResponse() {}

    public GeonetResponse(List<GeonetFeature> features) {
        this.features = features;
    }

    List<GeonetFeature> features;

    @NonNull
    public List<GeonetFeature> getFeatures() {
        if (features == null) {
            return Collections.emptyList();
        } else {
            return features;
        }
    }
}
