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
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class TestConditionalProcedure extends BaseFunctorTest {

	// Functor Testing Framework
	// ------------------------------------------------------------------------

	public static Procedure<Object> mockProcedure1() {
		Procedure<Object> mockInstance = mock(Procedure.class);
		return mockInstance;
	}

	@Override
	protected Object makeFunctor() {
		return new ConditionalProcedure<Object>(Constant.TRUE, NoOp.INSTANCE, NoOp.INSTANCE);
	}

	// Tests
	// ------------------------------------------------------------------------

	@Test
	public void testRun() throws Exception {
		Procedure<Object> left = mock(Procedure.class);
		Procedure<Object> right = mock(Procedure.class);
		ConditionalProcedure<Object> p = new ConditionalProcedure<Object>(Identity.INSTANCE, left, right);
		verify(left, times(0)).run(any(Object.class));
		verify(right, times(0)).run(any(Object.class));
		p.run(Boolean.TRUE);
		verify(left, times(1)).run(any(Object.class));
		verify(right, times(0)).run(any(Object.class));
		p.run(Boolean.FALSE);
		verify(left, times(1)).run(any(Object.class));
		verify(right, times(1)).run(any(Object.class));
		p.run(Boolean.TRUE);
		verify(left, times(2)).run(any(Object.class));
		verify(right, times(1)).run(any(Object.class));
	}

	@Test
	public void testEquals() throws Exception {
		ConditionalProcedure<Object> p = new ConditionalProcedure<Object>(Identity.INSTANCE, NoOp.INSTANCE,
				NoOp.INSTANCE);
		assertEquals(p, p);
		assertObjectsAreEqual(p, new ConditionalProcedure<Object>(Identity.INSTANCE, NoOp.INSTANCE, NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalProcedure<Object>(Constant.TRUE, NoOp.INSTANCE, NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalProcedure<Object>(Identity.INSTANCE,
				TestConditionalProcedure.mockProcedure1(), NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalProcedure<Object>(Identity.INSTANCE, NoOp.INSTANCE,
				TestConditionalProcedure.mockProcedure1()));
		assertObjectsAreNotEqual(p,
				new ConditionalProcedure<Object>(Constant.TRUE, TestConditionalProcedure.mockProcedure1()));
		assertTrue(!p.equals(null));
	}

	// Classes
	// ------------------------------------------------------------------------

}
