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

/**
 * A thread group that allows the setting and retrieval of a concern on a per-thread-group basis.
 * @author Garret Wilson
 * @see Concerns
 */
public class ConcernRegistryThreadGroup extends ConcernedThreadGroup implements ConcernRegistry {

	@Override
	protected ConcernRegistry getConcerned() {
		return (ConcernRegistry)getConcerned();
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
	public <C extends Concern> C registerConcern(final C concern) {
		return getConcerned().registerConcern(concern);
	}

	@Override
	public <C extends Concern> C registerConcern(final Class<C> concernClass, final C concern) {
		return getConcerned().registerConcern(concernClass, concern);
	}

	@Override
	public <C extends Concern> C unregisterConcern(final Class<C> concernClass) {
		return getConcerned().unregisterConcern(concernClass);
	}
}
