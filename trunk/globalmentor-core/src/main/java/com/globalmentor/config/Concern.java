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
 * Represents a <a href=
 * "https://en.wikipedia.org/wiki/Concern_%28computer_science%29>concern</a> (usually a <a href=" https://en.wikipedia.org/wiki/Cross-cutting_concern">cross-
 * cutting concern</a>) of a program such as logging or internationalization.
 * <p>
 * This framework allows concerns to be specified locally via a {@link ConcernedThreadGroup}, or as a global default using
 * {@link Concerns#registerDefaultConcern(Concern)}.
 * </p>
 * @author Garret Wilson
 * @see Concerns
 */
public interface Concern {
}
