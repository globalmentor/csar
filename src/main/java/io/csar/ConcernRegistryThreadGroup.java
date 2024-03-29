/*
 * Copyright © 2009 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
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

/**
 * A thread group that allows the setting and retrieval of a concern on a per-thread-group basis.
 * @implSpec This implementation decorates an existing concern registry.
 * @author Garret Wilson
 * @see Csar
 */
public class ConcernRegistryThreadGroup extends DecoratedConcernedThreadGroup implements ConcernRegistry {

	@Override
	protected ConcernRegistry getConcerned() {
		return (ConcernRegistry)super.getConcerned();
	}

	/**
	 * Thread group name constructor. Creates a thread group using the current thread as the parent. A default concern registry is used.
	 * @param name The name of the new thread group.
	 * @param concerns The available concerns to register.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #registerConcern(Concern)
	 * @see DefaultConcernRegistry
	 */
	public ConcernRegistryThreadGroup(final String name, final Concern... concerns) {
		this(Thread.currentThread().getThreadGroup(), name, concerns);
	}

	/**
	 * Thread group parent and thread group name constructor. A default concern registry is used.
	 * @param parent The parent thread group.
	 * @param name The name of the new thread group.
	 * @param concerns The available concerns to register.
	 * @throws NullPointerException if the given parent is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #registerConcern(Concern)
	 * @see DefaultConcernRegistry
	 */
	public ConcernRegistryThreadGroup(final ThreadGroup parent, final String name, final Concern... concerns) {
		this(parent, name, new DefaultConcernRegistry(), concerns);
	}

	/**
	 * Thread group name constructor. Creates a thread group using the current thread as the parent.
	 * @param name The name of the new thread group.
	 * @param concernRegistry The implementation for registering concerns for this thread group.
	 * @param concerns The available concerns to registry.
	 * @throws NullPointerException if the given concern registry is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #registerConcern(Concern)
	 */
	public ConcernRegistryThreadGroup(final String name, final ConcernRegistry concernRegistry, final Concern... concerns) {
		this(Thread.currentThread().getThreadGroup(), name, concernRegistry, concerns);
	}

	/**
	 * Thread group parent and thread group name constructor.
	 * @param parent The parent thread group.
	 * @param name The name of the new thread group.
	 * @param concernRegistry The implementation for registering concerns for this thread group.
	 * @param concerns The available concerns to register.
	 * @throws NullPointerException if the given parent and/or concern registry is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #registerConcern(Concern)
	 */
	public ConcernRegistryThreadGroup(final ThreadGroup parent, final String name, final ConcernRegistry concernRegistry, final Concern... concerns) {
		super(parent, name, concernRegistry);
		registerConcerns(concerns);
	}

	@Override
	public void registerConcerns(final Concern... concerns) {
		getConcerned().registerConcerns(concerns);
	}

	@Override
	public void registerConcerns(Collection<Concern> concerns) {
		getConcerned().registerConcerns(concerns);
	}

	@Override
	public void registerConcerns(Stream<Concern> concerns) {
		getConcerned().registerConcerns(concerns);
	}

	@Override
	public <C extends Concern, D extends Concern> Optional<D> registerConcern(final C concern) {
		return getConcerned().registerConcern(concern);
	}

	@Override
	public <T extends Concern, C extends T> Optional<T> registerConcern(final Class<T> concernType, final C concern) {
		return getConcerned().registerConcern(concernType, concern);
	}

	@Override
	public <T extends Concern> Optional<T> unregisterConcern(final Class<T> concernType) {
		return getConcerned().unregisterConcern(concernType);
	}
}
