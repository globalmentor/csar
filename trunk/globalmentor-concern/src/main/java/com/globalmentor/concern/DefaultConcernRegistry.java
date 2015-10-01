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

package com.globalmentor.concern;

import static java.util.Objects.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of a registry of concerns. This class is concurrent thread-safe.
 * @author Garret Wilson
 * @see Concerns
 */
public class DefaultConcernRegistry implements ConcernRegistry {

	/** The map of concerns keyed to their types. */
	private final Map<Class<? extends Concern>, Concern> concerns = new ConcurrentHashMap<Class<? extends Concern>, Concern>();

	@Override
	public final void registerConcerns(final Concern... concerns) {
		for(final Concern concern : concerns) {
			registerConcern(concern);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <C extends Concern> C registerConcern(final C concern) {
		return registerConcern((Class<C>)concern.getClass(), concern);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final <C extends Concern> C registerConcern(final Class<C> concernClass, final C concern) {
		return (C)concerns.put(concernClass, requireNonNull(concern, "Concern cannot be null."));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C extends Concern> C getConcern(final Class<C> concernClass) {
		return (C)concerns.get(concernClass);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C extends Concern> C unregisterConcern(final Class<C> concernClass) {
		return (C)concerns.remove(concernClass);
	}
}
