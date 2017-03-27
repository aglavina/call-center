package com.aglavina.service;

import org.junit.Before;
import org.junit.Test;

import com.aglavina.model.Call;
import com.aglavina.model.EmployeeType;
import com.aglavina.model.Operator;

import org.junit.Assert;

import static com.aglavina.model.EmployeeType.*;

import java.util.ArrayList;
import java.util.List;

public class DispatcherTest {

	private static int operatorId = 0,callId = 0;
	Dispatcher dispatcher;
	@Before
	public void setUp() {
		dispatcher = new Dispatcher();
	}
	
	@Test(expected=NoAvailableOperatorException.class)
	public void testZeroOperators() {
		dispatchCalls(1);
	}

	@Test
	public void testOneCall() {
		addOperators(1);
		dispatchCalls(1);
	}

	@Test
	public void testTenCalls() {
		addOperators(10);
		dispatchCalls(10);
	}

	@Test(expected=NoThreadAvailableException.class)
	public void testElevenCalls() {
		addOperators(20);
		dispatchCalls(11);
	}
	
	@Test
	public void testDirectorIsAvailable() {
		Operator director = getDirector();
		dispatcher.addOperator(director);
		List<Operator> operators = addOperators(2);
		dispatchCalls(2);
		sleep(2000);
		Assert.assertTrue(!operators.get(0).isAvailable());
		Assert.assertTrue(!operators.get(1).isAvailable());
		Assert.assertTrue(director.isAvailable());
	}

	@Test
	public void testDirectorSupervisorAreAvailable() {
		Operator director = getDirector();
		dispatcher.addOperator(director);
		List<Operator> operators = addOperators(1);
		Operator supervisor = getSupervisor();
		dispatcher.addOperator(supervisor);
		operators.addAll(addOperators(1));
		dispatchCalls(2);
		sleep(2000);
		Assert.assertTrue(!operators.get(0).isAvailable());
		Assert.assertTrue(!operators.get(1).isAvailable());
		Assert.assertTrue(director.isAvailable());
		Assert.assertTrue(supervisor.isAvailable());
	}

	@Test
	public void testTenCallsCheckEnded() {
		addOperators(10);
		List<Call> calls = dispatchCalls(10);
		for (Call call : calls ) checkCallEnded(call);
	}

	private List<Call> dispatchCalls(int n) {
		List<Call> calls = new ArrayList<Call>();
		for (int i = 0 ; i <n ; ++i ){
			Call call = getTestCall();
			dispatcher.dispatchCall(call);
			calls.add(call);
		}
		return calls;
	}

	private void checkCallEnded(Call call) {
		int i = 0;
		for (;!call.isFinished() && i < 20;++i) {
			sleep(2000);
		}
		Assert.assertTrue(i<20);
	}

	private void sleep(int timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private Operator getOperator() {return createOperator(OPERATOR);}
	private Operator getSupervisor() {return createOperator(SUPERVISOR);}
	private Operator getDirector() {return createOperator(DIRECTOR);}
	
	private Operator createOperator(EmployeeType type) {
		Operator res = new Operator();
		res.setId(nextOperatorId());
		res.setType(type);
		return res;
	}

	private Call getTestCall() {
		Call res = new TestCall();
		res.setId(nextCallId());
		return res;
	}
	
	private static int nextOperatorId() { return operatorId++;}
	private static int nextCallId() { return callId++;}
	
	private List<Operator> addOperators(int n) {
		List<Operator> res = new ArrayList<Operator>();
		for (int i = 0 ; i < n ; ++i ){
			Operator op = getOperator();
			dispatcher.addOperator(op);
			res.add(op);
		}
		return res;
	}

}
