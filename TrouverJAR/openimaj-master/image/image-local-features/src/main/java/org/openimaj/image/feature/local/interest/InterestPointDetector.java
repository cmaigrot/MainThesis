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
package org.openimaj.image.feature.local.interest;

import java.util.List;

import org.openimaj.image.FImage;
import org.openimaj.math.geometry.shape.Rectangle;

/**
 * A detector of interest points.
 * @author Sina Samangooei (ss@ecs.soton.ac.uk)
 * @param <T> The type of {@link InterestPointData}
 *
 */
public interface InterestPointDetector<T extends InterestPointData> extends Cloneable {
	/**
	 * Find the interest points in an image
	 * @param image
	 */
	public void findInterestPoints(FImage image);
	
	/**
	 * Find the interest points in an image
	 * @param image
	 * @param window
	 */
	void findInterestPoints(FImage image, Rectangle window);
	
	/**
	 * Retrieve the interest points found
	 * @param npoints number of interest points to retrieve, < 0 returns all
	 * @return interest points
	 */
	public List<T> getInterestPoints(int npoints);
	
	/**
	 * Retrieve the interest points found whose normalised score exceeds the threshold
	 * @param threshold normalised threshold
	 * @return interest points
	 */
	public List<T> getInterestPoints(float threshold);
	
	/**
	 * Get all the interest points found.
	 * @return all interest points
	 */
	public List<T> getInterestPoints();
	
	/**
	 * 
	 * @param detectionScaleVariance
	 */
	public void setDetectionScale(float detectionScaleVariance);

	public void setIntegrationScale(float currentScale);
}
