/*
 * Copyright © 2009 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of a registry of concerns. This class is concurrent thread-safe.
 * @author Garret Wilson
 * @see Csar
 */
public class DefaultConcernRegistry implements ConcernRegistry {

	/**
	 * The map of concerns keyed to their types.
	 * @implNote Values are stored as {@link Optional} instances to prevent wrapping overhead during lookup.
	 */
	private final Map<Class<? extends Concern>, Optional<Concern>> concerns = new ConcurrentHashMap<Class<? extends Concern>, Optional<Concern>>();

	@Override
	@SuppressWarnings("unchecked")
	public final <T extends Concern, C extends T> Optional<T> registerConcern(final Class<T> concernType, final C concern) {
		final Optional<T> oldValue = (Optional<T>)concerns.put(concernType, Optional.of(concernType.cast(concern)));
		return oldValue != null ? oldValue : Optional.empty();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Concern> Optional<T> findConcern(final Class<T> concernType) {
		return (Optional<T>)concerns.getOrDefault(concernType, Optional.empty());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Concern> Optional<T> unregisterConcern(final Class<T> concernType) {
		final Optional<T> oldValue = (Optional<T>)concerns.remove(concernType);
		return oldValue != null ? oldValue : Optional.empty();
	}
}
