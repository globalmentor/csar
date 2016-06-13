/*
 * Copyright © 2016 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

package io.csar;

import java.util.ServiceLoader;
import java.util.stream.Stream;

/**
 * A provider of concerns for registration with Csar
 * <p>
 * When initializing Csar will probe all instances of this class registered as service providers, and register returned {@link #concerns()} in the default Csar
 * {@link ConcernRegistry}.
 * </p>
 * @author Garret Wilson
 * @see ServiceLoader
 */
public interface ConcernProvider {

	/** @return The concern implementations provided for registration. */
	public Stream<Concern> concerns();

}
