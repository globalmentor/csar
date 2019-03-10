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

import static java.util.Collections.*;
import static java.util.Objects.*;

import java.util.*;
import java.util.stream.*;

import javax.annotation.*;

/**
 * A thread group that allows the retrieval of a concern on a per-thread-group basis.
 * @author Garret Wilson
 * @see Csar
 */
public class ConcernedThreadGroup extends ThreadGroup implements Concerned {

	/**
	 * The map of concerns keyed to their types.
	 * @implNote Values are stored as {@link Optional} instances to prevent wrapping overhead during lookup.
	 */
	private final Map<Class<? extends Concern>, Optional<Concern>> concerns;

	/**
	 * Thread group name and and concerns constructor. The current thread's thread group will be used as the parent.
	 * <p>
	 * The concerns will be accessible using {@link #findConcern(Class)} using the {@link Concern#getConcernType()} of each concern. If more than one concern has
	 * the same type, the latter concern has priority.
	 * </p>
	 * @param name The name of the new thread group.
	 * @param concerns The concerns to be retrievable from the thread group.
	 * @throws NullPointerException if the given name and/or concerns is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see ThreadGroup#checkAccess()
	 */
	public ConcernedThreadGroup(@Nonnull final String name, @Nonnull final Concern... concerns) {
		this(name, Stream.of(concerns));
	}

	/**
	 * Thread group name and and concerns constructor. The current thread's thread group will be used as the parent.
	 * <p>
	 * The concerns will be accessible using {@link #findConcern(Class)} using the {@link Concern#getConcernType()} of each concern. If more than one concern has
	 * the same type, the latter concern has priority.
	 * </p>
	 * @param name The name of the new thread group.
	 * @param concerns The concerns to be retrievable from the thread group.
	 * @throws NullPointerException if the given name and/or concerns is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see ThreadGroup#checkAccess()
	 */
	public ConcernedThreadGroup(@Nonnull final String name, @Nonnull final Stream<Concern> concerns) {
		this(Thread.currentThread().getThreadGroup(), name, concerns);
	}

	/**
	 * Thread group parent, thread group name, and concerns constructor.
	 * <p>
	 * The concerns will be accessible using {@link #findConcern(Class)} using the {@link Concern#getConcernType()} of each concern. If more than one concern has
	 * the same type, the latter concern has priority.
	 * </p>
	 * @param parent The parent thread group.
	 * @param name The name of the new thread group.
	 * @param concerns The concerns to be retrievable from the thread group.
	 * @throws NullPointerException if the given parent thread group, name, and/or concerns is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see ThreadGroup#checkAccess()
	 */
	public ConcernedThreadGroup(@Nonnull final ThreadGroup parent, @Nonnull final String name, @Nonnull final Concern... concerns) {
		this(parent, name, Stream.of(concerns));
	}

	/**
	 * Thread group parent, thread group name, and concerns constructor.
	 * <p>
	 * The concerns will be accessible using {@link #findConcern(Class)} using the {@link Concern#getConcernType()} of each concern. If more than one concern has
	 * the same type, the latter concern has priority.
	 * </p>
	 * @param parent The parent thread group.
	 * @param name The name of the new thread group.
	 * @param concerns The concerns to be retrievable from the thread group.
	 * @throws NullPointerException if the given parent thread group, name, and/or concerns is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see ThreadGroup#checkAccess()
	 */
	public ConcernedThreadGroup(@Nonnull final ThreadGroup parent, @Nonnull final String name, @Nonnull final Stream<Concern> concerns) {
		super(parent, requireNonNull(name));
		this.concerns = unmodifiableMap(concerns.collect(Collectors.toMap(Concern::getConcernType, Optional::of)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Concern> Optional<T> findConcern(final Class<T> concernType) {
		return (Optional<T>)concerns.getOrDefault(concernType, Optional.empty());
	}

}
