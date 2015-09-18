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

import static java.util.Objects.*;

/**
 * A thread group that allows the retrieval of a configuration on a per-thread-group basis. This implementation decorates an existing configuration managed
 * object.
 * @author Garret Wilson
 * @see Configurator
 */
public class ConfigurationManagedThreadGroup extends ThreadGroup implements ConfigurationManaged {

	/** The implementation for managing configurations for this thread group. */
	private final ConfigurationManaged configurationManaged;

	/** @return The implementation for managing configurations for this thread group. */
	protected ConfigurationManaged getConfigurationManaged() {
		return configurationManaged;
	}

	/**
	 * Thread group name constructor. Creates a thread group using the current thread as the parent.
	 * @param name The name of the new thread group.
	 * @param configurationManaged The implementation for managing configurations for this thread group.
	 * @throws NullPointerException if the given configuration manager is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #setConfiguration(Configuration)
	 */
	public ConfigurationManagedThreadGroup(final String name, final ConfigurationManaged configurationManaged) {
		this(Thread.currentThread().getThreadGroup(), name, configurationManaged);
	}

	/**
	 * Thread group parent and thread group name constructor.
	 * @param parent The parent thread group.
	 * @param name The name of the new thread group.
	 * @param configurationManaged The implementation for managing configurations for this thread group.
	 * @throws NullPointerException if the given parent and/or configuration manager is <code>null</code>.
	 * @throws SecurityException If the current thread cannot create a thread in the specified thread group.
	 * @see SecurityException
	 * @see ThreadGroup#checkAccess()
	 * @see #setConfiguration(Configuration)
	 */
	public ConfigurationManagedThreadGroup(final ThreadGroup parent, final String name, final ConfigurationManaged configurationManaged) {
		super(parent, name);
		this.configurationManaged = requireNonNull(configurationManaged, "Configuration manager cannot be null.");
	}

	/**
	 * Returns the configuration for the given configuration type.
	 * @param <C> The type of configuration to retrieve.
	 * @param configurationClass The class of configuration to retrieve.
	 * @return The configuration associated with the given class, or <code>null</code> if there was no configuration for that class.
	 */
	public <C extends Configuration> C getConfiguration(final Class<C> configurationClass) {
		return getConfigurationManaged().getConfiguration(configurationClass);
	}

}
