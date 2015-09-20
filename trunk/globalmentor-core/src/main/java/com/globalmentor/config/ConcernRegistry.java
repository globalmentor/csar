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

package com.globalmentor.config;

/**
 * A registry of concerns.
 * @author Garret Wilson
 * @see Concerns
 */
public interface ConcernRegistry extends Concerned {

	/**
	 * Registers the given concerns, associating them with their respective classes.
	 * @param concerns The concerns to set.
	 */
	public void registerConcerns(final Concern... concerns);

	/**
	 * Registers the given concern, associating it with its class.
	 * @param <C> The type of concern being registered.
	 * @param concern The concern to register.
	 * @return The concern previously associated with the same class, or <code>null</code> if there was no previous concern for that class.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 */
	public <C extends Concern> C registerConcern(final C concern);

	/**
	 * Registers the given concern.
	 * @param <C> The type of concern being registered.
	 * @param concernClass The class with which to associate the concern.
	 * @param concern The concern to register.
	 * @return The concern previously associated with the given class, or <code>null</code> if there was no previous concern for that class.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 */
	public <C extends Concern> C registerConcern(final Class<C> concernClass, final C concern);

	/**
	 * Unregisters a concern of the given type. If no concern is associated with the specified type, no action occurs.
	 * @param <C> The type of concern being unregistered.
	 * @param concernClass The class with which the concern is associated.
	 * @return The concern previously associated with the given class, or <code>null</code> if there was no previous concern for that class.
	 */
	public <C extends Concern> C unregisterConcern(final Class<C> concernClass);

}
