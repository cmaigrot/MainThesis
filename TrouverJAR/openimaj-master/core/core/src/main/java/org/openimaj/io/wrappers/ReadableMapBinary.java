/**
 * Copyright (c) 2011, The University of Southampton and the individual contributors.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * 	Redistributions of source code must retain the above copyright notice,
 * 	this list of conditions and the following disclaimer.
 *
 *   *	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 *
 *   *	Neither the name of the University of Southampton nor the names of its
 * 	contributors may be used to endorse or promote products derived from this
 * 	software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openimaj.io.wrappers;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;

import org.openimaj.io.ReadableBinary;

/**
 * A wrapper for {@link Map} that is readable.
 * 
 * @author Jonathon Hare (jsh2@ecs.soton.ac.uk)
 * 
 * @param <K> The key type.
 * @param <V> The value type.
 */
public abstract class ReadableMapBinary<K, V> implements ReadableBinary {
	/**
	 * The backing map
	 */
	public Map<K, V> value;
	
	/**
	 * Construct with a backing map
	 * @param map the backing map
	 */
	public ReadableMapBinary(Map<K,V> map) {
		this.value = map;
	}
	
	@Override
	public void readBinary(DataInput in) throws IOException {
		int sz = in.readInt();
		
		for (int i=0; i<sz; i++) {
			K key = readKey(in);
			V val = readValue(in);
			value.put(key, val);
		}
	}

	protected abstract K readKey(DataInput in) throws IOException;

	protected abstract V readValue(DataInput in) throws IOException;

	@Override
	public byte[] binaryHeader() {
		return value.getClass().getName().getBytes();
	}
}
