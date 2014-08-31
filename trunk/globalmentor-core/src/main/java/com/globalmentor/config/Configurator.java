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

import static com.globalmentor.java.Threads.*;

/**
 * Provides access to some configuration that may be global or local to some section of the program.
 * <p>
 * A configuration is some implementation of {@link Configuration}. A configuration of a specific type can be associated with some thread group, which allows
 * local configurations to be set. A global configuration can also be set, which which serves as a fallback for that configuration type when no thread
 * group-specific configuration is defined. No fallback need be implemented if a thread group-specific configuration is always provided to requesting threads.
 * </p>
 * <p>
 * A configuration can be made local by registering it with a thread group that is {@link ConfigurationManaged}, such as {@link ConfigurationManagerThreadGroup}
 * , and creating a {@link Thread} using one of the thread's constructors that specify a {@link ThreadGroup}. All threads that run in the thread group will have
 * access to the configuration by calling {@link Configurator#getConfiguration(Class)}, specifying the class of the {@link Configuration} implementation. The
 * configuration registered with the {@link ConfigurationManaged} thread group will be returned. A configuration can thus be restricted to specific areas of the
 * program.
 * </p>
 * <p>
 * If no thread group is found that implements the configuration type, a global default configuration is searched for by using
 * {@link Configurator#getDefaultConfiguration(Class)}. If no local or global configuration of the requested type is found, a {@link ConfigurationException} is
 * thrown.
 * </p>
 * @author Garret Wilson
 */
public class Configurator {

	/** The manager of default configurations. */
	private static final ConfigurationManager defaultConfigurationManager = new DefaultConfigurationManager();

	/** This class cannot be publicly instantiated. */
	private Configurator() {
	}

	/**
	 * Sets the given configurations as defualts for their respective classes.
	 * @param configurations The configurations to set.
	 */
	public static void setDefaultConfigurations(final Configuration... configurations) {
		defaultConfigurationManager.setConfigurations(configurations);
	}

	/**
	 * Sets the given configuration as default for its class.
	 * @param <C> The type of configuration being set.
	 * @param configuration The configuration to set.
	 * @return The configuration previously associated with the same class, or <code>null</code> if there was no previous configuration for that class.
	 * @throws NullPointerException if the given configuration is <code>null</code>.
	 */
	public static <C extends Configuration> C setDefaultConfiguration(final C configuration) {
		return defaultConfigurationManager.setConfiguration(configuration);
	}

	/**
	 * Sets the given configuration as default.
	 * @param <C> The type of configuration being set.
	 * @param configurationClass The class with which to associate the configuration.
	 * @param configuration The configuration to set.
	 * @return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	 * @throws NullPointerException if the given configuration is <code>null</code>.
	 */
	public static <C extends Configuration> C setDefaultConfiguration(final Class<C> configurationClass, final C configuration) {
		return defaultConfigurationManager.setConfiguration(configurationClass, configuration);
	}

	/**
	 * Returns the default configuration for the given configuration type.
	 * @param <C> The type of configuration to retrieve.
	 * @param configurationClass The class of configuration to retrieve.
	 * @return The configuration associated with the given class, or <code>null</code> if there was no configuration for that class.
	 */
	public static <C extends Configuration> C getDefaultConfiguration(final Class<C> configurationClass) {
		return defaultConfigurationManager.getConfiguration(configurationClass);
	}

	/**
	 * Removes a default configuration of the given type. If no configuration is associated with the specified type, no action occurs.
	 * @param <C> The type of configuration being removed.
	 * @param configurationClass The class with which the configuration is associated.
	 * @return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	 */
	public static <C extends Configuration> C removeDefaultConfiguration(final Class<C> configurationClass) {
		return defaultConfigurationManager.removeConfiguration(configurationClass);
	}

	/**
	 * Retrieves the configuration of the given type for the given thread.
	 * <p>
	 * A local configuration is first searched for using the first {@link ConfigurationManaged} thread group, if any, of the current thread. If no
	 * {@link ConfigurationManaged} thread group is found for the thread, or no such configuration is set for the thread group, a default configuration is
	 * searched for using {@link #getDefaultConfiguration(Class)}. If no appropriate configuration can be found, A {@link ConfigurationException} is thrown.
	 * </p>
	 * @param <C> The type of configuration to retrieve.
	 * @param configurationClass The class indicating the type of configuration to retrieve.
	 * @return The configuration of the requested type.
	 * @throws ConfigurationException if no configuration of the requested type could be found.
	 * @see ConfigurationManaged#getConfiguration(Class)
	 * @see #getDefaultConfiguration(Class)
	 */
	public static <C extends Configuration> C getConfiguration(final Class<C> configurationClass) {
		return getConfiguration(Thread.currentThread(), configurationClass); //retrieve a configuration for the current thread
	}

	/**
	 * Retrieves the configuration of the given type.
	 * <p>
	 * A local configuration is first searched for using the first {@link ConfigurationManaged} thread group, if any, of the given thread. If no
	 * {@link ConfigurationManaged} thread group is found for the thread, or no such configuration is set for the thread group, a default configuration is
	 * searched for using {@link #getDefaultConfiguration(Class)}. If no appropriate configuration can be found, A {@link ConfigurationException} is thrown.
	 * </p>
	 * @param <C> The type of configuration to retrieve.
	 * @param thread The thread for which a configuration should be retrieved.
	 * @param configurationClass The class indicating the type of configuration to retrieve.
	 * @return The configuration of the requested type.
	 * @throws ConfigurationException if no configuration of the requested type could be found.
	 * @see ConfigurationManaged#getConfiguration(Class)
	 * @see #getDefaultConfiguration(Class)
	 */
	protected static <C extends Configuration> C getConfiguration(final Thread thread, final Class<C> configurationClass) {
		C configuration = null; //search for a thread-group-local configuration
		final ConfigurationManaged configuratorManagedThreadGroup = getThreadGroup(thread, ConfigurationManaged.class); //get the configuration managed thread group
		if(configuratorManagedThreadGroup != null) { //if we found the configuration managed thread group
			configuration = configuratorManagedThreadGroup.getConfiguration(configurationClass); //ask the configurator managed thread group for the configuration
		}
		if(configuration == null) { //search for a default configuration
			configuration = getDefaultConfiguration(configurationClass); //find a default configuration
		}
		if(configuration == null) { //if no configuration could be found
			throw new ConfigurationException("No local or default configuration could be found for configuration type " + configurationClass.getName());
		}
		return configuration; //return the configuration we found
	}

}
