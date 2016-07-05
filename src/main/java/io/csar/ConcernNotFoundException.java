/*
 * Copyright © 1996-2015 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

import javax.annotation.*;

/**
 * An unchecked illegal state exception to indicate that a particular concern could not be located.
 * @author Garret Wilson
 */
public class ConcernNotFoundException extends IllegalStateException {

	private static final long serialVersionUID = 401301847243932359L;

	/** Default constructor with no message. */
	public ConcernNotFoundException() {
		this((String)null);
	}

	/**
	 * Message constructor.
	 * @param message An explanation of why the input could not be parsed, or <code>null</code> if a no message should be used.
	 */
	public ConcernNotFoundException(@Nullable final String message) {
		this(message, null); //construct the class with no cause
	}

	/**
	 * Cause constructor.
	 * @param cause The cause error or <code>null</code> if the cause is nonexistent or unknown.
	 */
	public ConcernNotFoundException(@Nullable final Throwable cause) {
		this(null, cause); //construct the class with no message
	}

	/**
	 * Message and cause constructor.
	 * @param message An explanation of why the input could not be parsed, or <code>null</code> if a no message should be used.
	 * @param cause The cause error or <code>null</code> if the cause is nonexistent or unknown.
	 */
	public ConcernNotFoundException(@Nullable final String message, @Nullable final Throwable cause) {
		super(message, cause); //construct the class
	}

}
