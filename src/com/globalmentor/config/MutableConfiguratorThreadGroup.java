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

/**A configuration that allows the setting and retrieval of a configuration on a per-thread-group basis.
@author Garret Wilson
@see Configurator
*/
public class MutableConfiguratorThreadGroup extends ConfiguratorThreadGroup
{

	/**Thread group name constructor.
	Creates a thread group using the current thread as the parent.
	@param name The name of the new thread group.
	@param configurations The available configurations to set.
	@throws SecurityException If the current thread cannot create a thread in the specified thread group.
	@see SecurityException
	@see ThreadGroup#checkAccess()
	@see #setConfiguration(Configuration)
	*/
	public MutableConfiguratorThreadGroup(final String name, final Configuration... configurations)
	{
		this(Thread.currentThread().getThreadGroup(), name, configurations);
	}

	/**Thread group parent and thread group name constructor. 
	@param parent The parent thread group.
	@param name The name of the new thread group.
	@param configurations The available configurations to set.
	@throws NullPointerException if the given parent is <code>null</code>. 
	@throws SecurityException If the current thread cannot create a thread in the specified thread group.
	@see SecurityException
	@see ThreadGroup#checkAccess()
	@see #setConfiguration(Configuration)
	*/
	public MutableConfiguratorThreadGroup(final ThreadGroup parent, final String name, final Configuration... configurations)
	{
		super(parent, name, configurations);
	}

	/**Sets the given configurations, associating them with their respective classes.
	@param configurations The configurations to set.
	*/
	@Override
	public final void setConfigurations(final Configuration... configurations)
	{
		super.setConfigurations(configurations);
	}

	/**Sets the given configuration, associating it with its class.
	@param <C> The type of configuration being set.
	@param configuration The configuration to set.
	@return The configuration previously associated with the same class, or <code>null</code> if there was no previous configuration for that class.
	@throws NullPointerException if the given configuration is <code>null</code>.
	*/
	@Override
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
	@Override
	protected <C extends Configuration> C setConfiguration(final Class<C> configurationClass, final C configuration)
	{
		return super.setConfiguration(configurationClass, configuration);
	}

	/**Removes a configuration of the given type.
	If no configuration is associated with the specified type, no action occurs.
	@param <C> The type of configuration being removed.
	@param configurationClass The class with which the configuration is associated.
	@return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	*/
	@Override
	public <C extends Configuration> C removeConfiguration(final Class<C> configurationClass)
	{
		return super.removeConfiguration(configurationClass);
	}
}
