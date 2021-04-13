/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.example.aggregator.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.aggregator.ArrayListBackedAggregator;
import org.junit.Test;

/**
 * Shows how to implement own aggregation function with a List-backed
 * aggregator. In this example we want to monitor whether a certain value
 * appears in the list or not.
 */
public class OwnFunctionImplementationSample {
	public static Function<List<Integer>, Integer> mockFunction1(int valueToFind) {
		int mockFieldVariableValueToFind;
		Function<List<Integer>, Integer> mockInstance = mock(Function.class);
		mockFieldVariableValueToFind = valueToFind;
		when(mockInstance.evaluate(any(List.class))).thenAnswer((stubInvo) -> {
			List<Integer> lst = stubInvo.getArgument(0);
			if (lst == null || lst.size() == 0) {
				return -1;
			}
			for (int i = 0; i < lst.size(); i++) {
				if (lst.get(i).intValue() == mockFieldVariableValueToFind) {
					return i;
				}
			}
			return -1;
		});
		return mockInstance;
	}

	@Test
	public void findValue() throws Exception {
		// we're looking for 10
		ArrayListBackedAggregator<Integer> agg = new ArrayListBackedAggregator<Integer>(
				OwnFunctionImplementationSample.mockFunction1(10));
		int eval = agg.evaluate();
		assertEquals(eval, -1);

		agg.add(1);
		eval = agg.evaluate();
		assertEquals(eval, -1);

		agg.add(2);
		eval = agg.evaluate();
		assertEquals(eval, -1);

		agg.add(10);
		eval = agg.evaluate();
		assertEquals(eval, 2);
		// function finds FIRST occurence!
		agg.add(10);
		eval = agg.evaluate();
		assertEquals(eval, 2);
		agg.add(10);
		eval = agg.evaluate();
		assertEquals(eval, 2);
		agg.add(10);
		eval = agg.evaluate();
		assertEquals(eval, 2);
	}
}
