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

/**
 * A thread group that allows the retrieval of a concern on a per-thread-group basis. This implementation decorates an existing concerned object.
 * @author Garret Wilson
 * @see Concerns
 */
public class ConcernedThreadGroup extends ThreadGroup implements Concerned {

	/** The implementation for managing concerns for this thread group. */
	private final Concerned concerned;

	/** @return The implementation for managing concerns for this thread group. */
	protected Concerned getConcerned() {
		return concerned;
	}

	/**
	 * Thread group name constructor. Creates a thread group using the current thread as the parent.
	 * @param name The name of the new thread group.
	 * @param concerned The implementation for retrieving concerns for this thread group.
	 * @throws NullPointerException if the given concerned is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #registerConcern(Concern)
	 */
	public ConcernedThreadGroup(final String name, final Concerned concerned) {
		this(Thread.currentThread().getThreadGroup(), name, concerned);
	}

	/**
	 * Thread group parent and thread group name constructor.
	 * @param parent The parent thread group.
	 * @param name The name of the new thread group.
	 * @param concerned The implementation for retrieving concerns for this thread group.
	 * @throws NullPointerException if the given parent and/or concerned is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #registerConcern(Concern)
	 */
	public ConcernedThreadGroup(final ThreadGroup parent, final String name, final Concerned concerned) {
		super(parent, name);
		this.concerned = requireNonNull(concerned, "Concerned implementation cannot be null.");
	}

	@Override
	public <C extends Concern> C getConcern(final Class<C> concernClass) {
		return getConcerned().getConcern(concernClass);
	}

}
