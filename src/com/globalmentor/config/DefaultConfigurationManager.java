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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**Default implementation of a manager of configurations.
This class is concurrent thread-safe.
@author Garret Wilson
@see Configurator
*/
public class DefaultConfigurationManager implements ConfigurationManager
{

	/**The map of configurations keyed to their types.*/
	private final Map<Class<? extends Configuration>, Configuration> configurations=new ConcurrentHashMap<Class<? extends Configuration>, Configuration>();

	/**Sets the given configurations, associating them with their respective classes.
	@param configurations The configurations to set.
	*/
	public final void setConfigurations(final Configuration... configurations)
	{
		for(final Configuration configuration:configurations)	//set the given configurations
		{
			setConfiguration(configuration);
		}
	}

	/**Sets the given configuration, associating it with its class.
	@param <C> The type of configuration being set.
	@param configuration The configuration to set.
	@return The configuration previously associated with the same class, or <code>null</code> if there was no previous configuration for that class.
	@throws NullPointerException if the given configuration is <code>null</code>.
	*/
	@SuppressWarnings("unchecked")
	public final <C extends Configuration> C setConfiguration(final C configuration)
	{
		return setConfiguration((Class<C>)configuration.getClass(), configuration);
	}

	/**Sets the given configuration.
	@param <C> The type of configuration being set.
	@param configurationClass The class with which to associate the configuration.
	@param configuration The configuration to set.
	@return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	*/
	@SuppressWarnings("unchecked")
	public final <C extends Configuration> C setConfiguration(final Class<C> configurationClass, final C configuration)
	{
		return (C)configurations.put(configurationClass, configuration);
	}

	/**Returns the configuration for the given configuration type.
	@param <C> The type of configuration to retrieve.
	@param configurationClass The class of configuration to retrieve.
	@return The configuration associated with the given class, or <code>null</code> if there was no configuration for that class.
	 */
	@SuppressWarnings("unchecked")
	public <C extends Configuration> C getConfiguration(final Class<C> configurationClass)
	{
		return (C)configurations.get(configurationClass);
	}

	/**Removes a configuration of the given type.
	If no configuration is associated with the specified type, no action occurs.
	@param <C> The type of configuration being removed.
	@param configurationClass The class with which the configuration is associated.
	@return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	*/
	@SuppressWarnings("unchecked")
	public <C extends Configuration> C removeConfiguration(final Class<C> configurationClass)
	{
		return (C)configurations.remove(configurationClass);
	}
}
