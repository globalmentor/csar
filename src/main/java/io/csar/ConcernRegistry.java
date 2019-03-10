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
import java.util.stream.Stream;

import javax.annotation.Nonnull;

/**
 * A registry of concerns.
 * @author Garret Wilson
 * @see Csar
 */
public interface ConcernRegistry extends Concerned {

	/**
	 * Registers the given concerns, associating them with their respective concern types.
	 * @implSpec The default implementation delegates to {@link #registerConcerns(Stream)}.
	 * @param concerns The concerns to register.
	 * @see #registerConcern(Concern)
	 */
	public default void registerConcerns(@Nonnull final Concern... concerns) {
		registerConcerns(Stream.of(concerns));
	}

	/**
	 * Registers the given concerns, associating them with their respective concern types.
	 * @implSpec The default implementation delegates to {@link #registerConcerns(Stream)}.
	 * @param concerns The concerns to register.
	 * @see #registerConcern(Concern)
	 */
	public default void registerConcerns(@Nonnull final Collection<Concern> concerns) {
		registerConcerns(concerns.stream());
	}

	/**
	 * Registers the given concerns, associating them with their respective concern types.
	 * @implSpec The default implementation calls {@link #registerConcern(Concern)} for each indicated concern.
	 * @param concerns The concerns to register.
	 * @see #registerConcern(Concern)
	 */
	public default void registerConcerns(@Nonnull final Stream<Concern> concerns) {
		concerns.forEach(this::registerConcern);
	}

	/**
	 * Registers the given concern, associating it with its concern type.
	 * @implSpec The default implementation delegates to {@link #registerConcern(Class, Concern)}.
	 * @param <C> The type of concern being registered.
	 * @param <D> The type of concern previously registered.
	 * @param concern The concern to register.
	 * @return The concern previously associated with the same concern type.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 * @throws ClassCastException if the concern to be registered is not an instance of its own {@link Concern#getConcernType()}.
	 * @see Concern#getConcernType()
	 * @see #registerConcern(Class, Concern)
	 */
	public default <C extends Concern, D extends Concern> Optional<D> registerConcern(@Nonnull final C concern) {
		@SuppressWarnings("unchecked")
		final Class<D> concernType = (Class<D>)concern.getConcernType(); //this is technically incorrect, but it gives us a temporary generic type to use
		return registerConcern(concernType, concernType.cast(concern));
	}

	/**
	 * Registers the given concern.
	 * @param <T> The registration concern type.
	 * @param <C> The type of concern being registered.
	 * @param concernType The class with which to associate the concern.
	 * @param concern The concern to register.
	 * @return The concern previously associated with the given class.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 * @throws ClassCastException if the concern to be registered is not an instance of the given concern type.
	 */
	public <T extends Concern, C extends T> Optional<T> registerConcern(@Nonnull final Class<T> concernType, @Nonnull final C concern);

	/**
	 * Unregisters a concern of the given type. If no concern is associated with the specified type, no action occurs.
	 * @param <T> The type of concern being unregistered.
	 * @param concernType The class with which the concern is associated.
	 * @return The concern previously associated with the given class.
	 */
	public <T extends Concern> Optional<T> unregisterConcern(@Nonnull final Class<T> concernType);

}
